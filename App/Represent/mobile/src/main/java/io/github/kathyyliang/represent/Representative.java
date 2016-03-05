package io.github.kathyyliang.represent;

/**
 * Created by kathyyliang on 3/3/16.
 */
public class Representative {

    public String type;
    public String first_name;
    public String last_name;
    public String party;
    public String email;
    public String website;
    public String image;

    public Representative(String type, String first_name, String last_name, String party, String email, String website, String image) {
        this.type = type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.party = party;
        this.email = email;
        this.website = website;
        this.image = image;
    }

}
