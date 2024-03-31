package iut.r411.filrouge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/** @noinspection ALL*/
public class UserActivity extends Activity implements Clickable{
    Utilisateur utilisateur;
    RatingBar ratingBar;
    private ArrayList<Annonce> listTest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listTest = new ArrayList<>();
        Intent intent = getIntent();
        utilisateur = getIntent().getExtras().getParcelable("utilisateur");

        // Vérifier si l'objet Utilisateur n'est pas nul
        if(utilisateur == null) {
            Log.e("utilisateur","Erreur: L'utilisateur choisi n'existe pas");
            finish();
            return;
        }

        TextView texteNom = findViewById(R.id.username);
        String pseudo = utilisateur.getPrenom()+" "+utilisateur.getNom();
        texteNom.setText(pseudo);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setIsIndicator(true);
        ratingBar.setRating(utilisateur.getNote());

        TextView texteMail = findViewById(R.id.userMail);
        texteMail.setText(utilisateur.getMail());

        TextView texteNum = findViewById(R.id.userNum);
        texteNum.setText(utilisateur.getNumTel());

        Button boutonRetour = findViewById(R.id.buttonBack);
        Button boutonNoter = findViewById(R.id.buttonRate);

        boutonNoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherPopupNotation();
            }
        });

        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ListView list = findViewById(R.id.liste_annonces);

        AnnonceAdapter adapter = new AnnonceAdapter(listTest, this);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);
    }

    private void afficherPopupNotation() {
        assert utilisateur != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_rating, null);
        final RatingBar ratingBarPopup = dialogView.findViewById(R.id.ratingBar);
        ratingBarPopup.setNumStars(5);
        ratingBarPopup.setStepSize(0.5F);
        ratingBarPopup.setRating(0);

        builder.setView(dialogView)
                .setTitle("Notez cet utilisateur")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float note = ratingBarPopup.getRating();
                        utilisateur.majNote(note);
                        ratingBar.setRating(utilisateur.getNote());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClicItem(int itemIndex) {
        Log.i("UserActivity","Has clicked");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}