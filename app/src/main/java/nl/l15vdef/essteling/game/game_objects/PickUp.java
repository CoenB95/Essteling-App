package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.game.game_objects.snake.Snake;
import nl.l15vdef.essteling.game.game_state.PlayingGameState;

/**
 * Created by Maarten on 08/06/2017.
 */

public class PickUp extends GameObject {
    private Point rasterChords;
    private boolean shouldRemove;
    private Bitmap image;
    private PlayingGameState playingGameState;

    public PickUp(View v,Point rasterChords,PlayingGameState playingGameState) {
        super(v);
        this.rasterChords = rasterChords;
        this.playingGameState = playingGameState;
        shouldRemove = false;
        image = BitmapFactory.decodeResource(v.getResources(),R.drawable.snake_pickup);
        image = Bitmap.createScaledBitmap(image,playingGameState.pixelPerRow,playingGameState.pixelPerCol,true);
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.drawBitmap(image,playingGameState.offSet + rasterChords.x * playingGameState.pixelPerRow,rasterChords.y * playingGameState.pixelPerCol,p);
    }

    public boolean intersect(Snake snake){
        if(rasterChords.equals(snake.getRasterChords())){
            shouldRemove = true;
        }
        return true;
    }

    public Point getRasterChords() {
        return rasterChords;
    }

    public boolean isShouldRemove() {
        return shouldRemove;
    }
}
