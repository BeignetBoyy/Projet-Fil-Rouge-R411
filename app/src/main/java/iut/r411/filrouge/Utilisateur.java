package iut.r411.filrouge;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Parcelable {
    private String password;
    private String nom;
    private String prenom;
    private String mail;
    private String numTel;
    private float note = 0;
    private float sommeNotes = 0;
    private int nbNotes = 0;
    private ArrayList<Annonce> annonces = new ArrayList<>();

    public Utilisateur(){
        super();
        Log.d("ANNONCES", annonces.toString());
    }

    //Constructeur de la classe
    public Utilisateur(String nom, String prenom, String mail, String numTel, String password){
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numTel = numTel;
        this.password = password;
    }

    protected Utilisateur(Parcel in) {
        mail = in.readString();
        password = in.readString();
        nom = in.readString();
        prenom = in.readString();
        numTel = in.readString();
        note = in.readFloat();
        sommeNotes = in.readFloat();
        nbNotes = in.readInt();
        annonces = in.createTypedArrayList(Annonce.CREATOR);
    }


    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };

    //Fonction de mise à jour de la note du vendeur
    public void majNote(float note){
        this.sommeNotes += note;
        this.nbNotes++;
        if(nbNotes!=0) { this.note = this.sommeNotes / this.nbNotes; }
        Log.i("Utilisateur","La nouvelle note de l'utilisateur est : "+this.note);
    }

    //Fonctions de gestion des annonces
    public void ajoutAnnonce(Annonce annonce){
        annonces.add(annonce);
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
    public String getPassword() {
        return password;
    }
    public List<Annonce> getAnnonces() { return annonces; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "password='" + password + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", numTel='" + numTel + '\'' +
                ", note=" + note +
                ", sommeNotes=" + sommeNotes +
                ", nbNotes=" + nbNotes +
                ", annonces=" + annonces +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mail);
        dest.writeString(password);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(numTel);
        dest.writeFloat(note);
        dest.writeFloat(sommeNotes);
        dest.writeInt(nbNotes);
        dest.writeTypedList(annonces);
    }
}
