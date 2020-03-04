package RockPaperScisssors.Game2;
import RockPaperScisssors.Player;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Match implements Callable<Player> {
    Player p1,p2;
    Future<Player> a,b;

    /**
     * Creates a Match object from players
     * @param p1 Player 1
     * @param p2 Player 2
     */
    public Match(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Creates a Match object from player futures
     * @param p1 Player 1
     * @param p2 Player 2
     */
    public Match(Future<Player> p1, Future<Player> p2){
        a = p1;
        b = p2;
    }

    /**
     * Gets a player result
     * @return The winner of the match
     */
    public Player getResult(){
        return call();
    }

    /**
     * Gets match result as a Future
     * @return The winner of the match
     */
    @Override
    public Player call() {
        if(a!=null){
            try {
                p1 = a.get();
                if(b==null)
                    p2 = null;
                else
                    p2 = b.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e){
                System.out.println("cause  "+e.getCause().getMessage()+" p1: "+p1.getName()+" p2: "+p2.getName());
            }
        }
        if(p2==null)
            return p1;

        Player winner = p1.play.score(p2.play)>=0?p1:p2;
        winner.reroll();
        return winner;
    }
}
