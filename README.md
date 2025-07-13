# Flappy Bird

A simple Flappy Bird clone built using Java and [FXGL](https://github.com/AlmasB/FXGL), a game development framework for JavaFX.

## 📦 Features

- Player-controlled bird with gravity and flap mechanics
- Randomly spawning obstacles (pipes)
- Collision detection between player and obstacles
- Score tracking
- Game over state when the player hits an obstacle or falls off screen
- Clean and modular code structure (Entities, Components, Factory)

## 🚀 Getting Started

### Prerequisites

- Java 11+ (or any version supported by FXGL)
- Maven (optional, but recommended)

### Running the Game

1. Clone the repository:

   ```bash
   git clone https://github.com/LugolBis/FlappyBird.git
   cd FlappyBird
   ```

2. Build and run the project:

   - Using **Maven**:
     ```bash
     mvn clean javafx:run
     ```
   - Or directly from your IDE by running `App.java`.

### Controls

| Key        | Action        |
|------------|---------------|
| `SPACE`    | Flap upward   |
| `UP ARROW` | Flap upward   |

## 🎮 Gameplay

The player controls a bird that automatically falls due to gravity. Pressing `SPACE` or the `UP ARROW` makes the bird flap upward. Avoid hitting the pipes and try to achieve the highest score possible.
