package RockPaperScisssors.Game2;

import RockPaperScisssors.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class Tech1 {

    public Tech1(int numPlayers){

        Queue<Match> matches = new LinkedList<>();
        for(int i=0;i<numPlayers;i+=2) {
            int finalI = i;
            matches.add(new Match(new Player(finalI),new Player(finalI +1)));
        }
        while (matches.size()>1){
            matches.add(new Match(matches.poll().getResult(),matches.poll().getResult()));
        }
        System.out.println(matches.poll().getResult().getName()+" wins");

    }
}
