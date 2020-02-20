package RockPaperScisssors.Game1;

import RockPaperScisssors.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Tech1 {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Running with technique 1 - ");
        System.out.print("Enter number of players: ");
        int numPlayer = new Scanner(System.in).nextInt();

        CountDownLatch creationLatch = new CountDownLatch(numPlayer), completeLatch = new CountDownLatch(numPlayer);
        List<Game> players = new ArrayList<>();
        for(int i=0;i<numPlayer;i++)
            players.add(i, new Game(i,creationLatch, completeLatch, players, "Player "+i));

        ExecutorService pool = Executors.newCachedThreadPool();

            creationLatch.await();


        Thread winnerThread = new Thread(() -> {
            try {
                while(players.size()>1) {
                    for (Game g : players)
                        g.init();
                    List<Future<Integer>> scores = pool.invokeAll(players);

                    //completeLatch.wait();
                    int index = 0;
                    for (int i = 1; i < players.size(); i++) {
                        if (scores.get(index).get() > scores.get(i).get())
                            index = i;
                    }
                    players.remove(index);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        winnerThread.start();
        winnerThread.join();
        System.out.println(players.get(0).name);

    }
}
