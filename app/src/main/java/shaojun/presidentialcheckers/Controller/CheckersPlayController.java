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
    //primitive
    private static final int DIMENSION=6;
    private int xOrigin = 0;
    private int yOrigin = 0;
    private int squareSize = 0;

    private boolean robot=RulesEngine.robot;
    private boolean trump=RulesEngine.trump;

    //reference
    public TextView hillaryscore;
    public TextView trumpscore;
    public ImageView hillaryhead;
    public ImageView trumphead;
    public Entity player=null;
    public Entity opponent=null;
    private final Tile[][] tiles;

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

    @Override
    protected void onDraw(final Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        this.squareSize = w/DIMENSION < h/DIMENSION? w/DIMENSION: h/DIMENSION;
        this.xOrigin = (w  - squareSize * DIMENSION) / 2;
        this.yOrigin = (h - squareSize * DIMENSION) / 2;

        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                int x = xOrigin + squareSize * c;
                int y = yOrigin + squareSize * r;
                tiles[r][c].rect=new Rect(x, y, (x + squareSize), (y + squareSize));
                tiles[r][c].draw(canvas);
                if(tiles[r][c].piece != null)
                {
                    tiles[r][c].piece.setDrawingParam(squareSize/2,x+squareSize/2,y+squareSize/2);
                    tiles[r][c].piece.draw(canvas);
                }
                Piece.drawSelected(canvas);
            }
        }
    }

    public void handleTouch(final MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Tile tile;
        outerloop:
        for (int r = 0; r < DIMENSION; r++) {
            for (int c = 0; c < DIMENSION; c++) {
                tile = tiles[r][c];
                if (tile.rect.contains(x, y))
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
