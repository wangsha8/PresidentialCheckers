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

public final class Tile {
    private static final String TAG = Tile.class.getSimpleName();

    private final int columnNumber;
    private final int rowNumber;

    private final Paint squareColor;
    private Rect rect;

    public Piece piece;
    public boolean legal=false;
    public boolean isNotBlocked;

    public Tile(final int rowNumber, final int columnNumber) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;

        this.squareColor = new Paint();
        squareColor.setColor(isBlack() ? Color.BLACK : Color.WHITE);
        isNotBlocked = isBlack() ? false : true;
    }

    public void draw(final Canvas canvas) {
        if (this.legal)
        {this.squareColor.setColor(Color.rgb(180,255,220));}
        else
        {squareColor.setColor(isBlack() ? Color.BLACK : Color.WHITE);}
        canvas.drawRect(rect, squareColor);
    }


    public void handleTouch()
    {
        Log.d(TAG, " Tile touched: column Number: " + columnNumber +", row Number: " + rowNumber);
    }

    public boolean isBlack() {
        return (columnNumber + rowNumber) % 2 == 0;
    }

    public boolean isTouched(final int x, final int y)
    {return rect.contains(x, y);}

    public void setRect(final Rect rect)
    {
        this.rect = rect;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
            return rowNumber;
    }
}