package RockPaperScisssors.Game1;

import RockPaperScisssors.Player;

import java.util.*;
import java.util.concurrent.*;

public class Tech2 {

    public Tech2(int numPlayer, int numThreads){
        List<Player> players = new ArrayList<>();
        for(int i=0;i<numPlayer;i++)
            players.add(new Player(i));

        try {
            ExecutorService pool = Executors.newFixedThreadPool(numThreads);
            HashMap<Integer,Future<Integer>> results = new HashMap<>();
            Thread winnerThread = new Thread(() -> {
                while(players.size()>1) {
                    results.clear();
                    for(int i=0;i<players.size();i++){
                        int player = i;
                        results.put(players.get(player).id, pool.submit(() -> {
                            int score = 0;
                            for(int i1 = 0; i1 < players.size(); i1++)
                                if(i1 !=player)
                                    score += players.get(player).play.score(players.get(i1).play);
                            return score;
                        }));
                    }
                    players.parallelStream().forEach(player -> {
                        try {
                            player.setScore(results.get(player.id).get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });

                    players.sort(Comparator.comparingInt(Player::getScore));
                    players.remove(0);
                    players.forEach(Player::reroll);
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
