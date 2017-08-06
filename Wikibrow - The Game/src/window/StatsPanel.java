package window;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class StatsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel timerLabel;
	private JLabel clicksLabel;
	
	public StatsPanel() {
		super();
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel timePanel = new JPanel();
		add(timePanel);
		timePanel.setBorder(null);
		timePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel timeNameLabel = new JLabel("Time: ");
		timeNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		timePanel.add(timeNameLabel, BorderLayout.WEST);
		
		JPanel clicksPanel = new JPanel();
		add(clicksPanel);
		clicksPanel.setBorder(null);
		clicksPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel clickNameLabel = new JLabel("Clicks: ");
		clickNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clickNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		clicksPanel.add(clickNameLabel, BorderLayout.WEST);
		
		timerLabel = new JLabel("00:00.000");
		timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		timePanel.add(timerLabel, BorderLayout.EAST);
		
		clicksLabel = new JLabel("0");
		clicksLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		clicksPanel.add(clicksLabel, BorderLayout.EAST);
	}
}
