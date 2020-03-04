package RockPaperScisssors.Game1;

import RockPaperScisssors.Player;

import java.util.*;
import java.util.concurrent.*;

public class Tech2 {

    /**
     * Runs a Rock Paper Scissors Battle Royale style game
     * @param numPlayer The number of players to run with
     * @param numThreads The number of threads to use
     */
    public Tech2(int numPlayer, int numThreads){
        List<Player> players = new ArrayList<>();
        for(int i=0;i<numPlayer;i++)
            players.add(new Player(i));

        try {
            ExecutorService pool = Executors.newCachedThreadPool();

            Thread winnerThread = new Thread(() -> {
                while(players.size()>1) {
                        players.parallelStream().forEach(player -> player.setFutureScore(pool.submit(() -> {
                            int score = 0;
                            for(int i1 = 0; i1 < players.size(); i1++)
                                if(i1 !=player.id)
                                    score += player.play.score(players.get(i1).play);
                            return score;
                        })));
                    players.sort(Comparator.comparingInt(Player::getScore));
                    players.remove(0);
                    players.parallelStream().forEach(Player::reroll);
                }

            });

            winnerThread.start();
            winnerThread.join();
            pool.shutdown();
            System.out.println("Winner: "+players.get(0).getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
