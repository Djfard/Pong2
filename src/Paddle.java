import java.awt.*;

public class Paddle {

    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 80;
    public static final int PADDLE_SPEED = 10;

    private int x;
    private int y;
    private int aiDifficulty;
    private boolean up, down;

    public Paddle(int x, int y, int aiDifficulty) {
        this.x = x;
        this.y = y;
        this.aiDifficulty = aiDifficulty;
        up = false;
        down = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void update(boolean isAI, Ball ball) {
        if (isAI) {
            double targetY = ball.getY() + (ball.getYVelocity() * (x - ball.getX()) / ball.getXVelocity());

            targetY -= PADDLE_HEIGHT / 2;
            targetY = Math.max(targetY, 0);
            targetY = Math.min(targetY, Pong.HEIGHT - PADDLE_HEIGHT);

            double diffY = targetY - y;

            int paddleSpeedAI;

            switch (aiDifficulty) {
                case 1: // Easy Mode
                    paddleSpeedAI = 6;
                    break;
                case 2: // Medium Mode
                    paddleSpeedAI = 8;
                    break;
                case 3: // Hard Mode
                    paddleSpeedAI = 10;
                    break;
                default:
                    paddleSpeedAI = 6;
            }

            if (diffY > 0) {
                y += Math.min(diffY, paddleSpeedAI);
            } else {
                y -= Math.min(-diffY, paddleSpeedAI);
            }

        } else {
            if (up && y > 0) {
                y -= PADDLE_SPEED;
            }

            if (down && y + PADDLE_HEIGHT < Pong.HEIGHT) {
                y += PADDLE_SPEED;
            }
        }

        y = Math.max(y, 0);
        y = Math.min(y, Pong.HEIGHT - PADDLE_HEIGHT);
    }




    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
