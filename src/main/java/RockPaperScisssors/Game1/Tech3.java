package RockPaperScisssors.Game1;

import RockPaperScisssors.Player;

import java.util.*;
import java.util.concurrent.*;

public class Tech3 {

    public Tech3(int numPlayer, int numThreads) {
        List<Player> players = new ArrayList<>();
        for(int i=0;i<numPlayer;i++)
            players.add(new Player(i));

        try {
            HashMap<Integer, Future<Integer>> scores = new HashMap<>();
            Hashtable<String, Integer> calculated = new Hashtable<>();
            Thread winnerThread = new Thread(() -> {
                while(players.size()>1) {
                    calculated.clear();
                    scores.clear();
                    ExecutorService pool = Executors.newFixedThreadPool(numThreads);
                    for(int i=0;i<players.size();i++){
                        int player = i;
                        scores.put(players.get(player).id, pool.submit(() -> {
                            int score = 0;
                            for(int i1 = 0; i1 < players.size(); i1++)
                                if(i1 !=player) {
                                    Integer tmp = calculated.putIfAbsent(sorted(player, i1), players.get(player).play.score(players.get(i1).play));
                                    score += tmp==null?calculated.get(sorted(player,i1)):tmp;
                                }
                            return score;
                        }));
                    }
                    pool.shutdown();
                    players.forEach(player -> {
                        try {
                            player.setScore(scores.get(player.id).get());
                            player.reroll();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });

                    players.sort(Comparator.comparingInt(Player::getScore));
                    players.remove(0);
                }
        });

        winnerThread.start();
        winnerThread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}
    public static String sorted(int a, int b){
        return Math.min(a,b)+"-"+Math.max(a,b);
    }
}
