package nl.l15vdef.essteling.game.game_objects.snake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.game.GameView;
import nl.l15vdef.essteling.game.OnSwipeListener;
import nl.l15vdef.essteling.game.game_objects.GameObject;
import nl.l15vdef.essteling.game.game_state.PlayingGameState;

import static nl.l15vdef.essteling.game.OnSwipeListener.Direction.down;
import static nl.l15vdef.essteling.game.OnSwipeListener.Direction.left;
import static nl.l15vdef.essteling.game.OnSwipeListener.Direction.right;
import static nl.l15vdef.essteling.game.OnSwipeListener.Direction.up;
import static nl.l15vdef.essteling.game.game_objects.snake.Snake.Direction.BOTTOM;
import static nl.l15vdef.essteling.game.game_objects.snake.Snake.Direction.LEFT;
import static nl.l15vdef.essteling.game.game_objects.snake.Snake.Direction.RIGHT;
import static nl.l15vdef.essteling.game.game_objects.snake.Snake.Direction.TOP;


/**
 * Created by Maarten on 08/06/2017.
 */

public class Snake extends GameObject {
    private Point rasterChords;
    private Point offSet;
    private Direction direction;

    private Direction nextDirection;

    private PlayingGameState playingGameState;

    private long totalUpdateTime;

    private Queue<DirectionAndPosition> prevRaster;

    private int sizeOfSnake;

    private List<Bitmap> randomStraight;

    private boolean isDead;

    private int score;


    private Bitmap head;
    private Bitmap middelLeft;
    private Bitmap middleRight;
    private Bitmap middleStraight;
    private Bitmap snakeTail;
    private Bitmap snakeTurn;


    long begin;
    public Snake(View v,Point rasterChords,PlayingGameState playingGameState) {
        super(v);
        this.playingGameState = playingGameState;
        this.rasterChords = rasterChords;
        direction = nextDirection =  RIGHT;
        totalUpdateTime = 0;
        sizeOfSnake = 6;
        score = 0;
        isDead = false;
        prevRaster = new LinkedList<>();

        offSet = new Point(0,0);

        head = BitmapFactory.decodeResource(v.getResources(), R.drawable.snake_head);
        head = Bitmap.createScaledBitmap(head,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);

        middelLeft = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_middle_left);
        middelLeft = Bitmap.createScaledBitmap(middelLeft,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);

