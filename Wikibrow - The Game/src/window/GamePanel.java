package window;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import window.browser.WikipediaBrowser;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private StatsPanel statsPanel;
	private EscapeButtonsPanel escapeButtons;
	private HistoryPanel historyPanel;
	private TargetPanel targetPanel;
	private WikipediaBrowser browser;
	private JPanel centerPanel;
	private JPanel panel;
	private JSeparator separator;
	private JSeparator separator_1;
	
	public GamePanel() {
		super();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(250, 10));
		add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		historyPanel = new HistoryPanel();
		leftPanel.add(historyPanel, BorderLayout.CENTER);
		
		targetPanel = new TargetPanel();
		leftPanel.add(targetPanel, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, historyPanel, targetPanel);
		splitPane.setResizeWeight(0.75);
		leftPanel.add(splitPane, BorderLayout.CENTER);
		
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		browser = new WikipediaBrowser();
		browser.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(5, 5, 3, 5))));
		centerPanel.add(browser);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(1, 1, 1, 1), new EtchedBorder(EtchedBorder.LOWERED, null, null)), new EmptyBorder(3, 3, 3, 3)));
		centerPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		statsPanel = new StatsPanel();
		escapeButtons = new EscapeButtonsPanel();
		
		bottomPanel.add(statsPanel, BorderLayout.WEST);
		bottomPanel.add(escapeButtons, BorderLayout.EAST);
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 3, 0, 3));
		bottomPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator, BorderLayout.WEST);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_1, BorderLayout.EAST);
	}
}
