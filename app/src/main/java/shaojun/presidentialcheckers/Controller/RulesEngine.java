package shaojun.presidentialcheckers.Controller;

/**
 * Created by shaojun on 11/10/16.
 */
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import shaojun.presidentialcheckers.Model.*;
import shaojun.presidentialcheckers.R;
import shaojun.presidentialcheckers.View.CheckersActivity;

public class RulesEngine
{
    public static Entity player;
    public static Entity opponent;
    public static Tile[][] tiles;
    public static Entity winner;
    public static boolean trump;
    public static boolean robot;
    public static CheckersActivity checkersActivity;

    public static boolean pieceSelectable(Piece piece)
    {
        return piece!=null &&
                (player.turn && player.pieces.contains(piece))
                || (opponent.turn && opponent.pieces.contains(piece));
    }

    public static boolean pieceDeployable(Tile tile)
    {
        return tile.piece==null && tile.isNotBlocked && tile.legal && Piece.selectedPiece!=null;
    }

    public static void switchTurn()
    {
        if (player.turn)
        {
            player.turn=false;
            opponent.turn=true;
        }
        else
        {
            player.turn=true;
            opponent.turn=false;
        }
    }

    public static void selectPiece(Piece piece)
    {
        clearLegality();
        Piece.selectedPiece=piece;
    }

    public static void clearLegality()
    {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles.length; c++) {
                tiles[r][c].legal=false;
            }
        }
    }

    private static boolean getPlayerLegal(Piece piece, Tile tile)
    {
        int r=(piece.tile.getRowNumber()+tile.getRowNumber())/2;
        int c=(piece.tile.getColumnNumber()+tile.getColumnNumber())/2;
        if(piece!=null && !piece.leveledUp && tile.piece==null)
        {
            if(piece.tile.getRowNumber()-tile.getRowNumber()==2 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==2
                    && tiles[r][c].piece!=null && tiles[r][c].piece.owner.getClass()==Opponent.class)
            {
                return true;
            }
            else if (piece.tile.getRowNumber()-tile.getRowNumber()==1 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (piece!=null && piece.leveledUp && tile.piece==null)
        {
            if(Math.abs(piece.tile.getRowNumber()-tile.getRowNumber())==2 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==2
                    && tiles[r][c].piece!=null && tiles[r][c].piece.owner.getClass()==Opponent.class)
            {
                return true;
            }
            else if (Math.abs(piece.tile.getRowNumber()-tile.getRowNumber())==1 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {return false;}
    }

    private static boolean getOpponentLegal(Piece piece, Tile tile)
    {
        int r=(piece.tile.getRowNumber()+tile.getRowNumber())/2;
        int c=(piece.tile.getColumnNumber()+tile.getColumnNumber())/2;
        if(piece!=null && !piece.leveledUp && tile.piece==null)
        {
            if(piece.tile.getRowNumber()-tile.getRowNumber()==-2 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==2
                    && tiles[r][c].piece!=null && tiles[r][c].piece.owner.getClass()==Player.class)
            {
                return true;
            }
            else if (piece.tile.getRowNumber()-tile.getRowNumber()==-1 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (piece!=null && piece.leveledUp && tile.piece==null)
        {
            if(Math.abs(piece.tile.getRowNumber()-tile.getRowNumber())==2 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==2
                    && tiles[r][c].piece!=null && tiles[r][c].piece.owner.getClass()==Player.class)
            {
                return true;
            }
            else if (Math.abs(piece.tile.getRowNumber()-tile.getRowNumber())==1 && Math.abs(piece.tile.getColumnNumber()-tile.getColumnNumber())==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {return false;}
    }

    public static void switchTile(Tile nTile,TextView hillaryscore, TextView trumpscore,ImageView hillaryhead,ImageView trumphead)
    {
        if(Math.abs(Piece.selectedPiece.tile.getColumnNumber()-nTile.getColumnNumber())==2)
        {
            int r=(Piece.selectedPiece.tile.getRowNumber()+nTile.getRowNumber())/2;
            int c=(Piece.selectedPiece.tile.getColumnNumber()+nTile.getColumnNumber())/2;
            if((player.turn && trump) || (opponent.turn && !trump))
            {
                trumpscore.setText(String.valueOf(Integer.parseInt(trumpscore.getText().toString())+1));
                trumphead.setImageResource(R.drawable.trumphappy);
                hillaryhead.setImageResource(R.drawable.hillarymad);
                checkersActivity.playTrump();
            }
            else
            {
                hillaryscore.setText(String.valueOf(Integer.parseInt(hillaryscore.getText().toString())+1));
                hillaryhead.setImageResource(R.drawable.hillaryhappy);
                trumphead.setImageResource(R.drawable.trumpmad);
                checkersActivity.playHillary();
            }
            tiles[r][c].piece.owner.pieces.remove(tiles[r][c].piece);
            tiles[r][c].piece=null;
        }
        Piece.selectedPiece.tile.piece=null;
        Piece.selectedPiece.tile=nTile;
        nTile.piece=Piece.selectedPiece;
        Log.d("Move Piece", "Destination row: "+String.valueOf(nTile.getRowNumber()) + " Trigger Row " + String.valueOf(tiles.length-1) + " opponent turn value "+String.valueOf(opponent.turn));
        if( (player.turn && nTile.getRowNumber()==0) || (opponent.turn && (nTile.getRowNumber()==(tiles.length-1))))
        {nTile.piece.leveledUp=true;}
        Piece.selectedPiece=null;
        checkWinningCondition();
    }

    public static void setLegality()
    {
        if(player.turn)
        {
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if(getPlayerLegal(Piece.selectedPiece,tiles[r][c]))
                    {
                        tiles[r][c].legal=true;
                    }
                }
            }
        }
        else
        {
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if(getOpponentLegal(Piece.selectedPiece,tiles[r][c]))
                    {
                        tiles[r][c].legal=true;
                    }
                }
            }
        }
    }

    public static void checkWinningCondition()
    {
        if(player.pieces.isEmpty())
        {winner=opponent;}
        else if(opponent.pieces.isEmpty())
        {winner=player;}
        boolean playerHasLegalMove=false;
        boolean oponentHasLegalMove=false;
        outerloop:
        for(Piece p :opponent.pieces)
        {

            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if(getOpponentLegal(p,tiles[r][c]))
                    {
                        oponentHasLegalMove=true;
                        break outerloop;
                    }
                }
            }
        }
        if(!oponentHasLegalMove)
        {winner=player;}

        outerloop:
        for(Piece p :player.pieces)
        {

            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if(getPlayerLegal(p,tiles[r][c]))
                    {
                        playerHasLegalMove=true;
                        break outerloop;
                    }
                }
            }
        }
        if(!playerHasLegalMove)
        {winner=opponent;}
    }


}
