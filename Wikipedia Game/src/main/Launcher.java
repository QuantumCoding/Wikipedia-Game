package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import game.GameClient;
import game.GameServer;
import networking.server.Server;
import window.browser.WikipediaURLStreamHandlerFactory;
import window.testing.SamShowFrameFactory;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image image;
	private JTextField usernameTextFeild;
	private JTextField clientServerIpTextField;
	private JTextField clientPortTextFeild;
	private JPanel soulthPanel;
	private JTextField serverServerIpTextFeild;
	private JTextField serverServerPortTextField;
	private JSpinner capacitySpinner;
	
	public static void main(String[] args) {
		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Launcher() {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		
		setType(Type.UTILITY);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int) (512 * 1.5), (int) (256 * 1.5));
		contentPane = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Launcher.this.image, 0, 0, null);
			}
		};
		
		int padding = getWidth() / 100;
		contentPane.setBorder(new EmptyBorder(padding, padding, padding, padding));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		image = scale(new ImageIcon("res/SplashWiki.png"), getWidth(), getHeight()).getImage();
		int buttonHeight = getHeight() / 8;
		
		soulthPanel = new JPanel();
		contentPane.add(soulthPanel, BorderLayout.SOUTH);
		soulthPanel.setLayout(new CardLayout(0, 0));
		soulthPanel.setOpaque(false);
		soulthPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel buttonPanel = new JPanel();
		soulthPanel.add(buttonPanel, "button panel");
		buttonPanel.setPreferredSize(new Dimension(getWidth() - padding * 2, buttonHeight));
		buttonPanel.setLayout(new GridLayout(0, 3, (int) (buttonPanel.getPreferredSize().getWidth() / 9), 0));
		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(new Color(255, 255, 255, 0));
		
		JButton clientButton = new JButton("Client");
		clientButton.setFocusable(false);
		clientButton.setBackground(new Color(255, 255, 255, 0));
		clientButton.setFont(new Font("Serif", Font.BOLD, (int) (buttonHeight / 1.3)));
		clientButton.addActionListener((e) -> { ((CardLayout) soulthPanel.getLayout()).show(soulthPanel, "client panel"); });
		buttonPanel.add(clientButton);
		
		JButton closeButton = new JButton("Close");
		closeButton.setFocusable(false);
		closeButton.setForeground(new Color(204, 0, 0));
		closeButton.setBackground(new Color(255, 255, 255, 0));
		closeButton.setFont(new Font("Serif", Font.BOLD, (int) (buttonHeight / 1.3)));
		closeButton.addActionListener((e) -> { System.exit(0); });
		buttonPanel.add(closeButton);
		
		JButton serverButton = new JButton("Server");
		serverButton.setFocusable(false);
		serverButton.setBackground(new Color(255, 255, 255, 0));
		serverButton.setFont(new Font("Serif", Font.BOLD, (int) (buttonHeight / 1.3)));
		serverButton.addActionListener((e) -> { ((CardLayout)soulthPanel.getLayout()).show(soulthPanel, "server panel"); });
		buttonPanel.add(serverButton);
		
		JPanel startClientPanel = new JPanel();
		startClientPanel.setBorder(new CompoundBorder(new LineBorder(new Color(255, 200, 0), 3), new EmptyBorder(2, 2, 2, 2)));
		soulthPanel.add(startClientPanel, "client panel");
		startClientPanel.setLayout(new BorderLayout(0, 0));
		startClientPanel.setOpaque(false);                          
		startClientPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel clientButtonPanel = new JPanel();
		startClientPanel.add(clientButtonPanel, BorderLayout.EAST);
		clientButtonPanel.setLayout(new GridLayout(2, 1, 0, 1));
		clientButtonPanel.setOpaque(false);                          
		clientButtonPanel.setBackground(new Color(255, 255, 255, 0));
		
		JButton clientStartButton = new JButton("Start");
		clientStartButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clientButtonPanel.add(clientStartButton);
		clientStartButton.setBackground(new Color(255, 255, 255, 0));
		clientStartButton.addActionListener((e) -> { startClient(); });
		
		JButton clientCancleButton = new JButton("Cancle");
		clientCancleButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clientCancleButton.addActionListener((e) -> { ((CardLayout)soulthPanel.getLayout()).show(soulthPanel, "button panel"); });
		clientButtonPanel.add(clientCancleButton);
		clientCancleButton.setBackground(new Color(255, 255, 255, 0));
		
		JPanel clientInfoPanel = new JPanel();
		startClientPanel.add(clientInfoPanel, BorderLayout.CENTER);
		clientInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		clientInfoPanel.setOpaque(false);
		clientInfoPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		clientInfoPanel.add(usernamePanel);
		usernamePanel.setLayout(new BorderLayout(5, 0));
		usernamePanel.setOpaque(false);
		usernamePanel.setBackground(new Color(255, 255, 255, 0));
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.ORANGE);
		usernamePanel.add(usernameLabel, BorderLayout.WEST);
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		usernameLabel.setBackground(new Color(255, 255, 255, 0));
		
		usernameTextFeild = new JTextField();
		usernameTextFeild.setText("localhost");
		usernameTextFeild.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernamePanel.add(usernameTextFeild, BorderLayout.CENTER);
		usernameTextFeild.setColumns(25);
		usernameTextFeild.addKeyListener(new KeyAdapter() {
			private void check() { clientStartButton.setEnabled(usernameTextFeild.getText().length() > 0); }
			
			public void keyPressed(KeyEvent e) { check(); }
			public void keyReleased(KeyEvent e) { check(); }
			public void keyTyped(KeyEvent e) { check(); }
		});
		
		clientStartButton.setEnabled(false);
		
		JPanel clientServerInfoPanel = new JPanel();
		clientServerInfoPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		clientInfoPanel.add(clientServerInfoPanel);
		clientServerInfoPanel.setLayout(new BoxLayout(clientServerInfoPanel, BoxLayout.X_AXIS));
		clientServerInfoPanel.setOpaque(false);
		clientServerInfoPanel.setBackground(new Color(255, 255, 255, 0));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		clientServerInfoPanel.add(horizontalGlue_2);
		
		JLabel clientServerIpLabel = new JLabel("Server IP: ");
		clientServerIpLabel.setForeground(Color.ORANGE);
		clientServerIpLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		clientServerInfoPanel.add(clientServerIpLabel);
		clientServerIpLabel.setBackground(new Color(255, 255, 255, 0));
		
		clientServerIpTextField = new JTextField();
		clientServerIpTextField.setText("localhost");
		clientServerIpTextField.setMaximumSize(new Dimension(500, 2147483647));
		clientServerIpTextField.setHorizontalAlignment(SwingConstants.CENTER);
		clientServerIpTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clientServerInfoPanel.add(clientServerIpTextField);
		clientServerIpTextField.setColumns(26);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		clientServerInfoPanel.add(horizontalGlue);
		horizontalGlue.setBackground(new Color(255, 255, 255, 0));
		
		JLabel clientPortLabel = new JLabel("Port: ");
		clientPortLabel.setForeground(Color.ORANGE);
		clientPortLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		clientServerInfoPanel.add(clientPortLabel);
		clientPortLabel.setBackground(new Color(255, 255, 255, 0));
		
		clientPortTextFeild = new JTextField();
		clientPortTextFeild.setHorizontalAlignment(SwingConstants.CENTER);
		clientPortTextFeild.setMaximumSize(new Dimension(100, 2147483647));
		clientPortTextFeild.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clientServerInfoPanel.add(clientPortTextFeild);
		clientPortTextFeild.setColumns(5);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		clientServerInfoPanel.add(horizontalGlue_1);
		horizontalGlue_1.setBackground(new Color(255, 255, 255, 0));
		
		JPanel startServerPanel = new JPanel();
		startServerPanel.setOpaque(false);
		startServerPanel.setBorder(new CompoundBorder(new LineBorder(new Color(255, 200, 0), 3), new EmptyBorder(2, 2, 2, 2)));
		startServerPanel.setBackground(new Color(255, 255, 255, 0));
		soulthPanel.add(startServerPanel, "server panel");
		startServerPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel serverButtonPabel = new JPanel();
		serverButtonPabel.setOpaque(false);
		serverButtonPabel.setBackground(new Color(255, 255, 255, 0));
		startServerPanel.add(serverButtonPabel, BorderLayout.EAST);
		serverButtonPabel.setLayout(new GridLayout(2, 1, 0, 1));
		
		JButton serverStartButton = new JButton("Start");
		serverStartButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		serverStartButton.setBackground(new Color(255, 255, 255, 0));
		serverStartButton.addActionListener((e) -> { startServer(); });
		serverButtonPabel.add(serverStartButton);
		
		JButton serverCancleButton = new JButton("Cancle");
		serverCancleButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		serverCancleButton.setBackground(new Color(255, 255, 255, 0));
		serverCancleButton.addActionListener((e) -> { ((CardLayout)soulthPanel.getLayout()).show(soulthPanel, "button panel"); });
		serverButtonPabel.add(serverCancleButton);
		
		JPanel serverInfoPanel = new JPanel();
		serverInfoPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		serverInfoPanel.setBackground(new Color(255, 255, 255, 0));
		startServerPanel.add(serverInfoPanel, BorderLayout.CENTER);
		serverInfoPanel.setLayout(new GridLayout(2, 1, 0, 5));
		
		JPanel serverServerInfoPanel = new JPanel();
		serverInfoPanel.add(serverServerInfoPanel);
		serverServerInfoPanel.setBackground(new Color(255, 255, 255, 0));
		serverServerInfoPanel.setLayout(new BoxLayout(serverServerInfoPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue_4 = Box.createHorizontalGlue();
		serverServerInfoPanel.add(horizontalGlue_4);
		
		JLabel serverServerIpLabel = new JLabel("Server IP: ");
		serverServerIpLabel.setForeground(Color.ORANGE);
		serverServerIpLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		serverServerIpLabel.setBackground(new Color(255, 255, 255, 0));
		serverServerInfoPanel.add(serverServerIpLabel);
		
		serverServerIpTextFeild = new JTextField();
		serverServerIpTextFeild.setText("localhost");
		serverServerIpTextFeild.setMaximumSize(new Dimension(500, 2147483647));
		serverServerIpTextFeild.setHorizontalAlignment(SwingConstants.CENTER);
		serverServerIpTextFeild.setFont(new Font("Tahoma", Font.PLAIN, 16));
		serverServerIpTextFeild.setColumns(26);
		serverServerInfoPanel.add(serverServerIpTextFeild);
		
		Component horizontalGlue_5 = Box.createHorizontalGlue();
		serverServerInfoPanel.add(horizontalGlue_5);
		
		JPanel severPortPanel = new JPanel();
		severPortPanel.setBackground(new Color(255, 255, 255, 0));
		serverInfoPanel.add(severPortPanel);
		severPortPanel.setLayout(new BoxLayout(severPortPanel, BoxLayout.X_AXIS));
		
		JLabel serverServerPortLabel = new JLabel("Port: ");
		severPortPanel.add(serverServerPortLabel);
		serverServerPortLabel.setForeground(Color.ORANGE);
		serverServerPortLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		serverServerPortLabel.setBackground(new Color(255, 255, 255, 0));
		
		serverServerPortTextField = new JTextField();
		severPortPanel.add(serverServerPortTextField);
		serverServerPortTextField.setHorizontalAlignment(SwingConstants.CENTER);
		serverServerPortTextField.setMaximumSize(new Dimension(100, 2147483647));
		serverServerPortTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		serverServerPortTextField.setColumns(5);
		
		Component horizontalGlue_6 = Box.createHorizontalGlue();
		severPortPanel.add(horizontalGlue_6);
		
		JLabel capacityLabel = new JLabel("Capacity: ");
		capacityLabel.setForeground(Color.ORANGE);
		capacityLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		capacityLabel.setBackground(new Color(255, 255, 255, 0));
		severPortPanel.add(capacityLabel);
		
		capacitySpinner = new JSpinner();
		capacitySpinner.setMaximumSize(new Dimension(20, 32767));
		capacitySpinner.setMinimumSize(new Dimension(20, 20));
		capacitySpinner.setPreferredSize(new Dimension(100, 20));
		capacitySpinner.setFont(new Font("Tahoma", Font.PLAIN, 16));
		capacitySpinner.setModel(new SpinnerNumberModel(5, 2, 65536, 1));
		severPortPanel.add(capacitySpinner);
		
		setLocationRelativeTo(null);
		
		repaint();
		setVisible(true);
	}

	private ImageIcon scale(ImageIcon icon, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = image.getGraphics();
		g.drawImage(icon.getImage(), 0, 0, width, height, null);
		g.dispose();
		
		return new ImageIcon(image);
	}
	
	public void startServer() {
		try {
			new GameServer(
					getIP(serverServerIpTextFeild.getText()), 
					serverServerPortTextField.getText().length() < 1 ? Server.DEFAULT_PORT : Integer.parseInt(serverServerPortTextField.getText()),
					(Integer) capacitySpinner.getValue());
			
			close();	
		} catch(UnknownHostException u) {
			JOptionPane.showMessageDialog(this, "Invalid Server IP", "Server IP Error", JOptionPane.ERROR_MESSAGE);
			
		} catch(NumberFormatException n) {
			JOptionPane.showMessageDialog(this, "<HTML> <CENTER> <B> Invalid Port Number </B> <BR>"
						+ "Port Numbers my only contain 1 - 9 </CENTER> </HTML>", 
						"Port Format Error", JOptionPane.ERROR_MESSAGE);
			
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this, e.toString(), "Failed to Start Sever", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void startClient() { 
		try {
			new GameClient(
					usernameTextFeild.getText(), 
					getIP(clientServerIpTextField.getText()), 
					clientPortTextFeild.getText().length() < 1 ? Server.DEFAULT_PORT : Integer.parseInt(clientPortTextFeild.getText()),
					new SamShowFrameFactory());
			
			close();	
		} catch(UnknownHostException u) {
			JOptionPane.showMessageDialog(this, "Invalid Server IP", "Server IP Error", JOptionPane.ERROR_MESSAGE);
			
		} catch(NumberFormatException n) {
			JOptionPane.showMessageDialog(this, "<HTML> <CENTER> <B> Invalid Port Number </B> <BR>"
						+ "Port Numbers my only contain 1 - 9 </CENTER> </HTML>", 
						"Port Format Error", JOptionPane.ERROR_MESSAGE);
			
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this, e.toString(), "Failed to Connect to Sever", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void close() {
		setVisible(false);
		dispose();
	}
	
	private InetAddress getIP(String ip) throws UnknownHostException {
		return InetAddress.getByName(ip);
	}
}
