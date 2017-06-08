package nl.l15vdef.essteling.activities_and_fragments.helpMenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_help_menu, container, false);

        list = new ArrayList<>();
        list.add(new FAQ(getContext().getResources().getString(R.string.faq_q_launchGame),
                getContext().getResources().getString(R.string.faq_a_launchGame)));

        adapter = new HelpMenuAdapter(v.getContext(), list);

        listView = (ListView) v.findViewById(R.id.ActivityHelpMenu_listViewFAQ);
        listView.setAdapter(adapter);
        return v;
    }

}
