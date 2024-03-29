package iut.r411.filrouge;


import android.util.Log;

/**
 * Modify by Fred on 22/03/2024.
 */
public class Bottle {

    private final String TAG = "frallo "+getClass().getSimpleName();
    private String nom;
    private String description;  //depends of settings language
    private float prix;
    private int quantite;
    private String photo;



    public Bottle() {
        super();
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }
    public void setPrix(float value) {
        this.prix = value;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = "http://edu.info06.net/studients/bottles/pictures/" + photo;
        Log.d(TAG, this.photo);
    }


    @Override
    public String toString(){
        return getNom() + "(" +  getPhoto() +  ")";
    }


}