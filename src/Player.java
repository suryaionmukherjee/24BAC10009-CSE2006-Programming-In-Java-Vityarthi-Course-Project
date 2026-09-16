public abstract class Player {
    protected int id;
    protected String name;
    protected String role;
    protected int matchesPlayed;

    public Player(String name, String role, int matchesPlayed) {
        this.name = name;
        this.role = role;
        this.matchesPlayed = matchesPlayed;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getMatchesPlayed() { return matchesPlayed; }

    // Each subclass defines its own performance summary (polymorphism)
    public abstract String getPerformanceSummary();

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Role: " + role +
               " | Matches: " + matchesPlayed + " | " + getPerformanceSummary();
    }
}