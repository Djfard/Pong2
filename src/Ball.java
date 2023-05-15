import java.awt.*;

public class Ball {
    public static final int BALL_SIZE = 10;

    private int x, y;
    private int xVelocity;
    private int yVelocity;
    private int speed;

    public Ball(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed + 2;
        this.xVelocity = this.speed;
        this.yVelocity = this.speed;
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

        // Check collision with the left paddle
        if (x <= leftPaddle.getX() + Paddle.PADDLE_WIDTH &&
                y + BALL_SIZE >= leftPaddle.getY() &&
                y <= leftPaddle.getY() + Paddle.PADDLE_HEIGHT &&
                xVelocity < 0) {
            xVelocity = -xVelocity;
            // Move the ball back to the position where it should have hit the paddle
            x = leftPaddle.getX() + Paddle.PADDLE_WIDTH;
        }

        // Check collision with the right paddle
        if (x + BALL_SIZE >= rightPaddle.getX() &&
                y + BALL_SIZE >= rightPaddle.getY() &&
                y <= rightPaddle.getY() + Paddle.PADDLE_HEIGHT &&
                xVelocity > 0) {
            xVelocity = -xVelocity;
            // Move the ball back to the position where it should have hit the paddle
            x = rightPaddle.getX() - BALL_SIZE;
        }

        if (x < 0) {
            reset();
            Pong.incrementRightScore();
        }

        if (x + BALL_SIZE > Pong.WIDTH) {
            reset();
            Pong.incrementLeftScore();
        }
    }


    private void reset() {
        x = Pong.WIDTH / 2 - BALL_SIZE / 2;
        y = Pong.HEIGHT / 2 - BALL_SIZE / 2;
        xVelocity = speed;
        yVelocity = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public int getSpeed() {
        return speed;
    }
}