        middleRight = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_middle_right);
        middleRight = Bitmap.createScaledBitmap(middleRight,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);

        middleStraight = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_middle_straight);
        middleStraight  =Bitmap.createScaledBitmap(middleStraight,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);

        snakeTurn = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_turn);
        snakeTurn = Bitmap.createScaledBitmap(snakeTurn,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);

        snakeTail = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_tail);
        snakeTail = Bitmap.createScaledBitmap(snakeTail,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);


        randomStraight = new ArrayList<>();
        randomStraight.add(middelLeft);
        randomStraight.add(middleRight);
        randomStraight.add(middleStraight);

        begin = System.currentTimeMillis();
    }

    @Override
    public void update(long updateTime) {
        double pixelPerMiliSec = (double) playingGameState.pixelPerCol/  (double) PlayingGameState.gameSpeedInMiliSeconds;


        offSet.y -= pixelPerMiliSec * updateTime;


        if(totalUpdateTime >= PlayingGameState.gameSpeedInMiliSeconds){
            totalUpdateTime = 0;
            offSet = new Point(0,0);
            updateCoords(updateTime);
            detectDeath();
            begin = System.currentTimeMillis();
        }else totalUpdateTime += updateTime;
    }

    private void detectDeath() {
        for (DirectionAndPosition directionAndPosition : prevRaster) {
            if(rasterChords.equals(directionAndPosition.getPoint())){
                isDead = true;
            }
        }
    }

    private void updateCoords(long updateTime) {
        if(prevRaster.size() >= sizeOfSnake){
            prevRaster.remove();
        }


            //top
        if(direction == TOP){
            if(nextDirection == TOP){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,randomStraight.get((int) (Math.random() * 3))));
            }else if(nextDirection == RIGHT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,0)));
            }else if(nextDirection == LEFT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.MirrorBitmap(snakeTurn)));
            }

            //right
        }else if(direction == RIGHT){
            if (nextDirection == RIGHT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(randomStraight.get((int) (Math.random() * 3)),90)));
            }else if(nextDirection == TOP){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,-180)));
            }else if (nextDirection == BOTTOM){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.MirrorBitmap(snakeTurn)));
            }

            //Left
        }else if(direction == LEFT){
            if(nextDirection == LEFT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(randomStraight.get((int) (Math.random() * 3)),-90)));
            }else if(nextDirection == TOP){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,-90)));
            }else if(nextDirection == BOTTOM){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,0)));
            }

            //bottom
        }else if(direction == BOTTOM){
            if(nextDirection == BOTTOM){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(randomStraight.get((int) (Math.random() * 3)),180)));
            }else if(nextDirection == RIGHT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,-90)));
            }else if (nextDirection == LEFT){
                prevRaster.add(new DirectionAndPosition(new Point(rasterChords),direction,GameView.RotateBitmap(snakeTurn,180)));
            }
        }



        direction = nextDirection;

        switch (direction){
            case TOP:{
                rasterChords.y -= 1;
            }
            break;
            case LEFT:{
                rasterChords.x -= 1;
            }
            break;
            case BOTTOM:{
                rasterChords.y +=1;
            }
            break;
            case RIGHT:{
                rasterChords.x += 1;
            }
            break;
        }



    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        switch (direction){
            case TOP:{
                canvas.drawBitmap(head,
                        playingGameState.offSet + rasterChords.x * playingGameState.pixelPerRow ,
                        rasterChords.y * playingGameState.pixelPerCol,p);
            }
            break;
            case LEFT:{
                canvas.drawBitmap(GameView.RotateBitmap(head,-90),
                        playingGameState.offSet + rasterChords.x * playingGameState.pixelPerRow ,
                        rasterChords.y * playingGameState.pixelPerCol,p);
            }
            break;
            case RIGHT:{
                canvas.drawBitmap(GameView.RotateBitmap(head,90),
                        playingGameState.offSet + rasterChords.x * playingGameState.pixelPerRow ,
                        rasterChords.y * playingGameState.pixelPerCol,p);
            }
            break;
            case BOTTOM:{
                canvas.drawBitmap(GameView.RotateBitmap(head,180),
                        playingGameState.offSet + rasterChords.x * playingGameState.pixelPerRow ,
                        rasterChords.y * playingGameState.pixelPerCol,p);
            }
        }

        int count = 0;
        DirectionAndPosition prev = null;
        for (DirectionAndPosition directionAndPosition : prevRaster) {
            if(count == 1) {
                switch (directionAndPosition.getDirection()) {
                    case TOP: {
                        canvas.drawBitmap(GameView.RotateBitmap(snakeTail, 0),
                                playingGameState.offSet + prev.getPoint().x * playingGameState.pixelPerRow,
                                prev.getPoint().y * playingGameState.pixelPerCol, p);
                    }
                    break;
                    case RIGHT: {
                        canvas.drawBitmap(GameView.RotateBitmap(snakeTail, 90),
                                playingGameState.offSet + prev.getPoint().x * playingGameState.pixelPerRow,
                                prev.getPoint().y * playingGameState.pixelPerCol, p);
                    }
                    break;
                    case LEFT: {
                        canvas.drawBitmap(GameView.RotateBitmap(snakeTail, -90),
                                playingGameState.offSet + prev.getPoint().x * playingGameState.pixelPerRow,
                                prev.getPoint().y * playingGameState.pixelPerCol, p);
                    }
                    break;
                    case BOTTOM: {
                        canvas.drawBitmap(GameView.RotateBitmap(snakeTail, 180),
                                playingGameState.offSet + prev.getPoint().x * playingGameState.pixelPerRow,
                                prev.getPoint().y * playingGameState.pixelPerCol, p);
                    }
                    break;
                }
            }
            if(count == 0){
                prev = directionAndPosition;
            }else canvas.drawBitmap(directionAndPosition.getImage(),
                        playingGameState.offSet + directionAndPosition.getPoint().x * playingGameState.pixelPerRow,
                        directionAndPosition.getPoint().y * playingGameState.pixelPerCol,p);
            count++;
        }
    }

    public Point getRasterChords() {
        return rasterChords;
    }

    public void setRasterChords(Point rasterChords) {
        this.rasterChords = rasterChords;
    }

    public int getScore() {
        return score;
    }

    public void oneLonger() {
        sizeOfSnake++;
        score++;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setNextDirection(Direction direction) {
        if(     this.direction == TOP && direction != BOTTOM ||
                this.direction == RIGHT && direction != LEFT ||
                this.direction == LEFT && direction != RIGHT ||
                this.direction == BOTTOM && direction != TOP)
        this.nextDirection = direction;
    }

    public void setNextDirection(OnSwipeListener.Direction direction){
        if(     this.direction == TOP && direction != down ||
                this.direction == RIGHT && direction != left ||
                this.direction == LEFT && direction != right ||
                this.direction == BOTTOM && direction != up){

            switch (direction){
                case up:
                    nextDirection = TOP;
                break;

                case down:
                    nextDirection = BOTTOM;
                    break;

                case right:
                    nextDirection = RIGHT;
                    break;

                case left:
                    nextDirection = LEFT;
                    break;
            }
        }

    }

    public enum Direction{
        TOP, LEFT,RIGHT, BOTTOM
    }
}


