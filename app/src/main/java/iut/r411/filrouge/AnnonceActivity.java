package iut.r411.filrouge;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnnonceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonce_activity);

        TextView titre = findViewById(R.id.libelle);


        Annonce annonce = getIntent().getExtras().getParcelable("annonce");

        titre.setText(annonce.getLibelle());

    }
}
