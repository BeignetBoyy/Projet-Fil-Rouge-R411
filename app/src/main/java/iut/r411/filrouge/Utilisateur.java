package iut.r411.filrouge;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String mail;
    private String numTel;
    private float note = 0;
    private float sommeNotes = 0;
    private int nbNotes = 0;
    private List<Annonce> annonces = new ArrayList<>();

    //Constructeur de la classe
    public Utilisateur(String nom, String prenom, String mail, String numTel){
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numTel = numTel;
    }

    //Fonction de mise à jour de la note du vendeur
    public void majNote(float note){
        this.sommeNotes += note;
        this.nbNotes++;
        if(nbNotes!=0) { this.note = this.sommeNotes / this.nbNotes; }
        Log.i("Utilisateur","La nouvelle note de l'utilisateur est : "+note);
    }

    //Fonctions de gestion des annonces
    public void ajoutAnnonce(Annonce annonce){
        if(this.annonces.contains(annonce)){ Log.e("Utilisateur","L'utilisateur possède déjà cette annonce."); }
        else{ this.annonces.add(annonce); }
    }

    public void retirerAnnonce(Annonce annonce){
        if(this.annonces.contains(annonce)){ this.annonces.remove(annonce); }
        else{ Log.e("Utilisateur","L'utilisateur ne possède pas cette annonce."); }
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

    public float getNote(){ return note; }
    public float getSommeNotes() { return sommeNotes; }
    public int getNbNotes() { return nbNotes; }

    public List<Annonce> getAnnonces() { return annonces; }
}
