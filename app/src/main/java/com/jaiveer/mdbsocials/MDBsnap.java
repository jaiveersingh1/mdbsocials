package com.jaiveer.mdbsocials;

import java.io.Serializable;

public class MDBsnap implements Serializable {
    String caption;
    String userEmail;
    String imageURL;

    public MDBsnap()
    {

    }

    public MDBsnap(String caption, String userEmail, String imageURL) {
        this.caption = caption;
        this.userEmail = userEmail;
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getImageURL() {
        return imageURL;
    }
}
