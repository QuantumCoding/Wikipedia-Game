package window;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import window.browser.WikipediaURLStreamHandlerFactory;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GamePanel gamePanel;
	
	public static void main(String[] args) {
		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		new Window();
	}
	
	public Window() {
		setSize(1080, 720);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
		
		gamePanel = new GamePanel();
		
		add(gamePanel);
	}
}
