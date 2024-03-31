package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Annonce>,Clickable {

    private List<Annonce> listAnnonces;
    private ListView list;
    private Utilisateur vendeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titre = findViewById(R.id.titre);
        list = findViewById(R.id.liste_annonces);

        listAnnonces = new ArrayList<>();

        String url = "https://pirrr3.github.io/r411api/annonce.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, Annonce.class, this, new ProgressDialog(MainActivity.this) );
    }

    @Override
    public void onClicItem(int itemIndex) {
        Log.i("Click", "Click !");
        Log.d("Click", "clicked on = " + listAnnonces.get(itemIndex));
        Intent intent = new Intent(MainActivity.this, AnnonceActivity.class);
        intent.putExtra("annonce", listAnnonces.get(itemIndex));
        startActivity(intent);

        /*//POUR TESTER LA VUE UTILISATEUR
        Intent intentUser = new Intent(MainActivity.this, UserActivity.class);
        intentUser.putExtra("utilisateur", vendeur);
        startActivity(intentUser);*/
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
            is.close();
        }
        return sb.toString();
    }

    @Override
    public void onPostExecute(List<Annonce> itemList) {

        listAnnonces.addAll(itemList);

        AnnonceAdapter adapter = new AnnonceAdapter(listAnnonces, this);
        list.setAdapter(adapter);

        Log.d("LIST","itemList = " + itemList);
    }


}
