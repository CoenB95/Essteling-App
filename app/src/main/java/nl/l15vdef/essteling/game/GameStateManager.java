package nl.l15vdef.essteling.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import nl.l15vdef.essteling.game.game_state.PlayingGameState;
import nl.l15vdef.essteling.game.game_state.StartingGameState;
import nl.l15vdef.essteling.game.game_state.State;

/**
 * Created by Maarten on 08/06/2017.
 */

public class GameStateManager {

    private View v;

    public final static int STARTING_STATE = 0;
    public final static int PLAYING_STATE  = 1;

    private int currentState;

    private List<State> gameStates;
    public GameStateManager(View v) {
        this.v = v;
        gameStates = new ArrayList<>();

        gameStates.add(new StartingGameState(v,this));
        gameStates.add(new PlayingGameState(v,this));

        currentState = 0;
    }

    public void update(long updateTime){
        gameStates.get(currentState).update(updateTime);
    }

    public void draw(Canvas canvas, Paint p){
        gameStates.get(currentState).draw(canvas,p);
    }

    public void onTouchEvent(MotionEvent event) {
        gameStates.get(currentState).onTouchEvent(event);
    }

    public void setState(int state) {
        currentState = state;
    }

    public Point getScreenDimensions(){
        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
