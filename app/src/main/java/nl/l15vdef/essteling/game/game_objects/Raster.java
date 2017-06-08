package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import nl.l15vdef.essteling.game.GameStateManager;
import nl.l15vdef.essteling.game.game_state.PlayingGameState;

import static nl.l15vdef.essteling.game.game_state.PlayingGameState.col;
import static nl.l15vdef.essteling.game.game_state.PlayingGameState.row;

/**
 * Created by Maarten on 08/06/2017.
 */

public class Raster extends GameObject {


    private GameStateManager gm;

    public Raster(View v, GameStateManager gm) {
        super(v);
        this.gm = gm;
    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        int color = p.getColor();

        int beginX = gm.getScreenDimensions().x/4;
        int width = beginX * 3;


        p.setColor(v.getResources().getColor(android.R.color.white));
        for (int i = 0; i < col; i++) {
            canvas.drawLine(beginX,(gm.getScreenDimensions().y/col) * i,gm.getScreenDimensions().x,(gm.getScreenDimensions().y/col) * i,p);
        }
        for (int j = 0; j <row; j++) {
            canvas.drawLine(beginX + width/row * j,0,beginX + width/row * j,gm.getScreenDimensions().y,p);
        }
        p.setColor(color);
    }
}
