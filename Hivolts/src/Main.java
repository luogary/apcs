import javax.swing.JFrame;

/**
 * The Main method creates a frame for the game to be played in and then calls
 * the Hivolts class for initialization.
 * @author Amy Luo
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 800);
		final Hivolts hivolts = new Hivolts();
		frame.getContentPane().add(hivolts);
		frame.setVisible(true);
		hivolts.init();
		hivolts.start();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
}