package iut.r411.filrouge;

public class Vendeur {
    private String nom;
    private String prenom;
    private String mail;
    private String numTel;
    private double rating = 0.0;
    private double sumRatings = 0.0;
    private int numRatings = 0;

    public Vendeur(String nom, String prenom, String mail, String numTel){
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numTel = numTel;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public double getSumRatings() {
        return sumRatings;
    }

    public double getRating(){
        return rating;
    }

    public void updateRating(){
        if(numRatings!=0) {
            this.rating = this.sumRatings / this.numRatings;
        }
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
