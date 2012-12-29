import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class Board extends JPanel {
	
	int width = 1200;
	int height = 600;
	Color color = new Color(27,242,41);
	int midLine = width/2;
	int midLineThickness = 2;
	int paddleHeight = 80;
	int paddleWidth = 10;
	int paddleStartY = height/2;
	int paddleStartX1 = 20;
	int paddleStartX2 = width - paddleStartX1;
	int ballRadius = 12;
	int ballX = width/2;
	int ballY = height/2;
	boolean moveBallUp;
	boolean moveBallLeft;
	int paddleY1;
	int paddleY2;
	int paddleShift = paddleHeight/2;
	int computerPaddleShift = 7;
	int barHeight = 40; // height of the bar with the title
	int lastToScore = 1;
	int ballShift = 5;
	int numHits;
	Random r = new Random();
	int yShift = 1;
	int xShift = 2;
	boolean gameOver = false;

	public Board() {
		setBorder(BorderFactory.createLineBorder(Color.blue));
		setPreferredSize(new Dimension(width, height));
		
		paddleY1 = paddleStartY;
		paddleY2 = paddleStartY;
	}
	
	public void setBallPos(int x, int y) {
		ballX = x;
		ballY = y;
	}
	
	public void restartBall() {
		ballY = height/4 + r.nextInt(height/2);
		ballX = width/2;
		moveBallUp = (r.nextInt()%2 == 0);
		numHits = 0;
		yShift = 1;
		paddleY1 = paddleStartY;
		paddleY2 = paddleStartY;
		if (lastToScore == 1) {
			moveBallLeft = true;
		} else {
			moveBallLeft = false;
		}
	}
	
	public boolean ballOutOfBounds() {
		if (ballX <= 0) {
			lastToScore = 1;
			Pong.score[1] += 1;
		}
		if (ballX >= width) {
			lastToScore = 2;
			Pong.score[0] += 1;
		}
		return (ballX <= 0 || ballX >= width);
	}
	
	public void moveComputerPaddle(int y) {
		if (y > 0) {
			paddleY2 -= computerPaddleShift;
		} else {
			paddleY2 += computerPaddleShift;
		}
		if (paddleY2 <= barHeight) paddleY2 = barHeight;
		if (paddleY2+paddleHeight >= height+barHeight) paddleY2 = height-paddleHeight+barHeight;
	}
	
	// -1 = down, +1 = up
	public void movePaddle1(int y) {
		if (y > 0) { // move up
			paddleY1 -= paddleShift;
		} else { // move down
			paddleY1 += paddleShift;
		}
		if (paddleY1 <= barHeight) paddleY1 = barHeight;
		if (paddleY1+paddleHeight >= height+barHeight) paddleY1 = height-paddleHeight+barHeight;
	}
	
	// -1 = down, +1 = up
		public void movePaddle2(int y) {
			if (y > 0) { // move up
				paddleY2 -= paddleShift;
			} else { // move down
				paddleY2 += paddleShift;
			}
			if (paddleY2 <= barHeight) paddleY2 = barHeight;
			if (paddleY2+paddleHeight >= height+barHeight) paddleY2 = height-paddleHeight+barHeight;
		}
	
	// returns true if the ball "hits" the walls/paddles
	public int ballHitWall() {
		
		// ceiling
		if (ballY-ballRadius <= 0) {
			return -1;
		}
		
		// floor
		if ((ballY+ballRadius) >= height) {
			return 1;
		}
		return 0;
	}

	// return 
	//   +1 if ball should move right/up
	//   -1 if ball should move right/down
	//   +2 if left/up
	//   -2 if left/down
	public int ballHitPaddle() {
		// left paddle
		if (ballX <= paddleStartX1+paddleWidth+ballRadius/2 && ballX >= (paddleStartX1)  && (ballY <= paddleY1+paddleHeight/2 && ballY >= paddleY1-paddleHeight/2)) {
			numHits++;
			if (ballY+ballRadius <= paddleY1) {
				if (ballY+ballRadius <= paddleY1-paddleHeight/2+paddleHeight/4) {
					yShift = 2;
				} else {
					yShift = 1;
				}
				return +1;
			} else {
				if (ballY+ballRadius >= paddleY1+paddleHeight/2-paddleHeight/4) {
					yShift = 2;
				} else {
					yShift = 1;
				}
				return -1;
			}
		}
		
		// right paddle
		if (ballX >= (paddleStartX2-paddleWidth-ballRadius/2) && ballX <= (paddleStartX2) && (ballY <= paddleY2+paddleHeight/2 && ballY >= paddleY2-paddleHeight/2)) {
			numHits++;
			if (ballY+ballRadius <= paddleY2) {
				if (ballY+ballRadius <= paddleY2-paddleHeight/2+paddleHeight/4) {
					yShift = 2;
				} else {
					yShift = 1;
				}
				return +2;
			} else {
				if (ballY+ballRadius >= paddleY2+paddleHeight/2-paddleHeight/4) {
					yShift = 2;
				} else {
					yShift = 1;
				}
				return -2;
			}
		}
		
		return 0;
	}
	
	// returns -1 if the ball should move down, 0 is no hit, and +1 if ball should move up
	public void getBallY() {
		int ballYOld = ballY;
		int b = ballHitWall();
		if (b == -1) { // hits off ceiling -> move ball down
			ballY += yShift*ballShift; 
		} else if (b == 1) {
			ballY -= yShift*ballShift;
		} else {
			if (moveBallUp) {
				ballY -= yShift*ballShift;
			} else {
				ballY += yShift*ballShift;
			}
		}
		if (ballY > ballYOld) {
			moveBallUp = false;
		} else {
			moveBallUp = true;
		}
	}
	
	// returns -1 if ball should move left, +1 if right
	public void getBallX() {
		int ballXOld = ballX;
		int b = ballHitPaddle();
		
		if (b == -1) { // right/down
			ballX += ballShift+xShift;
			ballY += yShift*ballShift;
			moveBallUp = false;
		} else if (b == 1) { // right/up
			ballX += ballShift+xShift;
			ballY -= yShift*ballShift;
			moveBallUp = true;
		} else if (b == -2) { // left/down
			ballX -= ballShift-xShift;
			ballY += yShift*ballShift;
			moveBallUp = false;
		} else if (b == 2) { // left/up
			ballX -= ballShift-xShift;
			ballY -= yShift*ballShift;
			moveBallUp = true;
		} else {
			getBallY();
			if (moveBallLeft) {
				ballX -= (ballShift+xShift);
			} else {
				ballX += ballShift+xShift;
			}
		}

		if (ballX < ballXOld) {
			moveBallLeft = true;
		} else {
			moveBallLeft = false;
		}
	}
	
	public void paint(Graphics g) { 
		g.setColor(Color.black); // background color
		g.fillRect(0, 0, width, height);
		
		g.setColor(color);
		
		if (gameOver) {
			int fontSize = 100;
		    g.setFont(new Font("Impact", Font.PLAIN, fontSize));
		    
			String gOver = "   GAME OVER";
			g.drawString(gOver, width/4, height/4);
			if (Pong.numHumans == 1) {
				if (Pong.score[0] == 10) {
					g.drawString("      YOU WIN", width/4, 3*height/4);
				} else {
					g.drawString("      YOU LOSE", width/4, 3*height/4);
				}
			} else {
				if (Pong.score[0] == 10) {
					g.drawString("  PLAYER 1 WINS", width/4, 3*height/4);
				} else {
					g.drawString("  PLAYER 2 WINS", width/4, 3*height/4);
				}
			}
		} else {
			int fontSize = 45;
		    g.setFont(new Font("Impact", Font.PLAIN, fontSize));
		    
			// show score
			String p1Score = Integer.toString(Pong.score[0]);
			String p2Score = Integer.toString(Pong.score[1]);
			g.drawString(p1Score, width/4, 50);
			g.drawString(p2Score, 3*width/4, 50);
			
			// draw middle line
			g.fillRect(midLine-midLineThickness/2, 0, midLineThickness, height);
			
			// draw paddle for player 1
			g.fillRect(paddleStartX1, paddleY1-paddleHeight/2, paddleWidth, paddleHeight);
			
			// draw paddle for player 2
			g.fillRect(paddleStartX2-paddleWidth, paddleY2-paddleHeight/2, paddleWidth, paddleHeight);
			
			// draw the ball
			if (ballOutOfBounds()) {
				restartBall();
			} else {
				getBallX();
			}
			g.fillOval(ballX-ballRadius/2, ballY-ballRadius/2, ballRadius, ballRadius);
		}
	}
	
}
