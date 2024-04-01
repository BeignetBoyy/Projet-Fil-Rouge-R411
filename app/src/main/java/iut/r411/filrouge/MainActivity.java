package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton profil = findViewById(R.id.profile_button);
        ImageButton create_annonce = findViewById(R.id.create_button);

        Intent intent = getIntent();
        utilisateur = getIntent().getExtras().getParcelable("utilisateur");

        TextView titre = findViewById(R.id.titre);
        list = findViewById(R.id.liste_annonces);

        listAnnonces = new ArrayList<>();

        String url = "https://pirrr3.github.io/r411api/annonce.json";
        new HttpAsyncGet<>(url, Annonce.class, this, new ProgressDialog(MainActivity.this) );


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", utilisateur.toString());
                Intent intentUser = new Intent(MainActivity.this, UserActivity.class);
                intentUser.putExtra("utilisateur", utilisateur);
                startActivity(intentUser);
            }
        });

        create_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", utilisateur.toString());
                Intent intentUser = new Intent(MainActivity.this, CreateAnnonce.class);
                intentUser.putExtra("utilisateur", utilisateur);
                startActivity(intentUser);
            }
        });
    }

    @Override
    public void onClicItem(int itemIndex) {
        Log.i("Click", "Click !");
        Log.d("Click", "clicked on = " + listAnnonces.get(itemIndex));
        Intent intent = new Intent(MainActivity.this, AnnonceActivity.class);
        intent.putExtra("annonce", listAnnonces.get(itemIndex));
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onPostExecute(List<Annonce> itemList) {

        listAnnonces.addAll(itemList);

        AnnonceAdapter adapter = new AnnonceAdapter(listAnnonces, this);
        list.setAdapter(adapter);

        Log.d("LIST","itemList = " + itemList);
    }


}
