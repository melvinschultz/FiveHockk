package net.schultzoss_montpellier.melvin.fivehock;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public String avatar;
    public int hockcoins;
    public int xp;

    public User() {

    }

    public User(String username, String email, String avatar, int hockcoins, int xp) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.hockcoins = hockcoins;
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
