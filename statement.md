# Project Statement: Cricket Team Manager

## Problem Statement
Amateur and semi-professional cricket teams frequently rely on informal methods—such as spreadsheets, paper scoresheets, or verbal communication—to track player details, team composition, and match outcomes. This approach is error-prone, difficult to search or filter, and offers no historical continuity between matches or seasons. There is a critical need for a lightweight software tool to consolidate these responsibilities into a single, reliable system.

## Scope of the Project
This project focuses on delivering a robust, console-based application for local team management. The scope includes persistent database storage for player and match records, roster filtering capabilities, and the generation of external text reports. The system is designed to be completely self-contained and portable, intentionally omitting a graphical user interface (GUI) or networked multi-device synchronization to ensure it remains lightweight and executable in resource-constrained environments.

## Target Users
*   **Team Managers & Administrators:** For maintaining accurate, up-to-date player rosters.
*   **Coaches:** For reviewing recorded performance data and filtering squads by role.
*   **Scorers:** For logging match results and individual player statistics reliably.

## High-Level Features
*   **Centralized Record Keeping:** Persistent create and read operations for player and match records utilizing an embedded SQLite database.
*   **Categorized Roster Views:** On-demand filtering of players based on specific team roles.
*   **Match Logging with Countdown Simulation:** Recording of match performance data, preceded by a simulated background countdown thread.
*   **Report Generation:** Quick exportation of database records into human-readable text files.
