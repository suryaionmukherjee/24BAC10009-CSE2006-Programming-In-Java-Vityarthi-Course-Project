public class Bowler extends Player {
    private int wicketsTaken;

    public Bowler(String name, int matchesPlayed, int wicketsTaken) {
        super(name, "Bowler", matchesPlayed);
        this.wicketsTaken = wicketsTaken;
    }

    public int getWicketsTaken() { return wicketsTaken; }

    public double getWicketsPerMatch() {
        if (matchesPlayed == 0) return 0.0;
        return (double) wicketsTaken / matchesPlayed;
    }

    @Override
    public String getPerformanceSummary() {
        return String.format("Wickets: %d | Wickets/Match: %.2f", wicketsTaken, getWicketsPerMatch());
    }
}