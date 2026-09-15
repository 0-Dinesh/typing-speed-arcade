# Typing Speed Arcade

An event-driven 2D desktop typing game written in Java, featuring smooth hardware-accelerated rendering, real-time input evaluation, and adaptive projectile speed mechanics.

---

## Gameplay Mechanics

- **Incoming Projectile:** A target projectile travels from the right boundary toward the critical zone on the left.
- **Real-Time Input Matching:** Words must be typed letter-for-letter before the projectile enters the critical collision boundary ($50 \le x \le 100$).
- **Dynamic Difficulty:** Projectile velocity scales incrementally with each completed round, decreasing the user's reaction window.
- **Scoring Engine:** Evaluates base word length points added to consecutive word streak multipliers:
  $$\text{Score} = (\text{length} - 1) + \left(\left\lfloor\frac{\text{wordIndex}}{5}\right\rfloor \times 4\right)$$

---

## Technical Highlights

- **Hardware Double Buffering:** Utilizes `java.awt.image.BufferStrategy` with a dual-buffer configuration to eliminate screen flicker and ensure stable frame rendering.
- **Event-Driven Key Input:** Implements `KeyListener` methods (`keyTyped`, `keyPressed`) to validate matching characters at runtime and prevent incorrect key capture.
- **Swing Timer Game Loop:** Drives frame updates, trajectory kinematics, collision detection, and rendering cycles at controlled tick intervals.

---

## Project Structure

```text
typing-speed-arcade/
├── src/
│   └── TypingGame.java
├── .gitignore
├── LICENSE
└── README.md
```

---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed.

### Compilation & Execution via Source

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/typing-speed-arcade.git
   cd typing-speed-arcade/src
   ```

2. Compile the Java source file:
   ```bash
   javac TypingGame.java
   ```

3. Run the compiled class:
   ```bash
   java TypingGame
   ```

### Running the Standalone Application (Windows)

This version is bundled with a pre-configured Java Runtime Environment (JRE). You do not need to install Java on your system to play the game.

1. Navigate to the **Releases** section on the right side of this repository.
2. Download the latest `Typing-Game.zip` file.
3. Extract the downloaded `.zip` folder to your desired location.
4. Open the extracted folder and double-click `Typing game.exe` to launch the application.

---

## Controls

| Key Action | Function |
|---|---|
| **Any Key** | Starts the game from the home menu |
| **A–Z** | Types the targeted characters |
| **Enter** | Confirms completion of the matching word |
| **Ctrl + Enter** | Restarts the game after Game Over |

---

## License

This project is licensed under the [MIT License](LICENSE).
