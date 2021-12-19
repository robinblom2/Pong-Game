import java.awt.*;
import java.util.*;



public class Ball extends Rectangle {

    Random random;
    int xVelocity;          // How fast the ball moves on the x & y-axis
    int yVelocity;
    int initialSpeed = 2;   // Speed of the ball

    // Set the random default direction of the ball
    Ball(int x, int y, int width, int height){
        super(x, y, width, height);

        random = new Random();

        // Set x-direction
        int randomXDirection = random.nextInt(2);   // 0 = go left, 1 = go right

        // Ball goes left
        if(randomXDirection == 0){
            randomXDirection--;
            setXDirection(randomXDirection * initialSpeed);
        }
        // Ball goes right
        else{
            randomXDirection++;
            setXDirection(randomXDirection * initialSpeed);
        }

        // Set y-direction
        int randomYDirection = random.nextInt(2);

        // Ball goes up
        if(randomYDirection == 0){
            randomYDirection--;
            setYDirection(randomYDirection * initialSpeed);
        }
        // Ball goes down
        else{
            randomYDirection++;
            setYDirection(randomYDirection * initialSpeed);
        }

    }

    // Set how fast the ball moves on the x-axis
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    // Set how fast the ball moves on the y-axis
    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

    // Move the ball on the x & y-axis
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    // Draw the ball
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }

}
