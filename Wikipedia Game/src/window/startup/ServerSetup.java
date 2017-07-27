package window.startup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import game.GameServer;

public class ServerSetup extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
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
		tabPanel.addTab("Properties", null, new PropertiesPanel(server), null);
		
		
		JPanel logPanel = new JPanel();
		tabPanel.addTab("Log", null, logPanel, null);
	
		pack();
		setLocationRelativeTo(null);
	}
}
