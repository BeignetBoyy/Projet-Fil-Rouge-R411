package iut.r411.filrouge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import java.util.List;

/** @noinspection ALL*/
public class UserActivity extends Activity implements PostExecuteActivity<Annonce>,Clickable{
    Utilisateur utilisateur;
    RatingBar ratingBar;
    private List<Annonce> listAnnonces;
    private ListView listview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listAnnonces = new ArrayList<>();
        Intent intent = getIntent();
        utilisateur = getIntent().getExtras().getParcelable("utilisateur");

        // Vérifier si l'objet Utilisateur n'est pas nul
        if(utilisateur == null) {
            Log.e("utilisateur","Erreur: L'utilisateur choisi n'existe pas");
            finish();
            return;
        }

        String url = "https://pirrr3.github.io/r411api/annonce.json";
        new HttpAsyncGet<>(url, Annonce.class, this, new ProgressDialog(UserActivity.this) );

        Log.i("USER", utilisateur.toString());

        listAnnonces = new ArrayList<>();
        Log.i("ANNONCES", listAnnonces.toString());

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
                //Intent intent = new Intent(UserActivity.this, MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });

        listview = findViewById(R.id.liste_annonces);
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
        Log.i("Click", "Click !");
        Log.d("Click", "clicked on = " + listAnnonces.get(itemIndex));
        Intent intent = new Intent(UserActivity.this, AnnonceActivity.class);
        intent.putExtra("annonce", listAnnonces.get(itemIndex));
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onPostExecute(List<Annonce> itemList) {
        Log.d("LIST","itemList = " + itemList);

        //Ajout des annonces de l'utilisateur connecté
        for(Annonce a : itemList){
            if(a.getUtilisateur().equals(utilisateur.getMail())){
                listAnnonces.add(a);
                Log.d("ADD ANNONCE", "Ajout de l'annonce " + a.getLibelle() + " à l'utilisateur " + utilisateur);
            }
        }

        AnnonceAdapter adapter = new AnnonceAdapter(listAnnonces, this);
        listview.setAdapter(adapter);
    }
}