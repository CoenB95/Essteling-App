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
import nl.l15vdef.essteling.game.game_objects.BackgroundMoving;
import nl.l15vdef.essteling.game.game_objects.StartGameButton;

/**
 * Created by Maarten on 08/06/2017.
 */

public class StartingGameState extends State {
    private BackgroundMoving b;
    private StartGameButton gameButton;
    private boolean starting;

    public StartingGameState(View v, GameStateManager gm) {
        super(v,gm);
        init();
    }

    public void init(Object... objects){
        //init background
        if(objects.length >= 1){
            int score =  (int) objects[0];
        }
        b = new BackgroundMoving(v,gm);
        gameButton = new StartGameButton(v,new Point(gm.getScreenDimensions().x/2 - 250,gm.getScreenDimensions().y/2 - 100),500,200,"Start game",android.R.color.holo_red_light);
        starting = false;
    }

    @Override
    public void update(long updateTime) {
        if(starting){
            gm.setState(GameStateManager.PLAYING_STATE);
        }
        b.update(updateTime);
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // draw background
        b.draw(canvas,p);
        gameButton.draw(canvas,p);

        p.setTextSize(70);
        Rect bounds = new Rect();
        String startGameText = "Anaconda";
        p.getTextBounds(startGameText, 0, startGameText.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        canvas.drawText(startGameText,gm.getScreenDimensions().x/2 - width/2,gm.getScreenDimensions().y/2 - 300,p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int left = gameButton.getP().x;
        int top =gameButton.getP().y;

        if(new Rect(left,top,left + gameButton.getWidthAndHeight().x,top + gameButton.getWidthAndHeight().y).contains(x,y)){
            starting = true;
        }
        return false;
    }
}
