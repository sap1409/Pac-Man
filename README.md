# Pac-Man Game - Assignment 3

## Note

Please ignore any printed statements on the terminal.

## Overview

This project is an extended version of the Pac-Man game developed for Assignment 3 of SOFT2201. The codebase builds on a provided scaffold, with new features implemented to add gameplay complexity and enhance user interaction. Key additions include new ghost types with distinct behaviors, power-ups like Power Pellets, and dynamic mode changes in ghost behavior.

## Features

The completed game includes the following new features:

- **New Ghost Types**: Four distinct ghost types (Blinky, Pinky, Inky, and Clyde), each with unique behaviors in `CHASE` and `SCATTER` modes.
- **Power Pellet**: Pac-Man can consume Power Pellets, temporarily gaining the ability to eat ghosts, who enter a `FRIGHTENED` mode when a Power Pellet is consumed.
- **Frightened Mode**: When in `FRIGHTENED` mode, ghosts exhibit randomized movement and are vulnerable to Pac-Man, leading to additional scoring opportunities.

## Design Patterns

The implementation uses several design patterns to ensure a modular, maintainable, and extensible codebase:

- **Strategy Pattern**: Used for defining unique `CHASE` mode behaviors for each ghost type, making it easy to add or adjust ghost strategies without changing the existing game logic.
- **State Pattern**: Applied to manage the various ghost modes (`CHASE`, `SCATTER`, and `FRIGHTENED`), allowing for clean transitions between behaviors.
- **Facade Pattern**: Introduced through `GhostManagerFacade` to handle complex state transitions and simplify ghost-specific logic management.

## Running the Game

### Prerequisites

- **JDK 17**: Ensure that Java Development Kit version 17 is installed.
- **Gradle 7.4.2**: The project uses Gradle for dependency management and building.

### Instructions

1. Clone or download the project files.
2. Navigate to the project root directory in a terminal.
3. Run the following commands:
   ```bash
   gradle clean build run
