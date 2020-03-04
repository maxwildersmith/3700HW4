package RockPaperScisssors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Player {
    public Play play;
    public int id, score;
    public Future<Integer> futureScore;
    private boolean updated= false;

    /**
     * Creates a new player with a certain ID
     * @param id The player's id
     */
    public Player(int id){
        this.id = id;
        play = Play.pick();
    }

    /**
     * Picks a new play for the player
     */
    public void reroll(){
        updated = false;
        play = Play.pick();
    }

    /**
     * Returns the player's name
     * @return This player's name
     */
    public String getName(){
        return "Player "+id;
    }

    /**
     * Sets the score using a Future value
     * @param val The Future int
     */
    public void setFutureScore(Future<Integer> val){
        futureScore = val;
    }

    /**
     * Sets the score of this player
     * @param score The new score for the player
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Gets the player's score
     * @return The score
     */
    public int getScore(){
        if(updated)
            return score;
        updated = true;
        try {
            score = futureScore.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return score;
    }

    @Override
    public String toString() {
        return getName()+" play: "+play;
    }
}
