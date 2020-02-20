package RockPaperScisssors;

public enum Play {
    Rock, Paper, Scissors;

    public static Play pick() {
        return values()[(int) (Math.random() * values().length)];
    }

    public int score(Play hand) {
        if(hand==this)
            return 0;
        switch (hand) {
            case Rock:
                return this == Paper?1:-1;
            case Paper:
                return this == Scissors?1:-1;
            case Scissors:
                return this == Rock?1:-1;
        }
        return 0;
    }
}
