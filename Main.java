/*Shaheer Khan
 * On My Honor as a Student, I have not given nor received aid on this lab
 */

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        Game game = new Game();
        frame.setTitle("wynson");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}