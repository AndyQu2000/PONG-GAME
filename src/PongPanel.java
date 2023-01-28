//Andy Qu
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PongPanel extends JPanel implements Runnable{
	Ball ball;
	Graphics graphics;
	Image image;
	Thread thread;
	Random random;
	Paddle paddle1,paddle2;
	JLabel P1ScoreLabel,P2ScoreLabel,ScoreDividerLabel;
	int P1Score = 0;
	int P2Score = 0;
	boolean StartGame = false;
	JButton SpeedButton,GamePointButton,StartButton;
	JLabel SpeedLabel,GamePointLabel,endLabel;
	int GamePoint = 5;

	public PongPanel() {
		//ball(x location on panel,y location on panel,width of ball(diameter), height of ball(diameter))
		ball = new Ball(500,262,20,20);
		//Paddle(x location on panel, yLocation on panel, width size,height size, numbered id)
		paddle1 = new Paddle(0,228,25,100,1);
		paddle2 = new Paddle(975,228,25,100,2);
		this.setFocusable(true);
		//implements the key functions for moving the paddles
		this.addKeyListener(new AL());
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1000,555));
		//keeps window in the middle of the screen
		setLayout(null);
		
		P1ScoreLabel = new JLabel(Integer.toString(P1Score));
		P1ScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		P1ScoreLabel.setBounds(387, 14, 92, 43);
		P1ScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		P1ScoreLabel.setForeground(Color.WHITE);
		add(P1ScoreLabel);
		
		ScoreDividerLabel = new JLabel("|");
		ScoreDividerLabel.setBounds(484, 5, 32, 61);
		ScoreDividerLabel.setForeground(Color.WHITE);
		ScoreDividerLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
		add(ScoreDividerLabel);
		
		P2ScoreLabel = new JLabel(Integer.toString(P2Score));
		P2ScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
		P2ScoreLabel.setBounds(521, 14, 92, 43);
		P2ScoreLabel.setForeground(Color.WHITE);
		P2ScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		add(P2ScoreLabel);
		
		//changes speed of ball with 4 options
		//8-normal
		//12-fast
		//15-faster
		//18-fastest
		SpeedButton = new JButton("Change Speed");
		SpeedButton.setBackground(new Color(0, 0, 0));
		SpeedButton.setFocusable(false);
		SpeedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				if(ball.speed==8) {
					ball.setSpeed(12);
					ball.reset(500, 262);
					SpeedLabel.setText("FAST");
				}
				else if(ball.speed==12) {
					ball.setSpeed(15);
					ball.reset(500, 262);
					SpeedLabel.setText("FASTER");
				}
				else if(ball.speed==15) {
					ball.setSpeed(18);
					ball.reset(500, 262);
					SpeedLabel.setText("FASTEST");
				}
				else {
					ball.setSpeed(8);
					ball.reset(500, 262);
					SpeedLabel.setText("NORMAL");
					
				
				}
			}
		});
		SpeedButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SpeedButton.setForeground(new Color(255, 255, 255));
		SpeedButton.setBounds(287, 81, 192, 43);
		add(SpeedButton);
		SpeedLabel = new JLabel("NORMAL");
		SpeedLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SpeedLabel.setForeground(new Color(255, 255, 255));
		SpeedLabel.setBounds(521, 81, 160, 48);
		add(SpeedLabel);
		
		//button to start the game
		//all settings become invisible once game starts
		
		StartButton = new JButton("START");
		StartButton.setForeground(new Color(255, 255, 255));
		StartButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		StartButton.setBackground(Color.BLACK);
		StartButton.setBounds(412, 193, 152, 43);
		StartButton.setFocusable(false);
		StartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				StartButton.setVisible(false);
				SpeedButton.setVisible(false);
				GamePointButton.setVisible(false);
				SpeedLabel.setVisible(false);
				GamePointLabel.setVisible(false);
				thread.start();;
			}
		});
		add(StartButton);
		//Changes the game point for winning the game
		// OPTIONS: 5,10,15
		GamePointButton = new JButton("Change Game Point");
		GamePointButton.setForeground(new Color(255, 255, 255));
		GamePointButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GamePointButton.setBackground(Color.BLACK);
		GamePointButton.setBounds(287, 139, 192, 43);
		GamePointButton.setFocusable(false);
		GamePointButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				if(GamePoint < 15)GamePoint += 5;
				else GamePoint = 5;
				GamePointLabel.setText(Integer.toString(GamePoint));
			}
		});
		add(GamePointButton);
		
		GamePointLabel = new JLabel("5");
		GamePointLabel.setForeground(Color.WHITE);
		GamePointLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GamePointLabel.setBounds(521, 140, 160, 48);
		add(GamePointLabel);
		//Label to display winner at the end of the game
		endLabel = new JLabel();
		endLabel.setHorizontalAlignment(SwingConstants.CENTER);
		endLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		endLabel.setForeground(new Color(255, 255, 255));
		endLabel.setBounds(198, 77, 599, 229);
		add(endLabel);
		
		thread = new Thread(this);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	//Draws the current location of balls and paddles
	public void draw(Graphics g) {
		super.paint(g);//paint background
		ball.draw(g);
		paddle1.draw(g);
		paddle2.draw(g);
	}
	//updates the ball and paddle's movements
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	//check to see if an collision has occured
	public void Collision() {
		//Stops paddle at screen edge
		if(paddle1.y<=0)paddle1.y=0;
		if(paddle2.y<=0)paddle2.y=0;
		if(paddle1.y>=455)paddle1.y=455;
		if(paddle2.y>=455)paddle2.y=455;
		//Makes ball bounce on top and bottom window
		if(ball.y<=0||ball.y>=535)ball.setYDir(-ball.yVel);
		//Makes ball reset to center after going past the paddle and increments score
		if(ball.x<=0||ball.x>=980) {
			if(ball.x<=0)P2ScoreLabel.setText(Integer.toString(++P2Score));
			else P1ScoreLabel.setText(Integer.toString(++P1Score));
			ball.reset(500, 262);
			
		}
		// Makes ball bounce off paddle
		if(ball.intersects(paddle1)) {
			ball.setXDir(-ball.xVel);
		}
		if(ball.intersects(paddle2)) {
			ball.setXDir(-ball.xVel);
		}
		
	}

	//game loop 
	public void run() {
		long Ltime = System.nanoTime();
		double amountOfT = 60.0;
		double ns = 1000000000/amountOfT;
		double delta = 0;
		//keeps updating movements, checks collision and repaints
		while(true&& P1Score<GamePoint&&P2Score<GamePoint) {
			long now = System.nanoTime();
			delta+=(now-Ltime)/ns;
			Ltime = now;
			if(delta>=1) {
				move();
				Collision();
				repaint();
				delta--;

			}
		}
		//Game ending text
		if(P1Score>P2Score)endLabel.setText("<html>PLAYER 1 HAS WON!<BR> THANKS FOR PLAYING!</html>");
		else endLabel.setText("<html>PLAYER 2 HAS WON!<BR> THANKS FOR PLAYING!</html>");
		
		
	}
	//keylisteners
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}

}
