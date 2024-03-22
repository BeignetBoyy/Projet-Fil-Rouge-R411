package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = findViewById(R.id.test);

        Annonce testAnn = new Annonce("Test", "Blablalba", 15.00, Etat.Bon_etat);

        test.setText(testAnn.toString());
    }
}