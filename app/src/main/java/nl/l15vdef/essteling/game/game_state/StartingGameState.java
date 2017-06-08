package nl.l15vdef.essteling.game.game_state;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import nl.l15vdef.essteling.game.GameStateManager;
import nl.l15vdef.essteling.game.game_objects.Background;
import nl.l15vdef.essteling.game.game_objects.StartGameButton;

/**
 * Created by Maarten on 08/06/2017.
 */

public class StartingGameState implements State {
    private Background b;
    private StartGameButton gameButton;
    private GameStateManager gm;

    public StartingGameState(View v, GameStateManager gm) {
        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        //init background
        b = new Background(v);
        gameButton = new StartGameButton(v,new Point(width/2 - 250,height/2 - 100),500,200);
        this.gm = gm;
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // draw background
        b.draw(canvas,p);
        gameButton.draw(canvas,p);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int left = gameButton.getP().x;
        int top =gameButton.getP().y;

        if(new Rect(left,top,left + gameButton.getWidthAndHeight().x,top + gameButton.getWidthAndHeight().y).contains(x,y)){
            gm.setState(GameStateManager.PLAYING_STATE);
        }
    }
}