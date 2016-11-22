package shaojun.presidentialcheckers.Model;

import java.util.LinkedList;

/**
 * Created by shaojun on 11/4/16.
 */

public abstract class Entity
{
    public boolean turn=false;
    public LinkedList<Piece> pieces=new LinkedList<>();
    public Entity()
    {}
}
