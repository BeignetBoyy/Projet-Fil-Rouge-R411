package iut.r411.filrouge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AnnonceAdapter extends BaseAdapter {

    private List<Annonce> annonces;
    private LayoutInflater mInflater;
    private Clickable callBackActivity;


    public AnnonceAdapter(List<Annonce> annonces, Clickable callBackActivity) {
        this.annonces = annonces;
        this.callBackActivity = callBackActivity;
        mInflater = LayoutInflater.from(callBackActivity.getContext());
    }
    @Override
    public int getCount() {
        return annonces.size();
    }

    @Override
    public Object getItem(int position) {
        return annonces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;

        //(1) : Réutilisation des layouts impossible (mémorisation des valeurs du ratingBar)
        //layoutItem = (ConstraintLayout) (convertView == null ? mInflater.inflate(R.layout.character_layout, parent, false) : convertView);
        layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.annonce_layout, parent, false);

        //(2) : Récupération des éléments
        TextView libelle = layoutItem.findViewById(R.id.libelle);
        TextView description = layoutItem.findViewById(R.id.description);
        TextView prix = layoutItem.findViewById(R.id.prix);
        ImageView image = layoutItem.findViewById(R.id.picture);

        //(3) : Renseignement des valeurs
        libelle.setText(annonces.get(position).getLibelle());
        description.setText(annonces.get(position).getDescription());
        prix.setText(annonces.get(position).getPrix() + " €");
        Picasso.get().load(annonces.get(position).getImage()).into(image);


        layoutItem.setOnClickListener( click -> callBackActivity.onClicItem(position));
        //On retourne l'item créé.
        return layoutItem;
    }
}
