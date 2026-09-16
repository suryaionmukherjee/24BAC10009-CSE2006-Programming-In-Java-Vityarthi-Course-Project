import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CricketTeamManagerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TeamDatabase db = new TeamDatabase();
    private static final FileManager fileManager = new FileManager();
    private static final List<Player> rosterCache = new ArrayList<>();

    public static void main(String[] args) {
        loadRosterFromDatabase();
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: addPlayer(); break;
                case 2: viewAllPlayers(); break;
                case 3: viewPlayersByRole(); break;
                case 4: logMatchPerformance(); break;
                case 5: exportRosterToFile(); break;
                case 6: exportMatchHistoryToFile(); break;
                case 7:
                    running = false;
                    System.out.println("Exiting Cricket Team Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select between 1 and 7.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n===== CRICKET TEAM MANAGER =====");
        System.out.println("1. Add Player");
        System.out.println("2. View Full Roster");
        System.out.println("3. View Roster Filtered by Role");
        System.out.println("4. Log Match Performance");
        System.out.println("5. Export Roster to File");
        System.out.println("6. Export Match History Report");
        System.out.println("7. Exit");
        System.out.println("=================================");
    }

    private static void loadRosterFromDatabase() {
        rosterCache.clear();
        rosterCache.addAll(db.getAllPlayers());
    }

    private static void addPlayer() {
        try {
            System.out.print("Enter player name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter role (Batsman/Bowler): ");
            String role = scanner.nextLine().trim();

            System.out.print("Enter matches played: ");
            int matches = Integer.parseInt(scanner.nextLine().trim());

            validatePlayerInput(name, role, matches);

            Player newPlayer;
            if (role.equalsIgnoreCase("Bowler")) {
                System.out.print("Enter wickets taken: ");
                int wickets = Integer.parseInt(scanner.nextLine().trim());
                if (wickets < 0) throw new InvalidPlayerDataException("Wickets cannot be negative.");
                newPlayer = new Bowler(name, matches, wickets);
            } else {
                System.out.print("Enter runs scored: ");
                int runs = Integer.parseInt(scanner.nextLine().trim());
                if (runs < 0) throw new InvalidPlayerDataException("Runs cannot be negative.");
                newPlayer = new Batsman(name, matches, runs);
            }

            db.insertPlayer(newPlayer);
            loadRosterFromDatabase();
            System.out.println("Player added: " + newPlayer);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please enter numeric values for matches/runs/wickets.");
        } catch (InvalidPlayerDataException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private static void validatePlayerInput(String name, String role, int matches) throws InvalidPlayerDataException {
        if (name.isEmpty()) {
            throw new InvalidPlayerDataException("Player name cannot be empty.");
        }
        if (!role.equalsIgnoreCase("Batsman") && !role.equalsIgnoreCase("Bowler")) {
            throw new InvalidPlayerDataException("Role must be either 'Batsman' or 'Bowler'.");
        }
        if (matches < 0) {
            throw new InvalidPlayerDataException("Matches played cannot be negative.");
        }
    }

    private static void viewAllPlayers() {
        loadRosterFromDatabase();
        if (rosterCache.isEmpty()) {
            System.out.println("No players found in the roster.");
            return;
        }
        System.out.println("\n----- FULL TEAM ROSTER -----");
        for (Player p : rosterCache) {
            System.out.println(p);
        }
    }

    private static void viewPlayersByRole() {
        System.out.print("Enter role to filter (Batsman/Bowler): ");
        String role = scanner.nextLine().trim();

        List<Player> filtered = db.getPlayersByRole(role);
        if (filtered.isEmpty()) {
            System.out.println("No players found for role: " + role);
            return;
        }
        System.out.println("\n----- ROSTER FILTERED BY ROLE: " + role.toUpperCase() + " -----");
        for (Player p : filtered) {
            System.out.println(p);
        }
    }

    private static void logMatchPerformance() {
        loadRosterFromDatabase();
        if (rosterCache.isEmpty()) {
            System.out.println("No players in roster. Add a player first.");
            return;
        }

        MatchCountdownThread countdown = new MatchCountdownThread(5);
        countdown.start();
        try {
            countdown.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting for countdown.");
        }

        try {
            System.out.print("Enter player name for performance entry: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter match date (DD-MM-YYYY): ");
            String date = scanner.nextLine().trim();

            System.out.print("Enter runs scored in this match: ");
            int runs = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter wickets taken in this match: ");
            int wickets = Integer.parseInt(scanner.nextLine().trim());

            if (runs < 0 || wickets < 0) {
                throw new InvalidPlayerDataException("Runs and wickets cannot be negative.");
            }

            System.out.print("Enter notes (e.g. 'Man of the Match'): ");
            String notes = scanner.nextLine().trim();

            db.insertMatchPerformance(name, date, runs, wickets, notes);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input: runs and wickets must be numbers.");
        } catch (InvalidPlayerDataException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }

    private static void exportRosterToFile() {
        loadRosterFromDatabase();
        fileManager.exportRoster(rosterCache, "roster_export.txt");
    }

    private static void exportMatchHistoryToFile() {
        List<String> history = db.getAllMatchPerformances();
        fileManager.exportMatchHistory(history, "match_history_report.txt");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}