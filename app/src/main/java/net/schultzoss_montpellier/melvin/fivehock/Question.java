package net.schultzoss_montpellier.melvin.fivehock;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Question {
    public int id;
    public int idTheme;
    public int idCategorie;
    public int idReponse;
    public String question;

    public Question() {

    }

    /*public Question(int id, int idTheme, int idCategorie, int idReponse, String question) {
        this.id = id;
        this.idTheme = idTheme;
        this.idCategorie = idCategorie;
        this.idReponse = idReponse;
        this.question = question;
    }*/

    public int getId() {
        return id;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public String getQuestion() {
        return question;
    }
}
