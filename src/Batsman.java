public class Batsman extends Player {
    private int runsScored;

    public Batsman(String name, int matchesPlayed, int runsScored) {
        super(name, "Batsman", matchesPlayed);
        this.runsScored = runsScored;
    }

    public int getRunsScored() { return runsScored; }

    public double getBattingAverage() {
        if (matchesPlayed == 0) return 0.0;
        return (double) runsScored / matchesPlayed;
    }

    @Override
    public String getPerformanceSummary() {
        return String.format("Runs: %d | Batting Avg: %.2f", runsScored, getBattingAverage());
    }
}