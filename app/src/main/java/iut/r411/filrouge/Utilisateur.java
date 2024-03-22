package iut.r411.filrouge;

public class Vendeur {
    private String nom;
    private String prenom;
    private String mail;
    private String numTel;
    private double note = 0.0;
    private double sommeNotes = 0.0;
    private int nbNotes = 0;
    private Annonce[] annonces = null;

    //Constructeur de la classe
    public Vendeur(String nom, String prenom, String mail, String numTel){
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numTel = numTel;
    }

    //Fonction de mise à jour de la note du vendeur
    public void majNote(){
        if(nbNotes!=0) {
            this.note = this.sommeNotes / this.nbNotes;
        }
    }

    //Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getNumTel() { return numTel; }
    public void setNumTel(String numTel) { this.numTel = numTel; }

    public double getNote(){ return note; }
    public double getSommeNotes() { return sommeNotes; }
    public int getNbNotes() { return nbNotes; }

    public Annonce[] getAnnonces() { return annonces; }
}
