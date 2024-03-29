package iut.r411.filrouge;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Modify by Fred on 16/02/2024.
 */

public class BottleAdapter extends BaseAdapter {
    private final String TAG = "fred " + getClass().getSimpleName();
    private List<Bottle> mangaBottles;
    private LayoutInflater mInflater;
    private Clickable callBackActivity;


    public BottleAdapter(List<Bottle> mangaBottles, Clickable callBackActivity) {
        this.mangaBottles = mangaBottles;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }

    public int getCount() {
        return mangaBottles.size();
    }
    public Object getItem(int position) {
        return mangaBottles.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts impossible (mémorisation des valeurs du ratingBar)
        //layoutItem = (ConstraintLayout) (convertView == null ? mInflater.inflate(R.layout.character_layout, parent, false) : convertView);
        layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.character_layout, parent, false);


        //(2) : Récupération des éléments
        TextView name = layoutItem.findViewById(R.id.name);
        TextView grade = layoutItem.findViewById(R.id.value);
        ImageView picture = layoutItem.findViewById(R.id.picture);
        RatingBar ratingBar = layoutItem.findViewById(R.id.ratingBar);

        //(3) : Renseignement des valeurs
        name.setText(mangaBottles.get(position).getNom());
        grade.setText((new DecimalFormat("##.##")).format( mangaBottles.get(position).getPrix()) ) ;


        Picasso.get().load(mangaBottles.get(position).getPhoto()).into(picture);
        ratingBar.setRating(mangaBottles.get(position).getPrix());

        ratingBar.setOnRatingBarChangeListener((ratingBar1, value, b) -> {
            Log.d(TAG, " seekbar change to " + value);
            callBackActivity.onRatingBarChange(position,value);
        });

        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }


}




