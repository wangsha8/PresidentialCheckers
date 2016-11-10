package shaojun.presidentialcheckers.View;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.LinkedList;

import shaojun.presidentialcheckers.Model.Tile;
import shaojun.presidentialcheckers.Model.Piece;
/**
 * Created by shaojun on 11/4/16.
 */

public class CheckersPlayView extends View
{
    private static final String TAG = CheckersPlayView.class.getSimpleName();

    private static final int DIMENSION=8;

    private final Tile[][] mTiles;

    private LinkedList<Piece> pieces=new LinkedList<>();

    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    public CheckersPlayView(Context context, AttributeSet attrs)
    {
        super(context,attrs);

        this.mTiles = new Tile[DIMENSION][DIMENSION];
        setFocusable(true);
        buildTiles();
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    private void buildTiles() {
        int count = 0;
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                mTiles[c][r] = new Tile(c, r);
                if ((c+r)%2==1)
                {
                    if (count<(DIMENSION*2+1))
                    {
                        Piece piece = new Piece(mTiles[c][r], Color.RED,count);
                        this.pieces.add(piece);
                        mTiles[c][r].piece = piece;
                    }
                    if (count>DIMENSION*DIMENSION-DIMENSION*2-1)
                    {
                        Piece piece = new Piece(mTiles[c][r], Color.BLUE,count);
                        this.pieces.add(piece);
                        mTiles[c][r].piece = piece;
                    }
                }
                count++;
            }
        }
    }


    private int getXCoord(final int x) {
        return x0 + squareSize * (flipped ? 7 - x : x);
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * (flipped ? y : 7 - y);
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = (width  - squareSize * 8) / 2;
        this.y0 = (height - squareSize * 8) / 2;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        this.squareSize = Math.min(getWidth()/DIMENSION, getHeight()/DIMENSION);

        computeOrigins(width, height);

        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                final int xCoord = getXCoord(c);
                final int yCoord = getYCoord(r);

                final Rect tileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                mTiles[c][r].setTileRect(tileRect);
                mTiles[c][r].draw(canvas);

                if(mTiles[c][r].piece != null)
                {
                    mTiles[c][r].piece.setDrawingParam(squareSize/2,xCoord+squareSize/2,yCoord+squareSize/2);
                    mTiles[c][r].piece.draw(canvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        boolean oneSelected= false;
        Piece selectedPiece=null;
        for (Piece p:pieces)
        {
            if (p.selected==true)
            {
                oneSelected=true;
                selectedPiece=p;
            }
        }
        Tile tile;
        outerloop:
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                tile = mTiles[c][r];
                if (tile.isTouched(x, y))
                {
                    tile.handleTouch();
                    if(oneSelected && tile.piece==null)
                    {
                        selectedPiece.tile.piece=null;
                        tile.piece=selectedPiece;
                        selectedPiece.tile=tile;
                        for (Piece p:pieces)
                        {p.selected=false;}
                        invalidate();
                        break outerloop;
                    }
                    else
                    {
                        tile.piece.selected=true;
                        for (Piece p:pieces)
                        {
                            if(p.id!=tile.piece.id)
                            {p.selected=false;}
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if(widthMeasureSpec<heightMeasureSpec)
        {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
        else
        {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        }
    }
}
