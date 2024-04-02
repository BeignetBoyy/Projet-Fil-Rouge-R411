package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
    private List<Annonce> filteredList; // New list to hold filtered items
    private AnnonceAdapter adapter;
    private EditText searchBar;

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
        searchBar = findViewById(R.id.searchBar);

        listAnnonces = new ArrayList<>();
        filteredList = new ArrayList<>();


        String url = "https://pirrr3.github.io/r411api/annonce.json";
        new HttpAsyncGet<>(url, Annonce.class, this, new ProgressDialog(MainActivity.this) );

        adapter = new AnnonceAdapter(filteredList, this);
        listview.setAdapter(adapter);

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
                filteredList.clear();
                seekValue = progress;
                for(Annonce annonce : listAnnonces) {
                    if(annonce.getPrix() <= progress) filteredList.add(annonce);
                }
                addItemsListView();
            }
        });

        maSeekBar.setProgress(seekValue);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


    }

    private void filter(String query) {
        filteredList.clear();
        for (Annonce annonce : listAnnonces) {
            if (annonce.getLibelle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(annonce);
            }
        }
        updateListView(filteredList);
    }

    private void updateListView(List<Annonce> filteredList) {
        AnnonceAdapter adapter = new AnnonceAdapter(filteredList, this);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClicItem(int index) {
        Log.i("Click", "Click ! : " + index);

        int itemIndex = findIndexInList(index);
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

        if (filteredList.isEmpty()){
            filteredList.addAll(listAnnonces);
        }
    }

    public void addItemsListView(){
        filteredList.clear();

        if(seekValue >= 1000){
            filteredList.addAll(listAnnonces);
            seek_text.setText(seekValue +" + €");
        }else{
            seek_text.setText(seekValue +" €");
            for(Annonce a : listAnnonces){
                if(a.getPrix() <= seekValue){
                    filteredList.add(a);
                }
            }
        }
        updateListView(filteredList);
    }

    private int findIndexInList(int index) {
        Annonce characterToFind = filteredList.get(index);
        for (int i = 0; i < listAnnonces.size(); i++) {
            if (listAnnonces.get(i).equals(characterToFind)) {
                return i;
            }
        }
        return -1;
    }

}
