
public class Computer {

	int bX, bY;
	boolean movingLeft, movingUp;
	int paddleY;
	int paddleX = Pong.b.paddleStartX2-Pong.b.paddleWidth;
	int paddleHeight = Pong.b.paddleHeight;
	Board b;
	
	public void update() {
		bX = Pong.b.ballX;
		bY = Pong.b.ballY;
		movingLeft = Pong.b.moveBallLeft;
		movingUp = Pong.b.moveBallUp;
		
		paddleY = Pong.b.paddleY2;
	}
	
	public void movePaddle() {
		update();
		// have the paddle follow the y position if the ball is in a given portion of the right half
		if (!movingLeft && bX >= 3*Pong.b.width/5) {
			// if no overlap between paddle and ball, move paddle
			if (!(bY >= paddleY-paddleHeight/2 && bY <= paddleY+paddleHeight/2)) {
				if (bY > paddleY) {
					Pong.b.moveComputerPaddle(-1);
				} else {
					Pong.b.moveComputerPaddle(1);
				}
			}
		}
	}
	
}
