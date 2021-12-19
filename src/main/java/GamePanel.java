import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Creates a new ball
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    // Creates our two paddles, centered on each side.
    public void newPaddles(){
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    // Handel all the collisions
    public void checkCollision(){

        // Bounce the ball of the top edge
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);    // Will go in the opposite direction
        }
        // Bounce the ball of the bottom edge
        else if(ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);    // Will go in the opposite direction
        }

        // Bounces ball of paddle1
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity); // Ball goes in opposite direction

            // The velocity is increased for every hit, makes the ball go faster.
            ball.xVelocity++;
            if(ball.yVelocity > 0){
                ball.yVelocity++;
            }
            else{
                ball.yVelocity--;
            }

            // We give the ball it's new direction
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        // Bounces ball of paddle2
        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);

            // The velocity is increased for every hit, makes the ball go faster.
            ball.xVelocity++;
            if(ball.yVelocity > 0){
                ball.yVelocity++;
            }
            else{
                ball.yVelocity--;
            }

            // We give the ball it's new direction
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        // Make sure that Paddle 1 stays within the size of the window
        if(paddle1.y <= 0){
            paddle1.y = 0;
        }
        else if(paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        // Make sure that Paddle 2 stays within the size of the window
        if(paddle2.y <= 0){
            paddle2.y = 0;
        }
        else if(paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT){
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        // Give a player 1 point, and create new paddles and ball
        if(ball.x <= 0){
            score.player2++;
            newPaddles();
            newBall();
        }
        else if(ball.x >= GAME_WIDTH - BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    // Game Loop
    public void run(){

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanoSeconds = 1000000000 / amountOfTicks;
        double delta = 0;

        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSeconds;
            lastTime = now;

            if(delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class ActionListener extends KeyAdapter{

        public void keyPressed(KeyEvent event){
            paddle1.keyPressed(event);
            paddle2.keyPressed(event);
        }

        public void keyReleased(KeyEvent event){
            paddle1.keyReleased(event);
            paddle2.keyReleased(event);
        }
    }

}
