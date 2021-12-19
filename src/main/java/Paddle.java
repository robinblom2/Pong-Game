import java.awt.*;
import java.awt.event.*;



public class Paddle extends Rectangle {

    int id;
    int yVelocity;
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    // Moves the paddles
    public void keyPressed(KeyEvent event){

        switch(id){
            case 1:
                if(event.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(-speed);
                    move();
                }
                if(event.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(speed);
                    move();
                }
                break;
            case 2:
                if(event.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(-speed);
                    move();
                }
                if(event.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(speed);
                    move();
                }
                break;
        }
    }

    // Stop moving paddles when released
    public void keyReleased(KeyEvent event){

        switch(id){
            case 1:
                if(event.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(0);
                    move();
                }
                if(event.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if(event.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(0);
                    move();
                }
                if(event.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(0);
                    move();
                }
                break;
        }

    }

    // Set the direction of the paddle
    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    // Move the paddle
    public void move(){
        y = y + yVelocity;
    }

    // Draw the paddles
    public void draw(Graphics g){
        if(id == 1){
            g.setColor(Color.blue);
        }
        else{
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
    }



}
