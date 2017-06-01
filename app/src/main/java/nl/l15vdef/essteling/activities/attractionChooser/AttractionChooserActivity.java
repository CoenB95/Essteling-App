package nl.l15vdef.essteling.activities.attractionChooser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities.AttractionDetailFragment;
import nl.l15vdef.essteling.data.Attraction;

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

        attractions.addAll(Attraction.getAttractions());



        listOfAttractions = (ListView) v.findViewById(R.id.activity_about_list_of_attractions);


        adapterOfAttractionList = new AttractionChooserAdapter(v.getContext(),attractions);
        listOfAttractions.setAdapter(adapterOfAttractionList);

        listOfAttractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttractionDetailFragment adf = new AttractionDetailFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, adf)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

    }
}
