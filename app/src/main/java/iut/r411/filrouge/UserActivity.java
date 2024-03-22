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

import java.util.ArrayList;

public class UserActivity extends Activity implements Clickable{
    Utilisateur utilisateur = (Utilisateur) getIntent().getSerializableExtra("utilisateur");
    private ArrayList<Annonce> listTest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        assert utilisateur != null;

        TextView texteNom = findViewById(R.id.username);
        String pseudo = utilisateur.getPrenom()+" "+utilisateur.getNom();
        texteNom.setText(pseudo);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
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

        listTest = new ArrayList<>();

        Utilisateur vendeur = new Utilisateur("Lallement", "Sébastien", "sebastien.lallement@etu.unice.fr", "0783770564");
        Annonce testAnn1 = new Annonce("Test1", "Blablalba", 15.00, Etat.Bon_etat, vendeur);
        Annonce testAnn2 = new Annonce("Test2", "Blablalba", 15.00, Etat.Bon_etat, vendeur);
        Annonce testAnn3 = new Annonce("Test3", "Blablalba", 15.00, Etat.Bon_etat, vendeur);
        Annonce testAnn4 = new Annonce("Test4", "Blablalba", 15.00, Etat.Bon_etat, vendeur);

        listTest.add(testAnn1);
        listTest.add(testAnn2);
        listTest.add(testAnn3);
        listTest.add(testAnn4);

        AnnonceAdapter adapter = new AnnonceAdapter(listTest, this);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);
    }

    private void afficherPopupNotation() {
        assert utilisateur != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_rating, null);
        final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5F);
        ratingBar.setRating(0);

        builder.setView(dialogView)
                .setTitle("Notez cet utilisateur")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float note = ratingBar.getRating();
                        utilisateur.majNote(note);
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