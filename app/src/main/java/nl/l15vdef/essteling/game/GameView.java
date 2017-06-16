package nl.l15vdef.essteling.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback{



    private SurfaceHolder surfaceHolder;

    private Thread t = null;
    private boolean isRunning;

    private double fps;
    private long frameTime;

    private final int infoRefreshTime = 500;
    private Paint paint;
    private GameStateManager gameStateManager;

    private long renderTime;
    private int tickTime;




    public GameView(Context context){
        super(context);

        //init gameStateManager
        gameStateManager = new GameStateManager(this);

        //begin fps will be displayed for infoRefreshTime milliseconds
        fps = 60;

        renderTime = 0;
        tickTime = 0;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void run() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        int frame = 0;

        double tickTimeSinceRefresh = 0;
        double renderTimeSinceRefresh = 0;
        Looper.prepare();

        while (isRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            frameTime = (now - lastLoopTime)  / 1000000;
            lastLoopTime = now;

            //update the time since refreshing the fps
            lastFpsTime += frameTime;



            // update our FPS counter if infoRefreshTime milliseconds has passed since
            // we last recorded
            if (lastFpsTime >= infoRefreshTime) {
                lastFpsTime = 0;
                fps = frame *  ((double) 1000/ (double) infoRefreshTime);
                double amountOfFramesBetweenRefresh = ((double) 1000/ (double) infoRefreshTime);
                tickTime = (int) ((tickTimeSinceRefresh / frame) * amountOfFramesBetweenRefresh);
                renderTime = (int) ((renderTimeSinceRefresh / frame)*  amountOfFramesBetweenRefresh);

                frame = 0;
                tickTimeSinceRefresh = 0;
                renderTimeSinceRefresh = 0;

            }else frame++;

            long beginUpdateTime = System.nanoTime();
            update();
            tickTimeSinceRefresh += (System.nanoTime() - beginUpdateTime) / 1000000;

            long beginRenderTime = System.nanoTime();
            draw();
            renderTimeSinceRefresh += (System.nanoTime() - beginRenderTime) / 1000000;
        }
    }

    public void update(){
        gameStateManager.update(frameTime);
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
                canvas.drawText("" + tickTime,80,20,paint);
                canvas.drawText(""+ renderTime,140,20,paint);



                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }else Log.e("canvas","null");
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