//Andy Qu
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.*;

public class Paddle extends Rectangle{
	int padId;
	int yVelocity;
	int speed = 10;
	Paddle(int x, int y, int width, int height,int id){
		super(x,y,width,height);
		this.padId = id;
	}
	public void setYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	public void move() {
		y= y + yVelocity;
	}
	//set keys for player 1 to w and s
	public void keyPressed(KeyEvent e) {
		if(padId == 1&&e.getKeyCode()==KeyEvent.VK_W) {
			setYDirection(-speed);
			move();
		}
		else if(padId == 1&&e.getKeyCode()==KeyEvent.VK_S) {
			setYDirection(speed);
			move();
		}
		else if(padId == 2 &&e.getKeyCode()==KeyEvent.VK_UP) {
			setYDirection(-speed);
			move();
		}
		else if(padId == 2&&e.getKeyCode()==KeyEvent.VK_DOWN) {
			setYDirection(speed);
			move();
		}
	}

	//set keys for player 1 to up and down
	public void keyReleased(KeyEvent e) {
		if(padId == 1&&e.getKeyCode()==KeyEvent.VK_W) {
			setYDirection(0);
			move();
		}
		else if(padId == 1&&e.getKeyCode()==KeyEvent.VK_S) {
			setYDirection(0);
			move();
		}
		else if(padId== 2 &&e.getKeyCode()==KeyEvent.VK_UP) {
			setYDirection(0);
			move();
		}
		else if(padId == 2&&e.getKeyCode()==KeyEvent.VK_DOWN) {
			setYDirection(0);
			move();
		}
	}
	
	//draw method for the paddles
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,y,width,height);
	}
}