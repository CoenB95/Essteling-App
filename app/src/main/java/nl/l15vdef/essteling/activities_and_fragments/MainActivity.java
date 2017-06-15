package nl.l15vdef.essteling.activities_and_fragments;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities_and_fragments.attractionChooser.AttractionChooserFragment;
import nl.l15vdef.essteling.activities_and_fragments.helpMenu.HelpMenuActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MAIN_BACKSTACK_TAG = "mainstack";
    private static boolean starting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , 2);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.INTERNET} , 3);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_NETWORK_STATE} ,4);

        if (savedInstanceState == null) {
            Fragment fragment = new HomepageFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            //ft.addToBackStack()
            ft.commit();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        final SharedPreferences.Editor editor = getSharedPreferences("Var_Score_Data", MODE_PRIVATE).edit();
        final SharedPreferences sharedPreferences = getSharedPreferences("Var_Score_Data", MODE_PRIVATE);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                editor.putString("Name",name);
                editor.apply();
            }
        });
        Log.d("name",sharedPreferences.getString("Name","Anonymous"));
        if(sharedPreferences.getString("Name", "Anonymous") == "Anonymous") {
            builder.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings)
            displaySelectedScreen(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){
        getSupportFragmentManager().popBackStack(MAIN_BACKSTACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(id){
            case R.id.action_settings:
                ft.replace(R.id.content_main, new OptionFragment());
                ft.addToBackStack(MAIN_BACKSTACK_TAG);
                break;
            case R.id.menu_attracties:
                ft.replace(R.id.content_main, new AttractionChooserFragment());
                ft.addToBackStack(MAIN_BACKSTACK_TAG);
                break;
            case R.id.menu_help:
                ft.replace(R.id.content_main, new HelpMenuActivity());
                ft.addToBackStack(MAIN_BACKSTACK_TAG);
                break;
            case R.id.menu_home:
                //Don't add home to backstack, we already went back.
                break;
            case R.id.menu_over:
                ft.replace(R.id.content_main, new AboutFragment());
                ft.addToBackStack(MAIN_BACKSTACK_TAG);
                break;
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);


        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null)
            bluetoothAdapter.disable();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}