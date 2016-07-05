package window.startup;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class ServerSetup extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField passwordTextField;
	private JTextField playerLogTextField;
	private JButton playerLogSendButton;
	private JTextField searchDestinationTextField;
	private JButton addDestingationButton;
	private JButton searchDesinationButton;
	private JButton moveDestUpButton;
	private JButton moveDestDownButton;
	private JButton removeDestButton;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerSetup frame = new ServerSetup();
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerSetup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 679, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		centerPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		buttonPanel.add(horizontalGlue_1);
		
		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		centerPanel.add(tabPanel, BorderLayout.CENTER);
		
		JPanel roundPanel = new JPanel();
		tabPanel.addTab("Round", null, roundPanel, null);
		roundPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		roundPanel.add(scrollPane, BorderLayout.WEST);
		
		JLabel lblRounds = new JLabel("Rounds");
		scrollPane.setColumnHeaderView(lblRounds);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JPanel panel_1 = new JPanel();
		roundPanel.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel destinationsPanel = new JPanel();
		panel_2.add(destinationsPanel);
		destinationsPanel.setLayout(new BoxLayout(destinationsPanel, BoxLayout.Y_AXIS));
		
		JPanel destinationLabelPanel = new JPanel();
		destinationsPanel.add(destinationLabelPanel);
		
		JLabel destinationOrderLabel = new JLabel("Destination Order");
		destinationLabelPanel.add(destinationOrderLabel);
		destinationOrderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		destinationOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane desinationsScrollPane = new JScrollPane();
		destinationsPanel.add(desinationsScrollPane);
		desinationsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JList desinationsList = new JList();
		desinationsList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		desinationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		desinationsScrollPane.setViewportView(desinationsList);
		
		JPanel desinationControlls = new JPanel();
		desinationControlls.setBorder(new EmptyBorder(3, 0, 0, 0));
		destinationsPanel.add(desinationControlls);
		desinationControlls.setLayout(new GridLayout(0, 3, 3, 0));
		
		moveDestUpButton = new JButton("Move Up");
		desinationControlls.add(moveDestUpButton);
		
		moveDestDownButton = new JButton("Move Down");
		desinationControlls.add(moveDestDownButton);
		
		removeDestButton = new JButton("Remove");
		desinationControlls.add(removeDestButton);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setPreferredSize(new Dimension(200, 1));
		searchPanel.setMinimumSize(new Dimension(1, 1));
		searchPanel.setMaximumSize(new Dimension(1, 1));
		panel_2.add(searchPanel, BorderLayout.EAST);
		searchPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel searchBarPanel = new JPanel();
		searchBarPanel.setMaximumSize(new Dimension(10, 32767));
		searchBarPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		searchPanel.add(searchBarPanel, BorderLayout.NORTH);
		searchBarPanel.setLayout(new BoxLayout(searchBarPanel, BoxLayout.X_AXIS));
		
		searchDestinationTextField = new JTextField();
		searchDestinationTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searchBarPanel.add(searchDestinationTextField);
		searchDestinationTextField.setColumns(10);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(3);
		searchBarPanel.add(horizontalStrut_1);
		
		searchDesinationButton = new JButton("Search");
		searchBarPanel.add(searchDesinationButton);
		
		JPanel resultsPanel = new JPanel();
		resultsPanel.setMaximumSize(new Dimension(10, 32767));
		resultsPanel.setBorder(new EmptyBorder(3, 0, 3, 0));
		searchPanel.add(resultsPanel, BorderLayout.CENTER);
		resultsPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane resultScrollPane = new JScrollPane();
		resultScrollPane.setMaximumSize(new Dimension(1, 32767));
		resultScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultsPanel.add(resultScrollPane);
		
		JList resultList = new JList();
		resultList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultScrollPane.setViewportView(resultList);
		
		JPanel addDestinationPanel = new JPanel();
		addDestinationPanel.setMaximumSize(new Dimension(10, 32767));
		addDestinationPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		searchPanel.add(addDestinationPanel, BorderLayout.SOUTH);
		addDestinationPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		addDestingationButton = new JButton("Add");
		addDestinationPanel.add(addDestingationButton);
		
		JPanel playerPanel = new JPanel();
		tabPanel.addTab("Players", null, playerPanel, null);
		playerPanel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane playerSplitPane = new JSplitPane();
		playerPanel.add(playerSplitPane, BorderLayout.CENTER);
		
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
		
		JPanel propertiesPanel = new JPanel();
		tabPanel.addTab("Properties", null, propertiesPanel, null);
		
		JPanel logPanel = new JPanel();
		tabPanel.addTab("Log", null, logPanel, null);
		
		JPanel panel_3 = new JPanel();
//		contentPane.add(panel_3, BorderLayout.CENTER);
		
		JPanel serverBindProperties = new JPanel();
		panel_3.add(serverBindProperties);
		serverBindProperties.setLayout(new BorderLayout(0, 0));
		
		JPanel topWrapPanel = new JPanel();
		serverBindProperties.add(topWrapPanel, BorderLayout.NORTH);
		topWrapPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel ipPanel = new JPanel();
		topWrapPanel.add(ipPanel);
		ipPanel.setLayout(new BoxLayout(ipPanel, BoxLayout.X_AXIS));
		
		JLabel ipLabel = new JLabel("IP Address:");
		ipLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		ipPanel.add(ipLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		ipPanel.add(horizontalStrut);
		
		ipTextField = new JTextField();
		ipTextField.setHorizontalAlignment(SwingConstants.CENTER);
		ipTextField.setMaximumSize(new Dimension(350, 2147483647));
		ipTextField.setText("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
		ipTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ipPanel.add(ipTextField);
		ipTextField.setColumns(26);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		ipPanel.add(horizontalGlue);
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.setForeground(Color.BLACK);
		btnStartServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		ipPanel.add(btnStartServer);
		
		JSeparator separator = new JSeparator();
		topWrapPanel.add(separator, BorderLayout.SOUTH);
		
		JPanel bottomWrapPanel = new JPanel();
		serverBindProperties.add(bottomWrapPanel, BorderLayout.SOUTH);
		bottomWrapPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel nonCritcalPanel = new JPanel();
		bottomWrapPanel.add(nonCritcalPanel);
		nonCritcalPanel.setLayout(new BoxLayout(nonCritcalPanel, BoxLayout.X_AXIS));
		
		JPanel portPanel = new JPanel();
		portPanel.setMaximumSize(new Dimension(50, 32767));
		portPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		nonCritcalPanel.add(portPanel);
		portPanel.setLayout(new BorderLayout(5, 0));
		
		JLabel portLabel = new JLabel("Port #");
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		portPanel.add(portLabel, BorderLayout.WEST);
		
		portTextField = new JTextField();
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);
		portTextField.setText("65536");
		portTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		portPanel.add(portTextField, BorderLayout.CENTER);
		portTextField.setColumns(5);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		nonCritcalPanel.add(passwordPanel);
		passwordPanel.setLayout(new BorderLayout(5, 0));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		
		passwordTextField = new JTextField();
		passwordPanel.add(passwordTextField, BorderLayout.CENTER);
		passwordTextField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		bottomWrapPanel.add(separator_1, BorderLayout.NORTH);
	}

}
