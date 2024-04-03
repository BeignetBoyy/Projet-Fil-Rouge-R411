package iut.r411.filrouge;

import android.app.ProgressDialog;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;


public class HttpAsyncGet<T>{
    private static final String TAG = "R411 " + HttpAsyncGet.class.getSimpleName();    //Pour affichage en cas d'erreur
    private final Class<T> clazz;
    private List<T> itemList;
    private final HttpHandler webService;


    /**
     *
     * Crée une nouvelle instance de la classe HttpAsyncGet pour effectuer une requête HTTP GET
     * vers l'URL spécifiée, avec les paramètres et le traitement de réponse spécifiés.
     *
     * @param url L'URL à laquelle envoyer la requête HTTP GET.
     * @param clazz La classe Java représentant le type d'objet attendu en réponse de la requête.
     * @param activity L'activité qui gère le traitement de la réponse de la requête.
     * @param progressDialog Le ProgressDialog optionnel pour afficher une indication de chargement pendant la requête.
     *
     * @see HttpHandler
     *      La classe qui gère les requêtes HTTP.
     * @see Runnable
     *      Une interface fonctionnelle qui représente une action qui peut être exécutée.
     *
     */
    public HttpAsyncGet(String url, Class<T> clazz, PostExecuteActivity activity, ProgressDialog progressDialog) {
        super();
        webService = new HttpHandler();
        this.clazz = clazz;
        if(progressDialog != null) onPreExecute( progressDialog );
        Runnable runnable = ()->{
            doInBackGround(url);
            activity.runOnUiThread( ()-> {
                if(progressDialog != null) progressDialog.dismiss();
                activity.onPostExecute(getItemResult());
            } );
        };
        Executors.newSingleThreadExecutor().execute( runnable );
    }


    /**
     *
     * Effectue une opération en arrière-plan en récupérant les données JSON à partir de l'URL spécifiée
     * et en les transformant en une liste d'objets du type attendu.
     *
     * @param urlAddress L'adresse URL à partir de laquelle récupérer les données JSON.
     *
     * @see ObjectMapper
     *      Une bibliothèque Java pour mapper des objets Java vers des objets JSON et vice versa.
     * @see JsonProcessingException
     *      Une exception qui peut être levée lors de la lecture ou de la manipulation de données JSON.
     *
     */

    public void doInBackGround(String urlAddress){
        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(urlAddress);
        ObjectMapper mapper = new ObjectMapper();
        try {
            itemList = mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> getItemResult() {
        return itemList;
    }

    /**
     *
     * Configure le ProgressDialog spécifié avant d'exécuter une tâche asynchrone.
     *
     * @param progressDialog Le ProgressDialog à configurer.
     *
     */

    public void onPreExecute( ProgressDialog progressDialog ) {
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }




    static class HttpHandler { //innerClass


        /**
         *
         * Effectue un appel de service en envoyant une requête HTTP GET à l'URL spécifiée.
         *
         * @param reqUrl L'URL de l'API à laquelle envoyer la requête.
         *
         * @see HttpURLConnection
         *      Une classe Java qui permet d'effectuer des demandes et des réponses HTTP.
         * @see URL
         *      Une classe qui représente une URL.
         * @see InputStream
         *      Une classe abstraite représentant un flux d'entrée de données.
         *
         * @return La réponse en tant que chaîne de caractères.
         *
         */

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
                Log.d("HTTP", "connexion OK");
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }


        /**
         *
         * Convertit un flux d'entrée en une chaîne de caractères.
         *
         * @param inputStream Le flux d'entrée à convertir en chaîne de caractères.
         *
         * @see BufferedReader
         *      Utilisé pour lire le flux d'entrée.
         * @see StringBuilder
         *      Utilisé pour construire la chaîne de caractères résultante.
         * @see IOException
         *      Une exception qui peut être levée lors de la lecture du flux d'entrée.
         *
         * @return La chaîne de caractères résultante.
         * 
         */

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.i(TAG,line);
                }
            }
            catch (IOException e) {  e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}