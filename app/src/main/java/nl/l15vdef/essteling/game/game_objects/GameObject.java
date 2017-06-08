package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Maarten on 08/06/2017.
 */

public abstract class GameObject  {
    protected View v;
    public GameObject(View v){
        this.v = v;
    }

    public abstract void update(long updateTime);
    public abstract void draw(Canvas canvas, Paint p);
}
