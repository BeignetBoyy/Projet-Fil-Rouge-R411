package iut.r411.filrouge;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Annonce {

    private String date;
    private String titre;
    private String description;
    private double prix;
    private Etat etat;
    //private Vendeur vendeur;

    public Annonce(String titre, String description, double prix, Etat etat){
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.etat = etat;

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

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

    @Override
    public String toString() {
        return "Annonce{" +
                "date='" + date + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", etat=" + etat +
                '}';
    }
}
