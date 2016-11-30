package shaojun.presidentialcheckers.Controller;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import shaojun.presidentialcheckers.Model.*;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shaojun on 11/4/16.
 * https://gist.github.com/Oshuma/3352280
 */

public class CheckersPlayController extends View
{
    private static final String TAG = CheckersPlayController.class.getSimpleName();
    public TextView hillaryscore;
    public TextView trumpscore;
    public ImageView hillaryhead;
    public ImageView trumphead;
    public Entity player=null;
    public Entity opponent=null;

    private static final int DIMENSION=6;

    private final Tile[][] tiles;


    private int xOrigin = 0;
    private int yOrigin = 0;
    private int squareSize = 0;
    private boolean robot=RulesEngine.robot;
    private boolean trump=RulesEngine.trump;

    public CheckersPlayController(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        this.player=new Player();
        this.opponent=new Opponent();
        RulesEngine.player=this.player;
        RulesEngine.opponent=this.opponent;
        this.tiles = new Tile[DIMENSION][DIMENSION];
        setFocusable(true);
        initializeTilesAndPieces();
        RulesEngine.tiles= tiles;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    private void initializeTilesAndPieces() {
        int count = 0;
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                tiles[r][c] = new Tile(r, c);
                if ((c+r)%2==1)
                {
                    if (count<(DIMENSION*2+1))
                    {
                        Piece piece = new Piece(tiles[r][c], getOpponentColor(),count);
                        piece.owner=opponent;
                        opponent.pieces.add(piece);
                        tiles[r][c].piece = piece;
                    }
                    if (count>DIMENSION*DIMENSION-DIMENSION*2-1)
                    {
                        Piece piece = new Piece(tiles[r][c], getPlayerColor(),count);
                        piece.owner=player;
                        player.pieces.add(piece);
                        tiles[r][c].piece = piece;
                    }
                }
                count++;
            }
        }
    }

    private int getPlayerColor()
    {
        if(this.trump)
        {
            return Color.RED;
        }
        else
        {
            return Color.BLUE;
        }
    }

    private int getOpponentColor()
    {
        if(!(this.trump))
        {
            return Color.RED;
        }
        else
        {
            return Color.BLUE;
        }
    }

    private int getXFromColumnNumber(final int c)
    {
        return xOrigin + squareSize * c;
    }

    private int getYFromRowNumber(final int r)
    {
        return yOrigin + squareSize * r;
    }

    private void setOrigins(final int width, final int height) {
        this.xOrigin = (width  - squareSize * DIMENSION) / 2;
        this.yOrigin = (height - squareSize * DIMENSION) / 2;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        this.squareSize = Math.min(getWidth()/DIMENSION, getHeight()/DIMENSION);

        setOrigins(width, height);

        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                final int xCoord = getXFromColumnNumber(c);
                final int yCoord = getYFromRowNumber(r);
                final Rect rect = new Rect(xCoord, yCoord, (xCoord + squareSize), (yCoord + squareSize));
                tiles[r][c].setRect(rect);
                tiles[r][c].draw(canvas);

                if(tiles[r][c].piece != null)
                {
                    tiles[r][c].piece.setDrawingParam(squareSize/2,xCoord+squareSize/2,yCoord+squareSize/2);
                    tiles[r][c].piece.draw(canvas);
                }
                Piece.drawSelected(canvas);
            }
        }
    }

    public void handleTouch(final MotionEvent event)
    {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        Tile tile;
        outerloop:
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                tile = tiles[r][c];
                if (tile.isTouched(x, y))
                {
                    tile.handleTouch();
                    if(RulesEngine.pieceSelectable(tile.piece))
                    {
                        RulesEngine.selectPiece(tile.piece);
                        RulesEngine.setLegality();
                    }
                    else if(RulesEngine.pieceDeployable(tile))
                    {
                        RulesEngine.switchTile(tile,hillaryscore,trumpscore,hillaryhead,trumphead);
                        RulesEngine.switchTurn();
                        RulesEngine.clearLegality();
                    }
                    invalidate();
                    break outerloop;
                }
            }
        }
        if(opponent.turn && RulesEngine.winner==null && !opponent.pieces.isEmpty() && RulesEngine.robot)
        {
            DecisionEngine.moveAPiece(hillaryscore,trumpscore,hillaryhead,trumphead);
            invalidate();
        }
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
