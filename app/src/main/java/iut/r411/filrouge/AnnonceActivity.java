package iut.r411.filrouge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AnnonceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonce_activity);

        Annonce annonce = getIntent().getExtras().getParcelable("annonce");



        Log.i("CONTENU ANNONCE", String.valueOf(annonce));

        TextView libelle = findViewById(R.id.libelle);
        TextView profilName = findViewById(R.id.profilName);
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

    }
}
