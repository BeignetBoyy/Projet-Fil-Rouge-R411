package iut.r411.filrouge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Annonce {

    private String id;
    private String date;
    private String titre;
    private String description;
    private double prix;
    private Etat etat;
    private Utilisateur utilisateur;

    public Annonce(String titre, String description, double prix, Etat etat, Utilisateur utilisateur){
        this.id = UUID.randomUUID().toString();
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.etat = etat;
        this.utilisateur = utilisateur;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        this.date = df.format(c);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id +
                "date='" + date + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", etat=" + etat +
                //", vendeur=" + vendeur +
                '}';
    }
}
