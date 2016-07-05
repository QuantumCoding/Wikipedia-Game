package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Color DONE_COLOR = new Color(0, 225, 0);
	private static final Color PLAYING_COLOR = new Color(255, 225, 0);
	private static final Color SPECTATING_COLOR = new Color(255, 200, 0);
	private static final Color QUIT_COLOR = new Color(225, 0, 0);
	
	private static final String DONE_STRING = "Done";
	private static final String PLAYING_STRING = "Playing";
	private static final String SPECTATING_STRING = "Spectating";
	private static final String QUIT_STRING = "Quit";
	
	private JLabel timeTextField;
	private JLabel clicksTextField;
	private JButton spectateButton;
	private JLabel winCountLabel;
	private JProgressBar progressBar;
	private JLabel statusLabel;

	public PlayerPanel(String username) {
		super.setName(username);
		setLayout(new BorderLayout(0, 0));
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new EmptyBorder(3, 3, 3, 0));
		add(dataPanel);
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(2, 0, 2, 3));
		dataPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 128, 0));
		progressBar.setStringPainted(true);
		progressBar.setValue(65);
		panel.add(progressBar);
		progressBar.setOrientation(SwingConstants.VERTICAL);
		
		JPanel dataSubPanel = new JPanel();
		dataSubPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(3, 3, 3, 3)));
		dataPanel.add(dataSubPanel);
		dataSubPanel.setLayout(new BoxLayout(dataSubPanel, BoxLayout.Y_AXIS));
		
		JPanel timePanel = new JPanel();
		dataSubPanel.add(timePanel);
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		timePanel.add(lblTime);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		timePanel.add(horizontalGlue_2);
		
		timeTextField = new JLabel();
		timeTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		timeTextField.setText("00:00.000");
		timeTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		timePanel.add(timeTextField);
		
		JPanel clicksPanel = new JPanel();
		dataSubPanel.add(clicksPanel);
		clicksPanel.setLayout(new BoxLayout(clicksPanel, BoxLayout.X_AXIS));
		
		JLabel lblClicks = new JLabel("Clicks:");
		lblClicks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clicksPanel.add(lblClicks);
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		clicksPanel.add(horizontalGlue_3);
		
		clicksTextField = new JLabel();
		clicksTextField.setHorizontalAlignment(SwingConstants.TRAILING);
		clicksTextField.setText("0");
		clicksTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		clicksPanel.add(clicksTextField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), null));
		buttonPanel.setMaximumSize(new Dimension(30, 32767));
		dataPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 2));
		
		spectateButton = new JButton("Spectate");
		spectateButton.setEnabled(false);
		spectateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(spectateButton);
		
		JPanel winsPanel = new JPanel();
		winsPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(0, 2, 0, 2)));
		buttonPanel.add(winsPanel);
		winsPanel.setLayout(new BoxLayout(winsPanel, BoxLayout.X_AXIS));
		
		JLabel winLabel = new JLabel("Wins: ");
		winsPanel.add(winLabel);
		winLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		winsPanel.add(horizontalGlue_1);
		
		winCountLabel = new JLabel("0");
		winCountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		winsPanel.add(winCountLabel);
		
		JPanel labelPanel = new JPanel();
		add(labelPanel, BorderLayout.NORTH);
		labelPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel labelSubPanel = new JPanel();
		labelPanel.add(labelSubPanel);
		labelSubPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		labelSubPanel.setLayout(new BoxLayout(labelSubPanel, BoxLayout.X_AXIS));
		
		JLabel lblPlayerName = new JLabel("PLayer Name");
		lblPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelSubPanel.add(lblPlayerName);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		labelSubPanel.add(horizontalGlue);
		
		statusLabel = new JLabel("Status");
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelSubPanel.add(statusLabel);
		
		JSeparator separator = new JSeparator();
		labelPanel.add(separator, BorderLayout.SOUTH);
	}
	
	public void set(String time, int clickCount) {
		timeTextField.setText(time);
		clicksTextField.setText(clickCount + "");
	}
	
	public void win() { this.winCountLabel.setText((Integer.parseInt(winCountLabel.getText()) + 1) + ""); }
	public void setPercenatgeDone(int perc) { this.progressBar.setValue(perc); }
	
	public void reset() {
		timeTextField.setText("00:00.000");
		clicksTextField.setText("0");
		progressBar.setValue(0);
		spectateButton.setEnabled(false);
	}
	
	// 0 = Playing
	// 1 = Done
	// 2 = Spectating
	// 3 = Quit
	public void setState(int state) {
		if(state == 0 || state == 1) {
			timeTextField.setEnabled(true);
			clicksTextField.setEnabled(true);
			spectateButton.setEnabled(true);
			progressBar.setEnabled(true);
		} else {
			timeTextField.setEnabled(false);
			clicksTextField.setEnabled(false);
			spectateButton.setEnabled(false);
			progressBar.setEnabled(false);
		}
		
		switch(state) {
			case 0: 
				statusLabel.setForeground(PLAYING_COLOR);
				statusLabel.setText(PLAYING_STRING);
			break;
			
			case 1: 
				statusLabel.setForeground(DONE_COLOR);
				statusLabel.setText(DONE_STRING);
			break;
			
			case 2: 
				statusLabel.setForeground(SPECTATING_COLOR);
				statusLabel.setText(SPECTATING_STRING);
			break;
			
			case 3: 
				statusLabel.setForeground(QUIT_COLOR);
				statusLabel.setText(QUIT_STRING);
			break;
		}
	}
}
