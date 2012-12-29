import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Pong {

	static int delay = 20; // the delay between moving the ball/paddles
	static JFrame f;
	static Board b;
	static int[] score = new int[2];
	static int numHumans;
	static PlayerControl pc = new PlayerControl();
	static Pong p;
	static Computer c;
	
	public Pong()
	{
		b = new Board();
        f = new JFrame("PONG");
		f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(b);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new FileReader("numPlayers"));
		numHumans = Integer.parseInt(br.readLine());
		if (numHumans >2 || numHumans < 1) {
			System.out.println("You can only have 1 or 2 players. Please fix the 'numPlayers' file");
			System.exit(0);
		}
		System.out.println("numPlayers: " + numHumans);
		p = new Pong();
		if (numHumans == 1) {
			c = new Computer();
		}
		p.run();
		while (true) {
			if (numHumans == 1) {
				c.movePaddle();
			}
			f.repaint();
			if (isGameOver()) {
				b.gameOver = true;
				f.repaint();
				break;
			}
			Thread.sleep(delay);
			if (b.numHits > 16 && b.numHits < 26) {
				delay = 20;
			} else if (b.numHits > 26) {
				delay = 10;
			}
		}
		
	}
	
	public static boolean isGameOver() {
		return (score[0] == 10 || score[1] == 10);
	}
	
	public void run() {

		b.addKeyListener(new KeyListener() {
	
	         @Override
	         public void keyTyped(KeyEvent e) {}
	
	         @Override
	         public void keyReleased(KeyEvent e) {}
	
	         @Override
	         public void keyPressed(KeyEvent e) {
		             switch (e.getKeyCode()) {
		             	case 37: // left arrow
		             		if (numHumans == 1) {
		             			b.movePaddle1(+1);
		             		} else {
		             			b.movePaddle2(+1);
		             		}
		             		break;
		             	case 38: // up arrow
		             		if (numHumans == 1) {
		             			b.movePaddle1(+1);
		             		} else {
		             			b.movePaddle2(+1);
		             		}
							break;
						case 39: // right arrow
							if (numHumans == 1) {
		             			b.movePaddle1(-1);
		             		} else {
		             			b.movePaddle2(-1);
		             		}
							break;
						case 40: // down arrow
							if (numHumans == 1) {
		             			b.movePaddle1(-1);
		             		} else {
		             			b.movePaddle2(-1);
		             		}
							break;
						case 65:
							if (numHumans == 2) {
								b.movePaddle1(+1);
							}
							break;
						case 87:
							if (numHumans == 2) {
								b.movePaddle1(+1);
							}
							break;
						case 83:
							if (numHumans == 2) {
								b.movePaddle1(-1);
							}
							break;
						case 68:
							if (numHumans == 2) {
								b.movePaddle1(-1);
							}
							break;
					}
	         }
	     });
		
	    b.setFocusable(true);
	    b.requestFocusInWindow();
         
     }
}
