import java.awt.*;

public class Ball {
    public static final int BALL_SIZE = 10;
    public static final int BALL_SPEED = 5;

    private int x, y;
    private int xVelocity = BALL_SPEED;
    private int yVelocity = BALL_SPEED;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, BALL_SIZE, BALL_SIZE);
    }

    public void update(Paddle leftPaddle, Paddle rightPaddle) {
        x += xVelocity;
        y += yVelocity;

        if (y < 0 || y + BALL_SIZE > Pong.HEIGHT) {
            yVelocity = -yVelocity;
        }

        if (x < leftPaddle.getX() + Paddle.PADDLE_WIDTH &&
                y + BALL_SIZE > leftPaddle.getY() &&
                y < leftPaddle.getY() + Paddle.PADDLE_HEIGHT ||
                x + BALL_SIZE > rightPaddle.getX() &&
                        y + BALL_SIZE > rightPaddle.getY() &&
                        y < rightPaddle.getY() + Paddle.PADDLE_HEIGHT) {
            xVelocity = -xVelocity;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
