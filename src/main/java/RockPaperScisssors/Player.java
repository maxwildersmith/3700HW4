package RockPaperScisssors;

public class Player {
    public Play play;
    public int id, score;

    public Player(int id){
        this.id = id;
        play = Play.pick();
    }

    public void reroll(){
        play = Play.pick();
    }

    public String getName(){
        return "Player "+id;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}
