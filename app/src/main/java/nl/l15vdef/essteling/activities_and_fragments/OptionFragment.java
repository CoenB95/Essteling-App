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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import nl.l15vdef.essteling.R;

import static android.content.Context.MODE_PRIVATE;

public class OptionFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Switch checkWifiOnly;
    private Boolean mayAccesInternet;
    private Spinner languageSelection;
    private Spinner difSpinner;
    private Configuration config ;

    private TextView currentName;
    private EditText nameEdit;
    private Button nameButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_option_fragment, container, false);
        getActivity().setTitle(getResources().getString(R.string.settings));
        final SharedPreferences prefs = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE);
        final SharedPreferences.Editor edit = getActivity().getSharedPreferences("Var_Score_Data", MODE_PRIVATE).edit();
        final SharedPreferences prefss = getActivity().getSharedPreferences("Var_Score_Data", MODE_PRIVATE);

        currentName = (TextView) view.findViewById(R.id.name_current_name);
        currentName.setText(prefss.getString("Name" , "No name set!"));

        nameEdit = (EditText) view.findViewById(R.id.name_change_name);
        nameButton = (Button) view.findViewById(R.id.name_button_name);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEdit.getText().toString().trim();
                if(!newName.equals("")){
                    edit.putString("Name" , newName);
                    edit.apply();
                    currentName.setText(prefss.getString("Name" , "No name set!"));
                }
            }
        });

        checkWifiOnly = (Switch) view.findViewById(R.id.option_wifi_check);

        checkWifiOnly.setChecked(prefs.getBoolean("Enabled" , false));

        languageSelection = (Spinner) view.findViewById(R.id.language_select_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.country_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSelection.setAdapter(adapter);
        String langu = prefs.getString("Lang", "en");
        if(langu.equals("en")){
            languageSelection.setSelection(0);
        }
        if(langu.equals("nl")){
            languageSelection.setSelection(1);
        }
        languageSelection.setOnItemSelectedListener(this);

        difSpinner = (Spinner) view.findViewById(R.id.dif_select_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.moeilijkheid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difSpinner.setAdapter(adapter2);
        String diff = prefss.getString("Diff" , "Child");

        if(diff.equals("Child")){
            difSpinner.setSelection(0);
        }
        if(diff.equals("Teenager")){
            difSpinner.setSelection(1);
        }
        if(diff.equals("Adult")){
            difSpinner.setSelection(2);
        }
        difSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final SharedPreferences.Editor editor = getActivity().getSharedPreferences("Var_Score_Data", MODE_PRIVATE).edit();

                if(position == 0){
                    editor.putString("Diff" , "Child");
                    editor.apply();
                }
                if(position == 1){
                    editor.putString("Diff" , "Teenager");
                    editor.apply();
                }
                if(position == 2){
                    editor.putString("Diff" , "Adult");
                    editor.apply();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                    editor.putBoolean("Enabled" , true);
                    editor.apply();
                }else{
                    mayAccesInternet = true;
                    editor.putBoolean("mayAcces" , mayAccesInternet);
                    editor.putBoolean("Enabled" , false);
                    editor.apply();
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
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("Var_internet_acces", MODE_PRIVATE);
        System.out.println("Pos: " + pos + " ID: )" + id);

        String lang = "";
        if(pos == 0){
            lang = "en";
            editor.putString("Lang" , lang);
            editor.apply();
        }
        if(pos == 1){
            lang = "nl";
            editor.putString("Lang" , lang);
            editor.apply();
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
