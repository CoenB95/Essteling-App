package nl.l15vdef.essteling.game.game_state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import nl.l15vdef.essteling.game.GameStateManager;

/**
 * Created by Maarten on 08/06/2017.
 */

public class PlayingGameState extends State {

    public PlayingGameState(View v, GameStateManager gm){
        super(v,gm);
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
