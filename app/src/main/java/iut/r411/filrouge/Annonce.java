package iut.r411.filrouge;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Annonce implements Parcelable {

    private String id;
    private String date_creation;
    private String libelle;
    private String description;
    private double prix;
    private Etat etat;
    private String utilisateur;
    private String image;

    public Annonce(){
        super();
    }

    /*public Annonce(String titre, String description, double prix, Etat etat, Utilisateur utilisateur){
        this.id = UUID.randomUUID().toString();
        this.libelle = titre;
        this.description = description;
        this.prix = prix;
        this.etat = etat;
        this.utilisateur = utilisateur;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        this.date_creation = df.format(c);
    }*/

    protected Annonce(Parcel in) {
        libelle = in.readString();

    }

    public static final Creator<Annonce> CREATOR = new Creator<Annonce>() {
        @Override
        public Annonce createFromParcel(Parcel in) {
            return new Annonce(in);
        }

        @Override
        public Annonce[] newArray(int size) {
            return new Annonce[size];
        }
    };

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = "https://pirrr3.github.io/r411api/images/" + image;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id +
                "date='" + date_creation + '\'' +
                ", titre='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", etat=" + etat +
                ", utilisateur=" + utilisateur +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(libelle);
    }
}
