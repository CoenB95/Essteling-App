package nl.l15vdef.essteling.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.l15vdef.essteling.R;

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

    public static List<Attraction> getAttractions(){
        List<Attraction> attractions = new ArrayList<>();

        attractions.add(new Attraction(
                R.drawable.attraction_sjoris_en_de_draak_image,
                "Sjoris en de Draak","hoi"
        ));
        attractions.add(new Attraction(
                R.drawable.anaconda_logo,
                "Anaconda","test"
        ));
        attractions.add(new Attraction(
                R.drawable.vliegende_duitser,
                "Vliegende Duitser","test"
        ));
        attractions.add(new Attraction(
                R.drawable.vogel_jazz,
                "Vogel Jazz","test"
        ));

        return attractions;
    }
}


