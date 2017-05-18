package nl.l15vdef.essteling.activities.data;

import android.graphics.drawable.Drawable;

/**
 * Created by Maarten on 18/05/2017.
 */

public class Attraction {
    private Drawable image;
    private String name;
    private String discription;

    public Attraction(Drawable image, String name, String discription) {
        this.image = image;
        this.name = name;
        this.discription = discription;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }
}


