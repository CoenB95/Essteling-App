package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Maarten on 08/06/2017.
 */

public interface GameObject  {
    void update(long updateTime);
    void draw(Canvas canvas, Paint p);
}
