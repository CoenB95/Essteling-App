package nl.l15vdef.essteling.game.game_objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

import nl.l15vdef.essteling.Score;

/**
 * Created by Maarten on 08/06/2017.
 */

public class GameScores extends GameObject {
    private List<Score> scores;

    public GameScores(View v) {
        super(v);

    }

    @Override
    public void update(long updateTime) {

    }

    @Override
    public void draw(Canvas canvas, Paint p) {

    }
}
