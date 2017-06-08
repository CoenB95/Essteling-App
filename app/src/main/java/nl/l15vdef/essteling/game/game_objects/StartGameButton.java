package nl.l15vdef.essteling.game.game_objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Maarten on 08/06/2017.
 */

public class StartGameButton extends GameObject {

    private Point p;
    private Point widthAndHeight;
    private Rect rect;
    private int screenWidht;
    private int screenHeight;

    public StartGameButton(View v, Point p, int with, int height) {
        super(v);
        this.p = p;
        this.widthAndHeight = new Point(with,height);
        rect = new Rect(p.x,p.y,p.x + with,p.y + height);
        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidht = size.x;
        screenHeight = size.y;
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        int color = p.getColor();

        p.setColor(v.getResources().getColor(android.R.color.black));
        canvas.drawRect(new Rect(rect.left - 3,rect.top - 3,rect.right + 3,rect.bottom +
                3),p);
        p.setColor(v.getResources().getColor(android.R.color.holo_red_light));
        canvas.drawRect(rect,p);
        Rect bounds = new Rect();
        String startGameText = "Start game";
        p.getTextBounds(startGameText, 0, startGameText.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();

        p.setColor(v.getResources().getColor(android.R.color.white));
        p.setTextSize(40);
        canvas.drawText("Start game",screenWidht/2 - width,screenHeight/2 - height,p);


        p.setColor(color);
    }

    public Point getP() {
        return p;
    }

    public Point getWidthAndHeight() {
        return widthAndHeight;
    }
}
