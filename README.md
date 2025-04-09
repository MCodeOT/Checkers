# Checkers

A Java-based Checkers game with a command line interface (CLI) and a graphical user interface (GUI) built using JavaFX and a web-based frontend.

---

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Setup and Installation](#setup-and-installation)
5. [How to Play](#how-to-play)
6. [Project Structure](#project-structure)
7. [Contributing](#contributing)
8. [License](#license)

---

## Overview

This project implements a digital version of the classic Checkers game. It features a graphical user interface and a command line interface for gameplay and adheres to specific Checkers rules which are defined in [Rulebook.md](rulebook.md). The backend logic is written in Java, while the frontend uses HTML, CSS, and JavaScript.

---

## Features

- **Graphical User Interface**: Interactive checkerboard with drag-and-drop functionality. *#wip*
- **Game Rules**: Implements standard Checkers rules, including movement, capturing, and crowning - [Rulebook.md](rulebook.md).
- **Multiplayer**: Play against another player locally in couch co-op.
- **Customizable Board Size**: Supports 8x8, 10x10, and 12x12 boards.
- **Error Handling**: Highlights invalid moves and provides feedback.

---

## Technologies Used

- **Java**: Backend logic, game rules and command line interface.
- **JavaFX**: GUI framework for rendering the game.
- **HTML/CSS/JavaScript**: Frontend for the checkerboard and interactions.
- **Maven**: Build and dependency management.

---

## Setup and Installation

*#wip*

---

## How to Play

1. Launch the application.
2. Select in the terminal if you want to play the GUI version or the CLI version
3. Select the board size (8x8, 10x10 or 12x12)
4. The game starts with black pieces moving first.
5. Follow the standard Checkers rules:
   - Move diagonally on black squares.
   - Capture opponent pieces by jumping over them.
   - Crown pieces by reaching the opposite end of the board.
6. The game ends when one player has no pieces left.

For detailed rules, refer to the [Rulebook](rulebook.md).

---

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── de.check.checkers/
│   │       ├── App.java          # Main application entry point
│   │       ├── ui/               # User interface logic
│   │       ├── structures/       # Game structures (Board, Piece, Position)
│   │       └── utils/            # Game logic and utilities
│   ├── resources/
│       ├── Checkers.html         # Frontend HTML
│       ├── mystyle.css           # Frontend CSS
│       └── scripts.js            # Frontend JavaScript
└── test/                         # Unit tests (if applicable)
```

---

## License

*#wip*
