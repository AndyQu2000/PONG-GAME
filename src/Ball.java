//Andy Qu
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle{
	
	Random random;
	int xVel,yVel;
	int speed = 8;
//	makes ball go in random direction when it first starts
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		random = new Random();
		int randomXDir = random.nextInt(2);
		if(randomXDir == 0)
			randomXDir--;
		setXDir(randomXDir*speed);
		
		int randomYDir = random.nextInt(2);
		if(randomYDir == 0)
			randomYDir--;
		setYDir(randomYDir*speed);
		
	}
	//used in pongpanel to set different speeds at menu
	public void setSpeed(int sp) {
		speed = sp;
	}
//	resets the ball to the center
//	used when ball goes out of bound/when player scores
	public void reset(int x, int y) {
		this.x = x;
		this.y = y;
		int randomXDir = random.nextInt(2);
		if(randomXDir == 0)
			randomXDir--;
		setXDir(randomXDir*speed);
		
		int randomYDir = random.nextInt(2);
		if(randomYDir == 0)
			randomYDir--;
		setYDir(randomYDir*speed);

	}
	
	public void setXDir(int randomXDir) {
		xVel = randomXDir;
	}
	public void setYDir(int randomYDir) {
		yVel = randomYDir;
	}
	public void move() {
		x=x+xVel;
		y=y+yVel;
	}
	//used for drawing ball
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, width, height);
	}
	
}