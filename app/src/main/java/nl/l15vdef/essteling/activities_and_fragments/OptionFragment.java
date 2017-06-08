package nl.l15vdef.essteling.activities_and_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.Locale;

import nl.l15vdef.essteling.R;

import static android.content.Context.MODE_PRIVATE;

public class OptionFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Switch checkWifiOnly;
    private Boolean mayAccesInternet;
    private Spinner languageSelection;
    private Configuration config ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_option_fragment, container, false);

        checkWifiOnly = (Switch) view.findViewById(R.id.option_wifi_check);
        languageSelection = (Spinner) view.findViewById(R.id.language_select_spin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.country_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        languageSelection.setAdapter(adapter);
        languageSelection.setOnItemSelectedListener(this);

        checkWifiConnection();
        return view;
    }

    public void checkWifiConnection(){
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE);
        checkWifiOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    blockMobileInternet();
                }else{
                    mayAccesInternet = true;
                    editor.putBoolean("mayAcces" , mayAccesInternet);
                    editor.commit();
                }

            }
        });

        //check the current state before we display the screen
        if(checkWifiOnly.isChecked()){
            blockMobileInternet();
        }
        else {
            mayAccesInternet = true;
            editor.putBoolean("mayAcces" , mayAccesInternet);
            editor.commit();
        }
    }

    public void blockMobileInternet(){
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE);
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            mayAccesInternet = true;
            editor.putBoolean("mayAcces" , mayAccesInternet);
            editor.commit();
        }
        else{
            mayAccesInternet = false;
            editor.putBoolean("mayAcces" , mayAccesInternet);
            editor.commit();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        System.out.println("Pos: " + pos + " ID: )" + id);
        String lang = "";
        if(pos == 0){
            lang = "en";
        }
        if(pos == 1){
            lang = "nl";
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());

        this.getActivity().invalidateOptionsMenu();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }





}
