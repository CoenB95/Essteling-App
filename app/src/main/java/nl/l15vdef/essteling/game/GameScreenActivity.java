package nl.l15vdef.essteling.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameScreenActivity extends AppCompatActivity{

    private GameView v;
    private float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        v = new GameView(this);
        x = 0;
        y = 0;
        setContentView(v);

        //Intent i = getIntent();
        //at = (Attraction) i.getSerializableExtra("AT");
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

}


