package RockPaperScisssors;

import RockPaperScisssors.Game1.Tech1;
import RockPaperScisssors.Game1.Tech3;


import java.util.*;

public class Main {
    public static void main(String[] args) {
        game1();
        game2();
    }

    private static void game1(){
        System.out.println("Starting with game 1 - Rock Paper Scissors Battle Royale");
        System.out.print("Enter number of players: ");
        int numPlayer = new Scanner(System.in).nextInt();
        System.out.println("Running Technique 1 - Fixed Thread Pool");
        long sTime = System.currentTimeMillis();
        new Tech1(numPlayer,Runtime.getRuntime().availableProcessors());
        System.out.println("Technique took "+(System.currentTimeMillis()-sTime)+"ms");

        System.out.println("\nRunning Technique 2 - Parallel Stream and Cached Pool");
        sTime = System.currentTimeMillis();
        new RockPaperScisssors.Game1.Tech2(numPlayer,Runtime.getRuntime().availableProcessors());
        System.out.println("Technique took "+(System.currentTimeMillis()-sTime)+"ms");

        System.out.println("\nRunning Technique 3 - Memoized");
        sTime = System.currentTimeMillis();
        new Tech3(numPlayer,Runtime.getRuntime().availableProcessors());
        System.out.println("Technique took "+(System.currentTimeMillis()-sTime)+"ms");
    }

    private static void game2(){
        System.out.println("\n\nStarting with game 2 - Rock Paper Scissors Tournament");
        System.out.print("Enter number of players: ");
        int numPlayer = new Scanner(System.in).nextInt();
        System.out.println("Running Technique 1 - Future List");
        long sTime = System.currentTimeMillis();
        new RockPaperScisssors.Game2.Tech1(numPlayer, Runtime.getRuntime().availableProcessors());

        System.out.println("Technique took "+(System.currentTimeMillis()-sTime)+"ms");

        System.out.println("\nRunning Technique 2 - Blocking Queue");
        sTime = System.currentTimeMillis();
        new RockPaperScisssors.Game2.Tech2(numPlayer,Runtime.getRuntime().availableProcessors());
        System.out.println("Technique took "+(System.currentTimeMillis()-sTime)+"ms");

    }
}
