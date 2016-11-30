package shaojun.presidentialcheckers.Model;

/**
 * Created by shaojun on 11/4/16.
 * https://gist.github.com/Oshuma/3352280
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public final class Tile
{
    //primative
    private final int columnNumber;
    private final int rowNumber;
    public boolean legal=false;
    public boolean isNotBlocked;
    //reference
    private final Paint rectColor = new Paint();
    public Rect rect;
    public Piece piece;

    public Tile(int rowNumber, int columnNumber)
    {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        rectColor.setColor((columnNumber + rowNumber) % 2 == 0 ? Color.BLACK : Color.WHITE);
        isNotBlocked = (columnNumber + rowNumber) % 2 == 0? false : true;
    }

    public void draw(final Canvas canvas)
    {
        if (this.legal)
        {this.rectColor.setColor(Color.rgb(180,255,220));}
        else
        {rectColor.setColor((columnNumber + rowNumber) % 2 == 0? Color.BLACK : Color.WHITE);}
        canvas.drawRect(rect, rectColor);
    }

    public void handleTouch()
    {Log.d("Tile touched"," column Number: " + columnNumber +", row Number: " + rowNumber);}

    public int getColumnNumber()
    {return columnNumber;}

    public int getRowNumber()
    {return rowNumber;}
}