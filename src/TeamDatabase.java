import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDatabase {
    private static final String DB_URL = "jdbc:sqlite:cricket_team.db";

   public TeamDatabase() {
        // Explicitly load the SQLite JDBC driver before doing anything else
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found in classpath! " + e.getMessage());
        }
        
        createTables();
    }

    private void createTables() {
        String playersTable = "CREATE TABLE IF NOT EXISTS players (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "role TEXT NOT NULL," +
                "matches_played INTEGER NOT NULL," +
                "stat_value INTEGER NOT NULL" +
                ")";

        String matchTable = "CREATE TABLE IF NOT EXISTS match_performance (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_name TEXT NOT NULL," +
                "match_date TEXT NOT NULL," +
                "runs_scored INTEGER NOT NULL," +
                "wickets_taken INTEGER NOT NULL," +
                "notes TEXT" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(playersTable);
            stmt.execute(matchTable);
        } catch (SQLException e) {
            System.out.println("Database setup error: " + e.getMessage());
        }
    }

    public void insertPlayer(Player p) {
        String sql = "INSERT INTO players(name, role, matches_played, stat_value) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getRole());
            ps.setInt(3, p.getMatchesPlayed());
            int statValue = (p instanceof Batsman) ? ((Batsman) p).getRunsScored()
                                                    : ((Bowler) p).getWicketsTaken();
            ps.setInt(4, statValue);
            ps.executeUpdate();
            System.out.println("Player saved to database successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting player: " + e.getMessage());
        }
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                players.add(buildPlayerFromRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching players: " + e.getMessage());
        }
        return players;
    }

    public List<Player> getPlayersByRole(String role) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players WHERE role = ? COLLATE NOCASE";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    players.add(buildPlayerFromRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error filtering players: " + e.getMessage());
        }
        return players;
    }

    private Player buildPlayerFromRow(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String role = rs.getString("role");
        int matches = rs.getInt("matches_played");
        int statValue = rs.getInt("stat_value");
        Player p;
        if (role.equalsIgnoreCase("Bowler")) {
            p = new Bowler(name, matches, statValue);
        } else {
            p = new Batsman(name, matches, statValue);
        }
        p.setId(rs.getInt("id"));
        return p;
    }

    public void insertMatchPerformance(String playerName, String date, int runs, int wickets, String notes) {
        String sql = "INSERT INTO match_performance(player_name, match_date, runs_scored, wickets_taken, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, playerName);
            ps.setString(2, date);
            ps.setInt(3, runs);
            ps.setInt(4, wickets);
            ps.setString(5, notes);
            ps.executeUpdate();
            System.out.println("Match performance logged successfully.");
        } catch (SQLException e) {
            System.out.println("Error logging match performance: " + e.getMessage());
        }
    }

    public List<String> getAllMatchPerformances() {
        List<String> records = new ArrayList<>();
        String sql = "SELECT * FROM match_performance ORDER BY id";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String line = String.format("Match #%d | Player: %-15s | Date: %-12s | Runs: %-4d | Wickets: %-3d | Notes: %s",
                        rs.getInt("id"), rs.getString("player_name"), rs.getString("match_date"),
                        rs.getInt("runs_scored"), rs.getInt("wickets_taken"), rs.getString("notes"));
                records.add(line);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching match history: " + e.getMessage());
        }
        return records;
    }
}