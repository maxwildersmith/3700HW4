package RockPaperScisssors.Game1;

import RockPaperScisssors.Player;

import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("ALL")
public class Tech1 {

    /**
     * Rock Paper Scissors Battle Royale with a simple thread pool
     * @param numPlayer The number of players to play with
     * @param numThreads The number of threads to use
     */
    public Tech1(int numPlayer, int numThreads){
        List<Player> players = new ArrayList<>();
        for(int i=0;i<numPlayer;i++)
            players.add(new Player(i));

        try {
            ExecutorService pool = Executors.newFixedThreadPool(numThreads);
            List<Future<Integer>> scores = new ArrayList<>();

            Thread winnerThread = new Thread(() -> {
                try {
                    while(players.size()>1) {
                        scores.clear();
                        for(int i=0;i<players.size();i++){
                            int player = i;
                            scores.add(pool.submit(() -> {
                                int score = 0;
                                for(int i1 = 0; i1 < players.size(); i1++)
                                    if(i1 !=player)
                                        score += players.get(player).play.score(players.get(i1).play);
                                return score;
                            }));
                        }
                        int index = 0;
                        for (int i = 1; i < players.size(); i++) {
                            if (scores.get(index).get() > scores.get(i).get())
                                index = i;
                            else if(scores.get(index).get() == scores.get(i).get()&&Math.random()>=.5)
                                index = i;
                        }
                        players.remove(index);
                        players.forEach(Player::reroll);
                    }

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });

            winnerThread.start();
            winnerThread.join();
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Winner: "+players.get(0).getName());
    }
}
