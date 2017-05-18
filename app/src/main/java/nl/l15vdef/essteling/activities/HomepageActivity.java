package nl.l15vdef.essteling.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities.attractionChooser.AttractionChooserActivity;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment fragment = new AttractionChooserActivity();
        ft.replace(R.id.attractionchooser,fragment);
        ft.commit();


    }
}
