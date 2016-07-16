package window.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField playerLogTextField;
	private JButton playerLogSendButton;
	
	public PlayerPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane playerSplitPane = new JSplitPane();
		add(playerSplitPane, BorderLayout.CENTER);
		
		JPanel playerLogPanel = new JPanel();
		playerSplitPane.setLeftComponent(playerLogPanel);
		playerLogPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane playerLogScrollPane = new JScrollPane();
		playerLogScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		playerLogPanel.add(playerLogScrollPane);
		
		JTextArea playerLogTextArea = new JTextArea();
		playerLogTextArea.setText("LOG\r\n\r\nRecord - All Messages sent to sever from Client\r\nIncluding Chat\r\nPage Redirects\r\n");
		playerLogScrollPane.setViewportView(playerLogTextArea);
		
		JPanel playerLogCommandPanel = new JPanel();
		playerLogCommandPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		playerLogPanel.add(playerLogCommandPanel, BorderLayout.SOUTH);
		playerLogCommandPanel.setLayout(new BorderLayout(3, 0));
		
		playerLogTextField = new JTextField();
		playerLogCommandPanel.add(playerLogTextField);
		playerLogTextField.setColumns(10);
		
		playerLogSendButton = new JButton("Send");
		playerLogCommandPanel.add(playerLogSendButton, BorderLayout.EAST);
		
		JPanel playerLogTopPanel = new JPanel();
		playerLogPanel.add(playerLogTopPanel, BorderLayout.NORTH);
		playerLogTopPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		playerLogTopPanel.setLayout(new BoxLayout(playerLogTopPanel, BoxLayout.X_AXIS));
		
		JLabel lblPlayerLog = new JLabel("Player Log");
		playerLogTopPanel.add(lblPlayerLog);
		lblPlayerLog.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		playerLogTopPanel.add(horizontalGlue_2);
		
		JButton btnKick = new JButton("Kick");
		btnKick.setForeground(new Color(175, 0, 0));
		btnKick.setFont(new Font("Tahoma", Font.BOLD, 14));
		playerLogTopPanel.add(btnKick);
		
		JButton btnMute = new JButton("Mute");
		btnMute.setForeground(new Color(200, 150, 0));
		btnMute.setFont(new Font("Tahoma", Font.BOLD, 14));
		playerLogTopPanel.add(btnMute);
		
		JScrollPane playerList = new JScrollPane();
		playerSplitPane.setRightComponent(playerList);
		playerList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		playerList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel playerListViewport = new JPanel();
		playerList.setViewportView(playerListViewport);
		playerListViewport.setLayout(new BoxLayout(playerListViewport, BoxLayout.Y_AXIS));
	}
}
