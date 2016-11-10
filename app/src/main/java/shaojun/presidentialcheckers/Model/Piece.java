package shaojun.presidentialcheckers.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by shaojun on 11/4/16.
 */

public class Piece
{
    public static Piece selectedPiece = null;

    public int id;
    public Tile tile;
    private Paint circleColor;
    private int radius;
    public int centerX;
    public int centerY;

    public Piece(Tile tile,int color,int id) {
        this.tile=tile;
        this.circleColor = new Paint();
        this.circleColor.setColor(color);
        this.circleColor.setStrokeWidth(40);
        this.circleColor.setStyle(Paint.Style.FILL);
        this.id = id;
    }

    public void setDrawingParam(int radius, int centerX, int centerY)
    {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void draw(final Canvas canvas) {
        canvas.drawCircle(centerX,centerY,radius,circleColor);
    }
}
