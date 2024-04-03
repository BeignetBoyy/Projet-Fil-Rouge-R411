package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements PostExecuteActivity<Utilisateur> {

    private List<Utilisateur> listUser;
    private EditText email;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listUser = new ArrayList<>();

        Button login_button = findViewById(R.id.login_button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Recupération des données d'utilisateur.json
        String url = "https://pirrr3.github.io/r411api/utilisateur.json";
        new HttpAsyncGet<>(url, Utilisateur.class, this, new ProgressDialog(LoginActivity.this) );

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Utilisateur u : listUser){
                    //Si un utilisateur de la list correspond au logins données
                    //on envoi les données à MainActivity avec un intent et on lance l'activité
                    if(u.getMail().equals(email.getText().toString()) && u.getPassword().equals(password.getText().toString())){

                        Intent intentUser = new Intent(LoginActivity.this, MainActivity.class);
                        intentUser.putExtra("utilisateur", u);
                        startActivity(intentUser);
                    }
                }
            }
        });
    }

    @Override
    public void onPostExecute(List<Utilisateur> itemList) {
        //On ajoute tous les utilisateurs du json dans une list
        listUser.addAll(itemList);
    }
}