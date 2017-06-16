package nl.l15vdef.essteling.game.game_objects.snake;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by Maarten on 08/06/2017.
 */

public class DirectionAndPosition {
    private Point point;
    private Snake.Direction direction;
    private Bitmap image;

    public DirectionAndPosition(Point point, Snake.Direction direction,Bitmap image) {
        this.point = point;
        this.direction = direction;
        this.image = image;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Snake.Direction getDirection() {
        return direction;
    }

    public void setDirection(Snake.Direction direction) {
        this.direction = direction;
    }

    public Bitmap getImage() {
        return image;
    }
}
