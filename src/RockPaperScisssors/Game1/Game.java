package RockPaperScisssors.Game1;

import RockPaperScisssors.Play;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Game implements Callable<Integer> {
    Play play;
    int id, score;
    CountDownLatch finishedLatch;
    List<Game> players;
    String name;

    public Game(int id, CountDownLatch setupLatch, CountDownLatch finishedLatch, List<Game> players, String name) {
        this.id = id;
        this.finishedLatch = finishedLatch;
        this.players = players;
        this.name = name;
        System.out.println(name+" was created");
        setupLatch.countDown();
    }

    public void init(){
        play = Play.pick();
    }

    @Override
    public Integer call() {
        for(int i=0;i<players.size();i++)
            if(id!=i)
                score+=play.score(players.get(i).play);
            finishedLatch.countDown();
            return score;
    }
}
