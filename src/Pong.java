import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.ArrayList;

public class Pong implements ActionListener, KeyListener {

    public static int WIDTH, HEIGHT;

    private JFrame frame;
    private JPanel panel;
    private Timer timer;

    private Paddle leftPaddle;
    private Paddle rightPaddle;

    private List<Ball> balls = new ArrayList<>();

    private Ball ball;

    public static int leftScore = 0, rightScore = 0;

    private static int gameMode;
    private static int difficulty;
    private static int ballSpeed;
    private static int numberOfBalls;
    private static boolean isPlayerVsPlayer;
    private static int aiDifficulty;

    public int getAiDifficulty() {
        return aiDifficulty;
    }

    public Pong(int gameMode, int difficulty, int ballSpeed, int numberOfBalls) {

        this.gameMode = gameMode;
        this.difficulty = difficulty;
        this.ballSpeed = ballSpeed;

        WIDTH = 800;
        HEIGHT = 600;

        frame = new JFrame("Pong Game");
        timer = new Timer(1000 / 60, this);
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();

        leftPaddle = new Paddle(0, HEIGHT / 2 - Paddle.PADDLE_HEIGHT / 2, difficulty);
        if (isPlayerVsPlayer) {
            rightPaddle = new Paddle(WIDTH - Paddle.PADDLE_WIDTH, HEIGHT / 2 - Paddle.PADDLE_HEIGHT / 2, aiDifficulty);
        } else {
            rightPaddle = new Paddle(WIDTH - Paddle.PADDLE_WIDTH - 10, HEIGHT / 2 - Paddle.PADDLE_HEIGHT / 2, aiDifficulty);
        }

        for (int i = 0; i < numberOfBalls; i++) {
            final int index = i;
            new Timer(i * 2000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    balls.add(new Ball(WIDTH / 2 - Ball.BALL_SIZE / 2, HEIGHT / 2 - Ball.BALL_SIZE / 2, ballSpeed));
                    ((Timer)evt.getSource()).stop(); // stop the timer after it's done
                }
            }).start();
        }

        frame.addKeyListener(this);

        timer.start();
    }

    public static boolean isPlayerVsPlayer() {
        return isPlayerVsPlayer;
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        leftPaddle.draw(g);
        rightPaddle.draw(g);

        // Draw each ball
        for (Ball ball : balls) {
            ball.draw(g);
        }

        // Draw the scores
        drawScore(g, true);
        drawScore(g, false);
    }

    private void drawScore(Graphics g, boolean isLeft) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        int score = isLeft ? leftScore : rightScore;
        int xPosition = isLeft ? WIDTH / 4 : WIDTH * 3 / 4;
        g.drawString(String.valueOf(score), xPosition, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Ball closestBallToLeftPaddle = getClosestBallToPaddle(leftPaddle);
        Ball closestBallToRightPaddle = getClosestBallToPaddle(rightPaddle);

        leftPaddle.update(false, closestBallToLeftPaddle);
        if (isPlayerVsPlayer) {
            rightPaddle.update(false, closestBallToRightPaddle);
        } else {
            rightPaddle.update(true, closestBallToRightPaddle);
        }

        // Update each ball
        for (Ball ball : balls) {
            ball.update(leftPaddle, rightPaddle);
        }

        panel.repaint();
    }

    private Ball getClosestBallToPaddle(Paddle paddle) {
        Ball closestBall = null;
        double closestDistance = Double.MAX_VALUE;

        for (Ball ball : balls) {
            double distance = Math.sqrt(Math.pow(ball.getX() - paddle.getX(), 2) + Math.pow(ball.getY() - paddle.getY(), 2));
            if (distance < closestDistance) {
                closestDistance = distance;
                closestBall = ball;
            }
        }

        return closestBall;
    }

    public static void incrementLeftScore() {
        leftScore++;
    }

    public static void incrementRightScore() {
        rightScore++;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            rightPaddle.setUp(true);
        } else if (key == KeyEvent.VK_DOWN) {
            rightPaddle.setDown(true);
        }

        if (key == KeyEvent.VK_W) {
            leftPaddle.setUp(true);
        } else if (key == KeyEvent.VK_S) {
            leftPaddle.setDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            rightPaddle.setUp
                    (false);
        } else if (key == KeyEvent.VK_DOWN) {
            rightPaddle.setDown(false);
        }

        if (key == KeyEvent.VK_W) {
            leftPaddle.setUp(false);
        } else if (key == KeyEvent.VK_S) {
            leftPaddle.setDown(false);
        }
    }

    public static void main(String[] args) {
        showMenu();
        new Pong(gameMode, difficulty, ballSpeed, numberOfBalls);
    }

    public static void showMenu() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
            }
        }

        // Panel
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // game mode
        String[] gameModes = { "Player vs Player", "Player vs AI" };
        JComboBox<String> gameModeBox = new JComboBox<>(gameModes);
        panel.add(new JLabel("Game Mode:"));
        panel.add(gameModeBox);

        // AI difficulty
        String[] difficulties = { "Easy", "Medium", "Hard" };
        JComboBox<String> difficultyBox = new JComboBox<>(difficulties);
        panel.add(new JLabel("AI Difficulty:"));
        panel.add(difficultyBox);

        // ball speed
        JSlider ballSpeedSlider = new JSlider(1, 10, ballSpeed != 0 ? ballSpeed : 5);
        ballSpeedSlider.setMajorTickSpacing(1);
        ballSpeedSlider.setPaintTicks(true);
        ballSpeedSlider.setPaintLabels(true);
        panel.add(new JLabel("Ball Speed:"));
        panel.add(ballSpeedSlider);

        // number of balls
        JSlider numberOfBallsSlider = new JSlider(1, 3, numberOfBalls != 0 ? numberOfBalls : 1);
        numberOfBallsSlider.setMajorTickSpacing(1);
        numberOfBallsSlider.setPaintTicks(true);
        numberOfBallsSlider.setPaintLabels(true);
        panel.add(new JLabel("Number of Balls:"));
        panel.add(numberOfBallsSlider);

        // show the panel in a dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Game Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // update game configuration based on user input
            isPlayerVsPlayer = gameModeBox.getSelectedIndex() == 0;
            aiDifficulty = difficultyBox.getSelectedIndex() + 1;
            ballSpeed = ballSpeedSlider.getValue();
            numberOfBalls = numberOfBallsSlider.getValue();
        } else {
            System.exit(0);
        }
    }

}
