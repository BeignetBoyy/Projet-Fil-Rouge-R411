package iut.r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityDelME extends AppCompatActivity implements PostExecuteActivity<Bottle>,Clickable {

    //TODO REMOVE
    private static final List<Bottle> MANGA_BOTTLES = new ArrayList<>(); //the complete list
    private final List<Bottle> displayedBottles = new ArrayList<>(); //the displayed list
    private BottleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_me);

        String url = "https://pirrr3.github.io/r411api/bottles.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, Bottle.class, this, new ProgressDialog(ActivityDelME.this) );
    }

    @Override
    public void onClicItem(int itemIndex) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value) {

    }

    @Override
    public void onPostExecute(List<Bottle> itemList) {
        Log.d("LIST","itemList = " + itemList);
    }
}