# CSE2006-Programming-In-Java-Vityarthi-Course-Project
# CLI Cricket Team Manager

## Overview
The Cricket Team Manager is a command-line interface (CLI) application developed in Java to streamline the management of cricket teams. It assists team administrators, coaches, and scorers in organizing squads, maintaining player records, and logging match performance data in a structured, centralized SQLite database.

## Features
*   **Player Management:** Add and view player records with specific roles (Batsman, Bowler) and statistics.
*   **Roster Filtering:** Filter and display players based on their assigned role (Batsman or Bowler).
*   **Match Performance Logging:** Record match results and individual player statistics (runs scored, wickets taken), and view the full match history.
*   **Multithreaded Simulation:** Uses a background thread to simulate a match-start countdown before performance entry.
*   **File I/O Export:** Export the complete team roster and match history to external text files for easy sharing.

## Technologies & Tools Used
*   **Language:** Java (OOP, Collections, Multithreading, Exception Handling, File I/O)
*   **Database:** SQLite (Embedded via Xerial JDBC Driver v3.53.4.0)
*   **Version Control:** Git & GitHub

## Steps to Install & Run
1. **Clone the repository:**
```bash
   git clone https://github.com/suryaionmukherjee/CSE2006-Programming-In-Java-Vityarthi-Course-Project.git
   cd CSE2006-Programming-In-Java-Vityarthi-Course-Project
```
2. **Compile the application:**
```bash
   javac -d bin src/*.java
```
3. **Run the application:**
```bash
   java --enable-native-access=ALL-UNNAMED -cp "bin:lib/*" CricketTeamManagerApp
```

## Instructions for Testing
1. Launch the application to access the main menu.
2. Select **Option 1** to add a new player (e.g., a Batsman or Bowler), testing the input validation constraints.
3. Select **Option 2** to view the complete list of all registered players in the database.
4. Select **Option 3** to filter the roster by a specific role (e.g., "Bowler") and verify the filtered output.
5. Select **Option 4** to log a match. Observe the multithreaded countdown delay before the performance is successfully recorded.
6. Select **Option 5** to export the team roster to a file, then verify the generated text file.
7. Select **Option 6** to export the match history to a file and verify the File I/O functionality.
8. Select **Option 7** to exit the application.
