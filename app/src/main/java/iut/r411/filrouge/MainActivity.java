package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Annonce>,Clickable {

    private List<Annonce> listAnnonces;
    private ListView listview;
    private Utilisateur utilisateur;
    private int seekValue = 1000;
    private TextView seek_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton profil = findViewById(R.id.profile_button);
        ImageButton create_annonce = findViewById(R.id.create_button);

        Intent intent = getIntent();
        utilisateur = getIntent().getExtras().getParcelable("utilisateur");

        TextView titre = findViewById(R.id.titre);
        listview = findViewById(R.id.liste_annonces);

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


        SeekBar maSeekBar = findViewById(R.id.seekbar);
        seek_text = findViewById(R.id.affiche_prix);

        maSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
                seekValue = progress;
                addItemsListView();
            }
        });

        maSeekBar.setProgress(seekValue);
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
        listview.setAdapter(adapter);

        Log.d("LIST","itemList = " + itemList);

        //Ajout des annonces de l'utilisateur connecté
        for(Annonce a : itemList){
            if(a.getUtilisateur().equals(utilisateur.getMail())){
                utilisateur.ajoutAnnonce(a);
                Log.d("ADD ANNONCE", "Ajout de l'annonce " + a.getLibelle() + " à l'utilisateur " + utilisateur);
            }
        }
    }

    public void addItemsListView(){
        List<Annonce> list = new ArrayList<>();

        if(seekValue >= 1000){
            list.addAll(listAnnonces);
            seek_text.setText(seekValue +" + €");
        }else{
            seek_text.setText(seekValue +" €");
            for(Annonce a : listAnnonces){
                if(a.getPrix() <= seekValue){
                    list.add(a);
                }
            }
        }
        AnnonceAdapter adapter = new AnnonceAdapter(list, this);
        listview.setAdapter(adapter);
    }


}
