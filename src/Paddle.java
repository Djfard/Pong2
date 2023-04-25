import java.awt.*;

public class Paddle {

    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 80;
    public static final int PADDLE_SPEED = 5;

    private int x;
    private int y;
    private boolean up, down;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
        up = false;
        down = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void update() {
        if (up && y > 0) {
            y -= PADDLE_SPEED;
        }

        if (down && y + PADDLE_HEIGHT < Pong.HEIGHT) {
            y += PADDLE_SPEED;
        }
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
