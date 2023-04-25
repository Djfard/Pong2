import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong implements ActionListener, KeyListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JFrame frame;
    private JPanel panel;
    private Timer timer;

    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;

    public Pong() {
        frame = new JFrame("Pong Game");
        timer = new Timer(1000 / 60, this);
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };

        leftPaddle = new Paddle(0, HEIGHT / 2 - Paddle.PADDLE_HEIGHT / 2);
        rightPaddle = new Paddle(WIDTH - Paddle.PADDLE_WIDTH, HEIGHT / 2 - Paddle.PADDLE_HEIGHT / 2);
        ball = new Ball(WIDTH / 2 - Ball.BALL_SIZE / 2, HEIGHT / 2 - Ball.BALL_SIZE / 2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        frame.addKeyListener(this);

        timer.start();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        leftPaddle.draw(g);
        rightPaddle.draw(g);
        ball.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        leftPaddle.update();
        rightPaddle.update();
        ball.update(leftPaddle, rightPaddle);

        panel.repaint();
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            rightPaddle.setUp(false);
        } else if (key == KeyEvent.VK_DOWN) {
            rightPaddle.setDown(false);
        }
    }

    public static void main(String[] args) {
        new Pong();
    }
}
