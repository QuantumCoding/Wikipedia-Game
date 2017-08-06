package window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EscapeButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton pauseButton;
	private JButton quitButton;
	private JButton settingsButton;
	
	public EscapeButtonsPanel() {
		super();
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonGridPanel = new JPanel();
		add(buttonGridPanel, BorderLayout.CENTER);
		buttonGridPanel.setLayout(new GridLayout(1, 1, 0, 0));
		
		settingsButton = new JButton("Settings");
		buttonGridPanel.add(settingsButton);
		settingsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		quitButton = new JButton("Quit");
		buttonGridPanel.add(quitButton);
		quitButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		pauseButton = new JButton("PAUSE!");
		add(pauseButton, BorderLayout.NORTH);
		
		pauseButton.setFont(new Font("Tahoma", Font.PLAIN, 34));
	}
}
