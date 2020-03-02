package RockPaperScisssors.Game2;
import RockPaperScisssors.Player;

import java.util.concurrent.Callable;

public class Match implements Callable<Player> {
    Player p1,p2;

    public Match(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getResult(){
        if(p2==null)
            return p1;
        Player winner = p1.play.score(p2.play)>=0?p1:p2;
        p1.reroll();
        p2.reroll();
        return winner;
    }

    @Override
    public Player call() {
        if(p2==null)
            return p1;
        Player winner = p1.play.score(p2.play)>=0?p1:p2;
        p1.reroll();
        p2.reroll();
        return winner;
    }
}
