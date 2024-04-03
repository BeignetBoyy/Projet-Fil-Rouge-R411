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
     * @param url
     * @param clazz
     * @param activity
     * @param progressDialog
     *
     * @see HttpHandler
     * @see Runnable
     * @see
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
     * @param urlAddress
     *
     * @see ObjectMapper
     * @see JsonProcessingException
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
     * @param progressDialog
     */

    public void onPreExecute( ProgressDialog progressDialog ) {
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }




    static class HttpHandler { //innerClass


        /**
         *
         * @param reqUrl
         *
         * @see HttpURLConnection
         * @see URL
         * @see InputStream
         *
         * @return response
         *
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
         * @param inputStream
         *
         * 
         *
         * @return
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