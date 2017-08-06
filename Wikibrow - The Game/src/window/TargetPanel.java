package window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TargetPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JList targetList;
	
	public TargetPanel() {
		super();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel targetNamePanel = new JPanel();
		targetNamePanel.setBorder(new EmptyBorder(3, 3, 3, 0));
		add(targetNamePanel, BorderLayout.NORTH);
		targetNamePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel targetNamePanel_1 = new JLabel("Target(s): ");
		targetNamePanel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		targetNamePanel_1.setHorizontalAlignment(SwingConstants.LEFT);
		targetNamePanel.add(targetNamePanel_1);
		
		JScrollPane targetScrollPane = new JScrollPane();
		targetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(targetScrollPane);
		
		targetList = new JList();
		targetScrollPane.setViewportView(targetList);
	}
}
