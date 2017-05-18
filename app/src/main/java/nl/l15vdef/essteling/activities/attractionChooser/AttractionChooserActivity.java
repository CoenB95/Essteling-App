package nl.l15vdef.essteling.activities.attractionChooser;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities.data.Attraction;

public class AttractionChooserActivity extends Fragment {

    private ListView listOfAttractions;
    private ArrayAdapter adapterOfAttractionList;
    private List<Attraction> attractions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_attraction_chooser, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();


        //inital lize attracions
        attractions = new ArrayList<>();

        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));
        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));
        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));
        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));
        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));

        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));

        attractions.add(new Attraction(
                getResources().getDrawable(R.drawable.attraction_sjoris_en_de_draak_image),
                "Sjoris en de Draak","hoi"
        ));


        listOfAttractions = (ListView) v.findViewById(R.id.activity_about_list_of_attractions);


        adapterOfAttractionList = new AttractionChooserAdapter(v.getContext(),attractions);
        listOfAttractions.setAdapter(adapterOfAttractionList);



    }
}
