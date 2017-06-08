package nl.l15vdef.essteling.game.game_state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nl.l15vdef.essteling.game.GameStateManager;
import nl.l15vdef.essteling.game.game_objects.Background;
import nl.l15vdef.essteling.game.game_objects.PickUp;
import nl.l15vdef.essteling.game.game_objects.Raster;

/**
 * Created by Maarten on 08/06/2017.
 */

public class PlayingGameState extends State {

    public final static int row = 15;
    public final static int col = 10;

    public int pixelPerRow;
    public int pixelPerCol;
    public int offSet;

    private Background b;
    private Raster r;
    private List<PickUp> pickUps;
    private long timer;

    public PlayingGameState(View v, GameStateManager gm){
        super(v,gm);
        b = new Background(v);
        r = new Raster(v,gm);

        pickUps = new ArrayList<>();
        timer = System.currentTimeMillis();

        offSet = gm.getScreenDimensions().x/4;
        int width = offSet * 3;
        pixelPerRow = width/row;
        pixelPerCol = gm.getScreenDimensions().y / col;
    }

    @Override
    public void update(long updateTime) {
        if(System.currentTimeMillis() - timer > 5000){
            pickUps.add(new PickUp(v,new Point( (int) (Math.random() * row), (int) (Math.random() * col)),this));
        }
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        b.draw(canvas,p);
        r.draw(canvas,p);
        for (PickUp pickUp : pickUps) {
            pickUp.draw(canvas,p);
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
