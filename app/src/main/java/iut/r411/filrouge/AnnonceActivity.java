package iut.r411.filrouge;

import android.os.Bundle;
import android.util.Log;
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
        TextView description = findViewById(R.id.description);
        TextView prix = findViewById(R.id.prix);
        ImageView image = findViewById(R.id.picture);

        libelle.setText(annonce.getLibelle());
        description.setText(annonce.getDescription());
        prix.setText(annonce.getPrix() + " €");
        Picasso.get().load(annonce.getImage()).into(image);

    }
}
