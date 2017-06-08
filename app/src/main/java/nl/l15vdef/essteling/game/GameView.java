package nl.l15vdef.essteling.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.game.game_objects.Background;

public class GameView extends SurfaceView implements Runnable{


    private SurfaceHolder surfaceHolder;
    private Thread t = null;
    private float fps;
    private long updateTime;
    private boolean isRunning;
    private Paint paint;

    private GameStateManager gameStateManager;

    private Background b;

    public GameView(Context context) {
        super(context);

        //init gameStateManager
        gameStateManager = new GameStateManager(this);





        surfaceHolder = getHolder();
        paint = new Paint();
        t = new Thread(this);
        t.start();
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
        while (true){
            try {
                t.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume() {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (isRunning){
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            update();


            draw();


            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            updateTime = System.currentTimeMillis() - startFrameTime;
            updateTime += 1;
            if (updateTime > 0) {
                try {
                    fps = 1000 / updateTime;
                }catch (Exception e){
                    Log.e("GameScreen",updateTime + "");
                }
            }
        }
    }
    public void draw(){

        if(surfaceHolder.getSurface().isValid()) {

            Canvas canvas = surfaceHolder.lockCanvas();

            gameStateManager.draw(canvas,paint);

            //draw fps  \\debugging only
            paint.setTextSize(20);
            canvas.drawText("" + fps,20,20,paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void update(){
        gameStateManager.update(updateTime);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ontouch","toched");
        gameStateManager.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

}