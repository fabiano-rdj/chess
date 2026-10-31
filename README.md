# Java Chess Project

## CS3354 — Object-Oriented Programming | Phase 1

### Project Overview

Java Chess Project is a two-player, console-based chess application developed in Java using object-oriented programming principles.

The application allows two players to play chess using standard chess coordinates, such as `E2 E4`. The program displays an 8×8 chessboard, validates moves, manages player turns, and applies chess rules.

This project demonstrates inheritance, encapsulation, abstraction, polymorphism, and the organization of Java classes into packages.

## Technologies and Requirements

- Programming language: Java
- Java version: JDK 21 or later
- Build system: Gradle
- Development environment: Visual Studio Code
- Operating systems: macOS, Windows, or Linux

## Project Structure

```text
ChessProject/
├── app/
│   ├── src/
│   │   ├── main/java/chessproject/
│   │   │   ├── console/
│   │   │   │   └── ChessApp.java
│   │   │   ├── model/
│   │   │   │   ├── Board.java
│   │   │   │   ├── Position.java
│   │   │   │   └── PieceColor.java
│   │   │   ├── pieces/
│   │   │   │   ├── Piece.java
│   │   │   │   ├── Pawn.java
│   │   │   │   ├── Rook.java
│   │   │   │   ├── Knight.java
│   │   │   │   ├── Bishop.java
│   │   │   │   ├── Queen.java
│   │   │   │   └── King.java
│   │   │   └── service/
│   │   │       └── ChessGame.java
│   │   └── test/
│   └── build.gradle
├── gradle/
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## Object-Oriented Programming Concepts

**Encapsulation:** Classes store their data in private fields and provide public methods to access or modify it.

**Inheritance:** Pawn, Rook, Knight, Bishop, Queen, and King inherit from the abstract `Piece` class.

**Abstraction:** The `Piece` class defines shared behavior for chess pieces while requiring subclasses to implement their movement patterns.

**Polymorphism:** The chessboard stores different piece types using `Piece` references. Each piece implements its own movement behavior.

## Completed Features

- 8×8 chessboard with standard starting positions
- All six chess piece types
- White and Black players
- Console-based chessboard display
- Chess-coordinate input
- Movement-pattern validation
- Blocked-path detection
- Capturing opponent pieces
- Alternating player turns
- Check detection
- Checkmate and stalemate detection
- Castling
- En passant
- Pawn promotion
- Invalid-move handling
- Console demonstration and movement tests

## Setup Instructions

1. Install JDK 21 or later.
2. Download or clone the repository.
3. Open the project folder in Visual Studio Code.
4. Open the integrated terminal.
5. Verify that Java is installed:

```bash
java -version
```

6. Compile the project:

```bash
./gradlew :app:compileJava
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Running the Application

### Run the demonstration tests

```bash
./gradlew :app:run --console=plain
```

### Play the console chess game

```bash
./gradlew :app:run --args="--play" --console=plain
```

The entry-point class is `chessproject.console.ChessApp`.

## How to Play

The program displays the chessboard and prompts the current player to enter a move.

Enter the starting and destination squares separated by a space.

Example:

```text
WHITE move > E2 E4
BLACK move > E7 E5
WHITE move > G1 F3
BLACK move > B8 C6
```

Additional commands:

- `help` — Display input instructions.
- `quit` — Exit the application.

For pawn promotion, enter a promotion piece when prompted or use the format supported by the application.

## Generate Javadoc

Run:

```bash
./gradlew :app:javadoc
```

The generated HTML documentation is located at:

```text
app/build/docs/javadoc/index.html
```

On macOS, open it using:

```bash
open app/build/docs/javadoc/index.html
```

## Known Limitations

- The application uses a text-based interface rather than a graphical chessboard.
- Players enter moves manually using chess coordinates.
- The application does not provide a computer-controlled opponent.
- The current version does not implement every official chess draw rule.
- Games are not automatically saved and restored between sessions.

## Testing

The project includes demonstration tests for chess piece movement, initial board setup, and invalid moves.

Additional game scenarios can be tested by running the interactive console application.

## Future Improvements

- Graphical user interface
- Player-versus-computer mode
- Save and load games
- Move history and algebraic notation
- Additional chess draw rules
- Expanded automated unit testing

## GitHub Repository

https://github.com/fabiano-rdj/chess

## Academic Project

Developed for CS3354 — Object-Oriented Programming, Phase 1.

AI assistance was used during development for explanations, code drafting, debugging, and documentation. The project should be reviewed, tested, and understood by its submitter before submission.
