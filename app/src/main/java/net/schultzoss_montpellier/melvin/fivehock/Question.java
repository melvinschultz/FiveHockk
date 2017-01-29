package net.schultzoss_montpellier.melvin.fivehock;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Question {
    public int categorieId;
    public String enonce;
    public String reponse;

    public Question() {

    }

    public int getCategorieId() { return categorieId; }

    public void setCategorieId(int categorieId) { this.categorieId = categorieId; }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
