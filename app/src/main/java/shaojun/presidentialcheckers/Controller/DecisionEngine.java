package shaojun.presidentialcheckers.Controller;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Random;

import shaojun.presidentialcheckers.Model.Piece;
import shaojun.presidentialcheckers.Model.Player;
import shaojun.presidentialcheckers.Model.Tile;
import shaojun.presidentialcheckers.R;

/**
 * Created by shaojun on 11/21/16.
 */

public class DecisionEngine extends RulesEngine
{
    private static class MoveSet
    {
        public Piece piece;
        public LinkedList<Tile> scoreTiles = new LinkedList<>();
        public LinkedList<Tile> legalTiles = new LinkedList<>();
    }
    public static void moveAPiece(TextView hillaryscore, TextView trumpscore, ImageView hillaryhead, ImageView trumphead)
    {
        LinkedList<MoveSet> moveSets = new LinkedList<>();
        for(Piece p:opponent.pieces)
        {
            MoveSet moveSet = new MoveSet();
            moveSet.piece=p;
            moveSets.add(moveSet);
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles.length; c++) {
                    if(isLegalMove(p,tiles[r][c]))
                    {
                        moveSet.legalTiles.add(tiles[r][c]);
                    }
                    else if(isScoreMove(p,tiles[r][c]))
                    {
                        moveSet.scoreTiles.add(tiles[r][c]);
                    }
                }
            }
            if(moveSet.scoreTiles.isEmpty() && moveSet.legalTiles.isEmpty())
            {
                moveSets.remove(moveSet);
            }
        }
        for(MoveSet m:moveSets)
        {
            if(!m.scoreTiles.isEmpty())
            {
                switchTile(m.piece,m.scoreTiles.get(0),hillaryscore,trumpscore,hillaryhead,trumphead);
                switchTurn();
                clearLegality();
                return;
            }
        }

        Random ran = new Random();
        int pindex=ran.nextInt(moveSets.size());
        int tindex=ran.nextInt(moveSets.get(pindex).legalTiles.size());
        switchTile(moveSets.get(pindex).piece,moveSets.get(pindex).legalTiles.get(tindex),hillaryscore,trumpscore,hillaryhead,trumphead);

        switchTurn();
        clearLegality();
    }

    private static void switchTile(Piece piece, Tile nTile, TextView hillaryscore, TextView trumpscore, ImageView hillaryhead, ImageView trumphead)
    {
        if(Math.abs(piece.tile.getCol()-nTile.getCol())==2)
        {
            int r=(piece.tile.getRow()+nTile.getRow())/2;
            int c=(piece.tile.getCol()+nTile.getCol())/2;
            if(!trump)
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
        piece.tile.piece=null;
        piece.tile=nTile;
        nTile.piece=piece;
        Log.d("Move Piece", "Destination row: "+String.valueOf(nTile.getRow()) + " Trigger Row " + String.valueOf(tiles.length-1) + " opponent turn value "+String.valueOf(opponent.turn));
        if( nTile.getRow()==(tiles.length-1))
        {nTile.piece.leveledUp=true;}
        checkWinningCondition();
    }

    private static boolean isScoreMove(Piece piece, Tile tile) {
        int r = (piece.tile.getRow() + tile.getRow()) / 2;
        int c = (piece.tile.getCol() + tile.getCol()) / 2;
        if (piece != null && !piece.leveledUp && tile.piece == null) {
            if (piece.tile.getRow() - tile.getRow() == -2 && Math.abs(piece.tile.getCol() - tile.getCol()) == 2
                    && tiles[r][c].piece != null && tiles[r][c].piece.owner.getClass() == Player.class) {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (piece != null && piece.leveledUp && tile.piece == null) {
            if (Math.abs(piece.tile.getRow() - tile.getRow()) == 2 && Math.abs(piece.tile.getCol() - tile.getCol()) == 2
                    && tiles[r][c].piece != null && tiles[r][c].piece.owner.getClass() == Player.class) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private static boolean isLegalMove(Piece piece, Tile tile) {
        if (piece != null && !piece.leveledUp && tile.piece == null) {
            if (piece.tile.getRow() - tile.getRow() == -1 && Math.abs(piece.tile.getCol() - tile.getCol()) == 1) {
                return true;
            } else {
                return false;
            }
        } else if (piece != null && piece.leveledUp && tile.piece == null) {
            if (Math.abs(piece.tile.getRow() - tile.getRow()) == 1 && Math.abs(piece.tile.getCol() - tile.getCol()) == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
