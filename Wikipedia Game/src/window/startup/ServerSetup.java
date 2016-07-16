package window.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import game.GameServer;

public class ServerSetup extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField passwordTextField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerSetup frame = new ServerSetup(null);
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerSetup(GameServer server) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		tabPanel.addTab("Round", null, new RoundPanel(server), null);
		tabPanel.addTab("Players", null, new PlayerPanel(), null);
		
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
	
		pack();
		setLocationRelativeTo(null);
	}
}
