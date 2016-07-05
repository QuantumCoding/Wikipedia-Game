package game.round;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PlayerReadyupPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final String READY_STRING = "Ready";
	private static final Color READY_COLOR = new Color(0, 204, 0);
	private static final Font READY_FONT = new Font("Tahoma", Font.BOLD | Font.ITALIC, 18);
	
	private static final String NOT_READY_STRING = "Not Ready";
	private static final Color NOT_READY_COLOR = new Color(204, 0, 0);
	private static final Font NOT_READY_FONT = new Font("Tahoma", Font.BOLD, 18);
	
	private JLabel usernameLabel;
	private JLabel readyLabel;

	public PlayerReadyupPanel(String username) {
		setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new LineBorder(new Color(0, 0, 0))));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setName(username);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
		innerPanel.setBackground(new Color(200, 200, 200, 200));
		add(innerPanel);
		
		usernameLabel = new JLabel(username);
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		innerPanel.add(usernameLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		innerPanel.add(horizontalGlue);
		
		readyLabel = new JLabel();
		innerPanel.add(readyLabel);
		notReady();
		
		setBackground(new Color(255, 255, 255, 0));
		usernameLabel.setBackground(new Color(255, 255, 255, 0));
		readyLabel.setBackground(new Color(255, 255, 255, 0));
		horizontalGlue.setBackground(new Color(255, 255, 255, 0));
	}

	public void setUsername(String name) { 
		usernameLabel.setText(name);
		setName(name);
	}
	
	public void ready(boolean ready) {
		if(ready) ready();
		else notReady();
	}
	
	public void ready() {
		setVisible(true);
		readyLabel.setText(READY_STRING);
		readyLabel.setForeground(READY_COLOR);
		readyLabel.setFont(READY_FONT);
	}
	
	public void notReady() {
		setVisible(true);
		readyLabel.setText(NOT_READY_STRING);
		readyLabel.setForeground(NOT_READY_COLOR);
		readyLabel.setFont(NOT_READY_FONT);
	}
	
	public void spectate() { setVisible(false); }
}
