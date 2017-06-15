package nl.l15vdef.essteling.game.game_state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.sql.SQLException;

import nl.l15vdef.essteling.game.GameStateManager;

/**
 * Created by Maarten on 08/06/2017.
 */

public abstract class State {

    protected View v;
    protected GameStateManager gm;

    public State(View v, GameStateManager gm){
        this.v = v;
        this.gm = gm;
    }

    public abstract void init(Object... objects) throws SQLException, ClassNotFoundException;
    public abstract void update(long updateTime);
    public abstract void draw(Canvas canvas, Paint p);
    public abstract boolean onTouchEvent(MotionEvent event);
}
