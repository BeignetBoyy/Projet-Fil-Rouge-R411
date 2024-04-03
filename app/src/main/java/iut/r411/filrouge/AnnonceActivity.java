package iut.r411.filrouge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AnnonceActivity extends AppCompatActivity implements PostExecuteActivity<Utilisateur>{
    /**
     * Cette activité affiche les détails d'une annonce, y compris son libellé, sa description,
     * son prix, sa date de création, son état, l'utilisateur associé et une image illustrative.
     * Elle permet également à l'utilisateur de revenir à l'activité précédente en appuyant sur un bouton.
     * Si l'utilisateur clique sur l'image de profil de l'utilisateur associé à l'annonce, cela l'amène
     * à une autre activité affichant les détails de cet utilisateur.
     */

    private Utilisateur utilisateur;
    private TextView profilName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonce_activity);

        Annonce annonce = getIntent().getExtras().getParcelable("annonce");

        String url = "https://pirrr3.github.io/r411api/utilisateur.json";
        new HttpAsyncGet<>(url, Utilisateur.class, this, new ProgressDialog(AnnonceActivity.this) );


        Log.i("CONTENU ANNONCE", String.valueOf(annonce));

        TextView libelle = findViewById(R.id.libelle);
        profilName = findViewById(R.id.profilName);
        TextView description = findViewById(R.id.description);
        TextView prix = findViewById(R.id.prix);
        TextView date_creation = findViewById(R.id.date_creation);
        TextView etat = findViewById(R.id.etatText);
        ImageView image = findViewById(R.id.picture);

        libelle.setText(annonce.getLibelle());
        profilName.setText(annonce.getUtilisateur());
        description.setText(annonce.getDescription());
        date_creation.setText(annonce.getDate_creation());
        etat.setText(annonce.getEtat().toString());
        prix.setText(annonce.getPrix() + " €");
        Picasso.get().load(annonce.getImage()).into(image);


        Button backButton = findViewById(R.id.boutton_retour); // Supposons que votre bouton ait l'id backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Appel de la méthode finish pour fermer cette activité et revenir à la vue précédente
            }
        });

        ImageView profil_picutre = findViewById(R.id.profilPicture);
        profil_picutre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", utilisateur.toString());
                Intent intentUser = new Intent(AnnonceActivity.this, UserActivity.class);
                intentUser.putExtra("utilisateur", utilisateur);
                startActivity(intentUser);
            }
        });

    }

    @Override
    public void onPostExecute(List<Utilisateur> itemList) {
        for(Utilisateur u : itemList){
            if(u.getMail().equals(profilName.getText().toString())){
                utilisateur = u;
            }
        }
    }
}
