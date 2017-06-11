package nl.l15vdef.essteling.game.game_state;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.l15vdef.essteling.game.GameStateManager;
import nl.l15vdef.essteling.game.OnSwipeListener;
import nl.l15vdef.essteling.game.game_objects.Background;
import nl.l15vdef.essteling.game.game_objects.PickUp;
import nl.l15vdef.essteling.game.game_objects.Raster;
import nl.l15vdef.essteling.game.game_objects.snake.Snake;



/**
 * Created by Maarten on 08/06/2017.
 */

public class PlayingGameState extends State {

    public final static int row = 15;
    public final static int col = 10;

    public final static int gameSpeedInMiliSeconds = 300;

    public int pixelPerRow;
    public int pixelPerCol;
    public int offSet;

    private GestureDetector gestureDetector;


    private Background b;
    private Raster r;
    private List<PickUp> pickUps;
    private long timer;
    private Snake snake;




    public PlayingGameState(View v, GameStateManager gm){
        super(v,gm);
        init();
    }

    public void init(){
        b = new Background(v);
        r = new Raster(v,gm);


        gestureDetector = new GestureDetector(v.getContext(),new OnSwipeListener(){
            @Override
            public boolean onSwipe(Direction direction) {
                snake.setNextDirection(direction);
                return true;
            }
        });


        pickUps = new ArrayList<>();
        timer = System.currentTimeMillis();

        offSet = gm.getScreenDimensions().x/4;
        int width = offSet * 3;
        pixelPerRow = width/row;
        pixelPerCol = gm.getScreenDimensions().y / col;
        snake = new Snake(v,new Point(5,5),this);
    }

    @Override
    public void update(long updateTime) {
        if(System.currentTimeMillis() - timer > 5000){
            timer = System.currentTimeMillis();
            pickUps.add(new PickUp(v,new Point( (int) (Math.random() * row), (int) (Math.random() * col)),this));
        }
        snake.update(updateTime);


        //detect when the snake gets out of the map
        if(snake.getRasterChords().y > col){
            snake.setRasterChords(new Point(snake.getRasterChords().x,0));
        }else if(snake.getRasterChords().y < 0){
            snake.setRasterChords(new Point(snake.getRasterChords().x,col));
        }else if(snake.getRasterChords().x > row){
            snake.setRasterChords(new Point(0,snake.getRasterChords().y));
        }else if(snake.getRasterChords().x < 0){
            snake.setRasterChords(new Point(row,snake.getRasterChords().y));
        }

        Iterator<PickUp> pickUpIterator = pickUps.iterator();
        while (pickUpIterator.hasNext()){
            PickUp p = pickUpIterator.next();
            p.intersect(snake);
            if(p.isShouldRemove()){
                pickUpIterator.remove();
                snake.oneLonger();
            }
        }

        //detect if an game has ended
        if(snake.isDead()){
            gm.setState(GameStateManager.STARTING_STATE);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        b.draw(canvas,p);
        //canvas.drawRGB(255,255,255);
        //r.draw(canvas,p);
        for (PickUp pickUp : pickUps) {
            pickUp.draw(canvas,p);
        }

        snake.draw(canvas,p);
        float textSize = p.getTextSize();
        p.setTextSize(50);
        canvas.drawText("score: "+ snake.getScore(),20,100,p);

        p.setTextSize(textSize);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

}
