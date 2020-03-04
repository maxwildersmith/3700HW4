package RockPaperScisssors.Game2;

import RockPaperScisssors.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SuppressWarnings("ALL")
public class Tech1 {

    /**
     * Runs tournement style Rock Paper Scissors using a list of Futures
     * @param numPlayers The number of players to run with
     * @param numThreads The number of threads to use
     */
    public Tech1(int numPlayers, int numThreads){
        List<Match> matches = new ArrayList<>();
        try {
            ExecutorService pool = Executors.newFixedThreadPool(numThreads);
            List<Player> players = new ArrayList<>(numPlayers);
            List<Future<Player>> winners = new ArrayList<>();
            for(int i=0;i<numPlayers;i++) {
                players.add(new Player(i));
            }

            while(players.size()>1)
                matches.add(new Match(players.remove(0),players.remove(0)));
            if(!players.isEmpty())
                matches.add(new Match(players.remove(0),null));

            do {
                winners = pool.invokeAll(matches);

                matches.clear();
                while(winners.size()>1)
                    matches.add(new Match(winners.remove(0),winners.remove(0)));
                if(!winners.isEmpty())
                    matches.add(new Match(winners.remove(0),null));

            } while (matches.size()>1);

            System.out.println(matches.get(0).getResult().getName()+" wins");

            pool.shutdown();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}