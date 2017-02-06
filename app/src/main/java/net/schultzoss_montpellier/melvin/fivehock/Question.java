package net.schultzoss_montpellier.melvin.fivehock;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Question {
    public String id;
    public String bonneReponse;
    public String categorie;
    public String enonce;
    public String reponseA;
    public String reponseB;
    public String reponseC;
    public String reponseD;
    public String reponseE;

    public Question() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBonneReponse() {
        return bonneReponse;
    }

    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getReponseA() {
        return reponseA;
    }

    public void setReponseA(String reponseA) {
        this.reponseA = reponseA;
    }

    public String getReponseB() {
        return reponseB;
    }

    public void setReponseB(String reponseB) {
        this.reponseB = reponseB;
    }

    public String getReponseC() {
        return reponseC;
    }

    public void setReponseC(String reponseC) {
        this.reponseC = reponseC;
    }

    public String getReponseD() {
        return reponseD;
    }

    public void setReponseD(String reponseD) {
        this.reponseD = reponseD;
    }

    public String getReponseE() {
        return reponseE;
    }

    public void setReponseE(String reponseE) {
        this.reponseE = reponseE;
    }
}
