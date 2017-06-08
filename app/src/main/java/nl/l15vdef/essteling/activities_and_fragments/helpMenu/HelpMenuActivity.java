package nl.l15vdef.essteling.activities_and_fragments.helpMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.data.FAQ;

public class HelpMenuActivity extends Fragment {

    private ListView listView;
    private ArrayList<FAQ> list;
    private ArrayAdapter adapter;

    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.help));
        return inflater.inflate(R.layout.activity_help_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        View v = getView();


        list = new ArrayList<>();
        list.add(new FAQ("Is dit een test?","Dit is inderdaad een test. Dit antwoord staat ook nog" +
                " vol met willekeurige tekst, om te testen hoe de adapter hierop reageert."));
        list.add(new FAQ("Is dit een test om te kijken hoe de adapter omgaat met een langere vraag?",
                "Dit is inderderdaad een test om te kijken hoe de adapter omgaat met een langere "+
                "vragen en misschien zelfs ook teksten, ligt eraan hoeveel ik hier bij kan verzinnen, " +
                " wat eigenlijk vrij weinig is."));



        listView = (ListView) v.findViewById(R.id.ActivityHelpMenu_listViewFAQ);


        adapter = new HelpMenuAdapter(v.getContext(),list);
        listView.setAdapter(adapter);

    }

}
