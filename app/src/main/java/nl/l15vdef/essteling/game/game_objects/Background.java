package nl.l15vdef.essteling.game.game_objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import nl.l15vdef.essteling.R;

/**
 * Created by Maarten on 08/06/2017.
 */

public class Background extends GameObject {
    private Bitmap background;
    private Bitmap scoreBackground;
    public Background(View v) {
        super(v);

        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        background = BitmapFactory.decodeResource(v.getResources(), R.drawable.snake_background2);
        background = Bitmap.createScaledBitmap(background,width,height,true);

        scoreBackground = BitmapFactory.decodeResource(v.getResources(),R.drawable.scorebar_background);
        scoreBackground = Bitmap.createScaledBitmap(scoreBackground,
                (width/4),height,true
                );
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.drawBitmap(background,0,0,p);
        canvas.drawBitmap(scoreBackground,0,0,p);
    }
}
