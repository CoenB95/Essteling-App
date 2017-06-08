package nl.l15vdef.essteling.game.game_state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Maarten on 08/06/2017.
 */

public interface State {

    void update(long updateTime);
    void draw(Canvas canvas, Paint p);
    void onTouchEvent(MotionEvent event);
}
