package nl.l15vdef.essteling.activities.data;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Maarten on 18/05/2017.
 */

public class Attraction implements Serializable{
    private int imageId;
    private String name;
    private String discription;

    public Attraction(int imageId, String name, String discription) {
        this.imageId = imageId;
        this.name = name;
        this.discription = discription;
    }

    public int getImage() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }
}


