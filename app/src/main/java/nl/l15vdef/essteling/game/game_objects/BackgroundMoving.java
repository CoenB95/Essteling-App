package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.game.GameStateManager;

/**
 * Created by Maarten on 11/06/2017.
 */

public class BackgroundMoving extends GameObject {

    private Bitmap background;
    private Point position;
    private GameStateManager gm;
    public BackgroundMoving(View v, GameStateManager gm) {
        super(v);
        this.gm = gm;
        this.position = new Point(0,0);
        background = BitmapFactory.decodeResource(v.getResources(), R.drawable.no_watermark);
        background = Bitmap.createScaledBitmap(background,
                gm.getScreenDimensions().x,
                gm.getScreenDimensions().y,true);

    }

    @Override
    public void update(long updateTime) {
            position.x += 1;
        if(position.x + gm.getScreenDimensions().x < 0){
            position.x = gm.getScreenDimensions().x;
        }else if(position.x > gm.getScreenDimensions().x){
            position.x = 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.drawBitmap(background,position.x,position.y,p);
        if (position.x < 0){
            canvas.drawBitmap(background,position.x + gm.getScreenDimensions().x,position.y,p);
        }else {
            canvas.drawBitmap(background,position.x - gm.getScreenDimensions().x,position.y,p);
        }
    }
}
