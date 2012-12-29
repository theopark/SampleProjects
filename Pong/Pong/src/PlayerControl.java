import java.io.*;

public class PlayerControl {

	static BufferedReader br;
	
	public PlayerControl() {
		InputStreamReader istream = new InputStreamReader(System.in) ;
		br = new BufferedReader(istream);
	}
	
	// +1 = up, -1 = down, 0 = don't move
	public int getMove() throws IOException {
		String line = br.readLine();
		System.out.println(line);
		return 0;
	}
	
}
