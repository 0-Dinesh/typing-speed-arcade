import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Arrays;
import java.awt.image.BufferStrategy;

public class TypingGame extends JFrame implements KeyListener, Runnable {

    private static final long serialVersionUID = 1L;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 400;
    private static final int MAX_SPEED = 15;
    private static final int MIN_SPEED = 5;

    private int bulletSpeed = MIN_SPEED;
    private int bulletX = WINDOW_WIDTH - 100;
    private int bulletY = WINDOW_HEIGHT / 2;
    private String currentWord;
    private String typedWord = "";
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private int wordIndex = 0;
    private int score = 0;

    private Timer timer;
    private BufferStrategy bufferStrategy;

    private static final List<String> words = Arrays.asList("sun", "fox", "book", "fish", "rain", "house", "river", "apple", "forest", "bridge", "planet", "amazing", "picture", "journey", "elephant", "mountains", "sentence", "beautiful", "adventure", "knowledge", "celebration", "supermarket", "enthusiasm", "exploration", "friendliness", "commitments", "understanding", "transformative", "magnificence", "extraordinary", "unpredictable", "incomprehensible");

    public TypingGame() {
        setTitle("Typing Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);

        getContentPane().setBackground(Color.WHITE);

        currentWord = generateWord();

        timer = new Timer(1000 / bulletSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameStarted && !gameOver) {
                    moveBullet();
                    checkCollision();
                    increaseDifficulty();
                    repaint();
                }
            }
        });
    }

    private String generateWord() {
        String word = words.get(wordIndex);
        wordIndex = (wordIndex + 1) % words.size();
        return word;
    }

    private int calculateScore(String word) {
        int length = word.length();
        int baseScore = (length - 1); // Base score calculation
        int bonus = (wordIndex / 5) * 4; // Add 4 points for each five consecutive words
        return baseScore + bonus;
    }

    private void moveBullet() {
        bulletX -= bulletSpeed;
        if (bulletX < 0) {
            bulletX = WINDOW_WIDTH - 100;
            currentWord = generateWord();
            typedWord = "";
        }
    }

    private void checkCollision() {
        if (bulletX <= 100 && bulletX >= 50) {
            if (typedWord.equals(currentWord)) {
                score += calculateScore(currentWord);
                currentWord = generateWord();
                typedWord = "";
                bulletX = WINDOW_WIDTH - 100;
            } else {
                gameOver = true;
                typedWord = "";
                repaint();
            }
        }
    }

    private void increaseDifficulty() {
        if (bulletSpeed < MAX_SPEED) {
            bulletSpeed++;
            timer.setDelay(1000 / bulletSpeed);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (!gameStarted) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Serif", Font.BOLD, 36));
            g2d.drawString("TYPING GAME", WINDOW_WIDTH / 2 - 150, 80);
            g2d.setFont(new Font("Serif", Font.PLAIN, 18));
            g2d.drawString("A WORD BASED ARCADE GAME", WINDOW_WIDTH / 2 - 130, 110);
            g2d.drawString("Press any key to start", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
            bufferStrategy.show();
            g2d.dispose();
            return;
        }

        if (gameOver) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Serif", Font.BOLD, 36));
            g2d.drawString("Game Over!", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
            g2d.setFont(new Font("Serif", Font.PLAIN, 20));
            g2d.drawString("Score: " + score, WINDOW_WIDTH / 2 - 50, WINDOW_HEIGHT / 2 - 40);
            g2d.drawString("Press Ctrl + Enter to restart", WINDOW_WIDTH / 2 - 120, WINDOW_HEIGHT / 2 + 40);
            bufferStrategy.show();
            g2d.dispose();
            return;
        }

        g2d.setColor(Color.GREEN);
        g2d.fillRect(50, WINDOW_HEIGHT / 2 - 25, 50, 50);

        g2d.setColor(Color.RED);
        g2d.fillOval(WINDOW_WIDTH - 100, WINDOW_HEIGHT / 2 - 25, 50, 50);

        g2d.setColor(Color.RED);
        g2d.fillOval(bulletX, bulletY, 20, 20);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 24));
        g2d.drawString("Type this word: " + currentWord, WINDOW_WIDTH / 2 - 100, 50);

        g2d.setFont(new Font("Serif", Font.PLAIN, 20));
        g2d.drawString("Typed: " + typedWord, WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT - 60);

        bufferStrategy.show();
        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!gameStarted || gameOver) {
            return;
        }

        char keyChar = e.getKeyChar();
        if (typedWord.length() < currentWord.length() && keyChar == currentWord.charAt(typedWord.length())) {
            typedWord += keyChar;
        }

        repaint();
    }

    @Override
public void keyPressed(KeyEvent e) {
    // Start the game if it has not started yet
    if (!gameStarted) {
        startGame();
    } 
    // Check for Ctrl + Enter to restart the game if game is over
    else if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
        startGame();
    } 
    // Move to the next word if Enter is pressed and the typed word matches the current word
    else if (e.getKeyCode() == KeyEvent.VK_ENTER && typedWord.equals(currentWord)) {
        score += calculateScore(currentWord);  // Update the score
        currentWord = generateWord();          // Move to the next word
        typedWord = "";                        // Reset the typed word
        bulletX = WINDOW_WIDTH - 100;          // Reset bullet position
        repaint();
    }
}


    @Override
    public void keyReleased(KeyEvent e) {}

    private void startGame() {
        gameStarted = true;
        gameOver = false;
        bulletSpeed = MIN_SPEED;
        bulletX = WINDOW_WIDTH - 100;
        wordIndex = 0;
        score = 0;
        typedWord = "";
        currentWord = generateWord();
        timer.start();
        repaint();
    }

    @Override
    public void run() {
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TypingGame game = new TypingGame();
                game.setVisible(true);
                game.run();
            }
        });
    }
}
