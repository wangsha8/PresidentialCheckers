package shaojun.presidentialcheckers.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by shaojun on 11/4/16.
 */

public class Piece
{
    public int id;
    public Tile tile;
    private Paint circleColor;
    private int radius;
    public int centerX;
    public int centerY;
    public Entity owner;
    public boolean leveledUp=false;
    public static Piece selectedPiece=null;
    private int newColor;

    public Piece(Tile tile,int color,int id) {
        this.tile=tile;
        this.circleColor = new Paint();
        this.circleColor.setColor(color);
        this.newColor=color+100;
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
        if(leveledUp)
        {circleColor.setColor(newColor);}
        canvas.drawCircle(centerX,centerY,radius-4,circleColor);
    }

    public static void drawSelected(final Canvas canvas)
    {
        if(selectedPiece!=null)
        {
            Paint p = new Paint();
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(10);
            p.setColor(Color.GREEN);
            canvas.drawCircle(selectedPiece.centerX,selectedPiece.centerY,selectedPiece.radius,p);
        }

    }

}
