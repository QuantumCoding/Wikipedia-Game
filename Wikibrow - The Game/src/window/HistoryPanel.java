package window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

public class HistoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JList historyList;
	
	public HistoryPanel() {
		super();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel historyNamePanel = new JPanel();
		add(historyNamePanel, BorderLayout.NORTH);
		historyNamePanel.setBorder(new EmptyBorder(3, 3, 3, 0));
		historyNamePanel.setLayout(new BoxLayout(historyNamePanel, BoxLayout.X_AXIS));
		
		JLabel historyNameLabel = new JLabel("History:");
		historyNameLabel.setVerticalAlignment(SwingConstants.TOP);
		historyNamePanel.add(historyNameLabel);
		historyNameLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		
		JPanel historyListPanel = new JPanel();
		add(historyListPanel);
		historyListPanel.setLayout(new BoxLayout(historyListPanel, BoxLayout.Y_AXIS));
		
		JScrollPane historyScrollPane = new JScrollPane();
		historyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		historyListPanel.add(historyScrollPane);
		
		historyList = new JList();
		historyScrollPane.setViewportView(historyList);
	}
}
