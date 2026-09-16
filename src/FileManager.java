import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {

    public void exportRoster(List<Player> players, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("===== CRICKET TEAM ROSTER =====");
            for (Player p : players) {
                writer.println(p.toString());
            }
            writer.println("================================");
            System.out.println("Roster exported to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing roster file: " + e.getMessage());
        }
    }

    public void exportMatchHistory(List<String> records, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("===== MATCH HISTORY REPORT =====");
            if (records.isEmpty()) {
                writer.println("No match records found.");
            } else {
                for (String record : records) {
                    writer.println(record);
                }
            }
            writer.println("=================================");
            System.out.println("Match history exported to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing match history file: " + e.getMessage());
        }
    }
}