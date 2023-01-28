//Andy Qu
import java.awt.EventQueue;

import javax.swing.JFrame;


public class PongFrame extends JFrame {
	PongPanel GamePanel;
	
	PongFrame(){
		GamePanel = new PongPanel();
		this.add(GamePanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("PONG");
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
