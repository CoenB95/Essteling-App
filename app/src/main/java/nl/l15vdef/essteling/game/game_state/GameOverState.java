package nl.l15vdef.essteling.game.game_state;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import nl.l15vdef.essteling.game.GameStateManager;
import nl.l15vdef.essteling.game.game_objects.Background;
import nl.l15vdef.essteling.game.game_objects.BackgroundMoving;

import static java.security.AccessController.getContext;

/**
 * Created by Maarten on 15/06/2017.
 */

public class GameOverState extends State{

    private BackgroundMoving bm;
    private int score;

    public GameOverState(View v, GameStateManager gm) {
        super(v, gm);
        init();
    }

    @Override
    public void init(Object... objects) {
        bm = new BackgroundMoving(v,gm);
        score = 0;
        if(objects.length >= 1){
            score = (int) objects[0];
        }
    }

    @Override
    public void update(long updateTime) {
        bm.update(updateTime);
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        int color = p.getColor();

                bm.draw(canvas,p);
        p.setTextSize(70);
        Rect bounds = new Rect();
        String scoreText = "Score " + score;
        p.getTextBounds(scoreText, 0, scoreText.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
        canvas.drawText(scoreText,gm.getScreenDimensions().x/2 - width/2,gm.getScreenDimensions().y/2 + 200,p);

        String highscore = "Highscore 1"; // TODO: 15/06/2017  create highscore getter
        p.getTextBounds(highscore,0,highscore.length(),bounds);
        height = bounds.height();
        width = bounds.width();
        canvas.drawText(highscore,gm.getScreenDimensions().x/2 - width/2,gm.getScreenDimensions().y/2,p);

        p.setColor(v.getResources().getColor(android.R.color.holo_red_dark));
        p.setTextSize(100);
        p.setColor(v.getResources().getColor(android.R.color.holo_red_dark));
        p.setTextSize(100);
        String gameOverText = "Game over";
        p.getTextBounds(gameOverText,0,gameOverText.length(),bounds);
        height = bounds.height();
        width = bounds.width();
        canvas.drawText(gameOverText,gm.getScreenDimensions().x/2 - width/2,gm.getScreenDimensions().y/2 - 200,p);



        p.setColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finishFunction();
        return false;
    }

    private void finishFunction() {
        Activity activity = (Activity)v.getContext();
        activity.finish();
    }
}
