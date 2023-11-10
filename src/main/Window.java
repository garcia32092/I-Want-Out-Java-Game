package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
	
	private static final long serialVersionUID = -240840600533728354L;
	
	public Window(int width, int height, String title, Game game) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.add(game);
//		frame.setMaximumSize(new Dimension(width, height));
//		frame.setMinimumSize(new Dimension(width, height));
		frame.pack();
		
		
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
	
}
