package game;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -240840600533728354L;
	
	public JFrame frame;
	
	public Window(int width, int height, String title, Env env){
		frame = new JFrame(title);
		
		int idk = 27;
		frame.setPreferredSize(new Dimension(width, height + idk));
		frame.setMinimumSize(new Dimension(0, 0));
		frame.setMaximumSize(new Dimension(width, height + idk));
		frame.setSize(new Dimension(width, height + idk));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		//frame.setLocationRelativeTo(null);
		frame.add(env);
		frame.setVisible(true);
		
		// sometimes the environment needs to be started from here
		// env.start();
	}
}
