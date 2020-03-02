package RockPaperScisssors.Game2;

import RockPaperScisssors.Player;

import java.util.concurrent.*;

public class Tech2 {

    public Tech2(int numPlayers, int numThreads){

        BlockingQueue<Match> matches = new ArrayBlockingQueue<>(numPlayers);
        try {
            ExecutorService pool = Executors.newFixedThreadPool(numThreads);

            for(int i=0;i<numPlayers;i+=2) {
                matches.put(new Match(new Player(i),new Player(i +1)));
            }
            while (matches.size()>1){
                try {
                    matches.add(new Match(pool.submit(matches.poll()).get(),pool.submit(matches.poll()).get()));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(pool.submit(matches.poll()).get().getName()+" wins");

            pool.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
