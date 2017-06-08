package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

/**
 * Created by Maarten on 08/06/2017.
 */

public class Snake extends GameObject{
    private Point rasterChords;

    public Snake(View v,Point rasterChords) {
        super(v);
        this.rasterChords = rasterChords;
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {

    }

    public Point getRasterChords() {
        return rasterChords;
    }
}
