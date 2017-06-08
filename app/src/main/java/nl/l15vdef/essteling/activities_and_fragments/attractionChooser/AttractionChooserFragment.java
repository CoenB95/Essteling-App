package nl.l15vdef.essteling.activities_and_fragments.attractionChooser;

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
import nl.l15vdef.essteling.activities_and_fragments.AttractionDetailFragment;
import nl.l15vdef.essteling.activities_and_fragments.MainActivity;
import nl.l15vdef.essteling.data.Attraction;

public class AttractionChooserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_attraction_chooser, container, false);

        //Initialize attractions
        List<Attraction> attractions = new ArrayList<>();
        attractions.addAll(Attraction.getAttractions());

        ArrayAdapter adapterOfAttractionList = new AttractionChooserAdapter(getContext(), attractions);

        ListView listOfAttractions = (ListView) v.findViewById(R.id.activity_about_list_of_attractions);
        listOfAttractions.setAdapter(adapterOfAttractionList);
        listOfAttractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttractionDetailFragment adf = new AttractionDetailFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, adf)
                        .addToBackStack(MainActivity.MAIN_BACKSTACK_TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
        return v;
    }
}
