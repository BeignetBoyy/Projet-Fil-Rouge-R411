package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Clickable {

    private List<Annonce> listTest;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = findViewById(R.id.titre);
        ListView list = findViewById(R.id.liste_annonces);
        alertDialogBuilder = new AlertDialog.Builder(this);  // ne pas mettre getApplicationContext() ici

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

        //test.setText(testAnn.toString());
    }

    @Override
    public void onClicItem(int itemIndex) {
        Log.i("Click", "Click !");
        Log.d("Click", "clicked on = " + listTest.get(itemIndex));
        Intent intent = new Intent(MainActivity.this, AnnonceActivity.class);
        intent.putExtra("annonce", listTest.get(itemIndex));
        startActivity(intent);

    }
    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
