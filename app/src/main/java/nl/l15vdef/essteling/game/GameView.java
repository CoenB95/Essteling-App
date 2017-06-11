package nl.l15vdef.essteling.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.game.game_objects.Background;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback{



    private SurfaceHolder surfaceHolder;

    private Thread t = null;
    private boolean isRunning;

    private double fps;
    private long updateTime;

    private final int fpsCheckTime = 500;



    private Paint paint;

    private GameStateManager gameStateManager;

    private Background b;



    public GameView(Context context){
        super(context);

        //init gameStateManager
        gameStateManager = new GameStateManager(this);

        fps = 0;



        surfaceHolder = getHolder();
        surfaceHolder.setFixedSize(1920,1080);
        surfaceHolder.addCallback(this);
    }

    public void run() {
        long lastLoopTime = System.nanoTime();

        long lastFpsTime = 0;
        int frame = 0;
        Looper.prepare();

        while (isRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            updateTime = (now - lastLoopTime)  / 1000000;
            lastLoopTime = now;


            // update the frame counter
            lastFpsTime += updateTime;



            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= fpsCheckTime) {
                lastFpsTime = 0;
                fps = frame *  ((double) 1000/ (double) fpsCheckTime);
                frame = 0;
            }else frame++;

            //long updateTiming = System.currentTimeMillis();

            update();

            //updateTiming = System.currentTimeMillis() - updateTiming;

            //long drawTime = System.currentTimeMillis();

            draw();

            //drawTime = System.currentTimeMillis() - drawTime;


            //Log.d("Game","total time = " + this.updateTime + "\nupdate time =  " + updateTiming + "\ndraw time =  " + drawTime);
        }
    }
    public void draw(){
        if(surfaceHolder.getSurface().isValid()) {

            Canvas canvas = surfaceHolder.lockCanvas();

            if(canvas != null) {

                int color = paint.getColor();

                Bitmap b = Bitmap.createBitmap(gameStateManager.getScreenDimensions().x, gameStateManager.getScreenDimensions().y, Bitmap.Config.RGB_565);
                Canvas newCanvas = new Canvas(b);


                gameStateManager.draw(newCanvas, paint);
                canvas.drawBitmap(b, 0, 0, paint);

                //draw fps  \\debugging only
                paint.setTextSize(20);
                paint.setColor(getResources().getColor(android.R.color.white));
                canvas.drawText("" + fps, 20, 20, paint);


                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }else Log.e("canvas","null");
    }

    public void update(){
        gameStateManager.update(updateTime);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gameStateManager.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint = new Paint();

        t = new Thread(this);
        isRunning = true;
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        t = null;
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap MirrorBitmap(Bitmap source){
        Matrix matrix = new Matrix();
        matrix.setScale(-1,1);
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
    }
}