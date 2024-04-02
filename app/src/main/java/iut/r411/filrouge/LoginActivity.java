package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        String url = "https://pirrr3.github.io/r411api/utilisateur.json";
        new HttpAsyncGet<>(url, Utilisateur.class, this, new ProgressDialog(LoginActivity.this) );

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Utilisateur u : listUser){
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
        listUser.addAll(itemList);
    }
}