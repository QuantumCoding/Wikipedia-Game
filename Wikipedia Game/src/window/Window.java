package window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import java.awt.Dimension;
import javax.swing.UIManager;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel stats = new JPanel();
		panel_6.add(stats);
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		stats.add(panel_2);
		
		JLabel lblStats = new JLabel("Stats:");
		panel_2.add(lblStats);
		lblStats.setFont(new Font("Verdana", Font.BOLD, 36));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		stats.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_5.add(panel_1);
		panel_1.setBorder(null);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_1.add(lblTime);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(100);
		panel_1.add(horizontalStrut_1);
		
		JLabel label = new JLabel("00:00.000");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_1.add(label);
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4);
		panel_4.setBorder(null);
		
		JLabel lblNumberOfClicks = new JLabel("Number of clicks:");
		lblNumberOfClicks.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_4.add(lblNumberOfClicks);
		
		Component horizontalStrut = Box.createHorizontalStrut(70);
		panel_4.add(horizontalStrut);
		
		JLabel label_1 = new JLabel("0");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel_4.add(label_1);
		
		JPanel target = new JPanel();
		target.setBorder(new CompoundBorder(new EmptyBorder(6, 0, 6, 0), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		target.setPreferredSize(new Dimension(1, 168));
		panel_6.add(target);
		target.setLayout(new BoxLayout(target, BoxLayout.Y_AXIS));
		
		JPanel panel_10 = new JPanel();
		target.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblTargets = new JLabel("Target(s): ");
		lblTargets.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTargets.setHorizontalAlignment(SwingConstants.LEFT);
		panel_10.add(lblTargets);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		target.add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setMinimumSize(new Dimension(23, 23));
		scrollPane_1.setViewportView(list_1);
		
		JPanel players = new JPanel();
		players.setPreferredSize(new Dimension(175, 270));
		players.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(1, 1, 1, 1), new EtchedBorder(EtchedBorder.LOWERED, null, null)), new EmptyBorder(3, 3, 3, 3)));
		panel_6.add(players);
		players.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		players.add(panel_8, BorderLayout.NORTH);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Players: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		panel_8.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setRequestFocusEnabled(false);
		scrollPane.setMinimumSize(new Dimension(25, 25));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		players.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		Component verticalGlue = Box.createVerticalGlue();
		players.add(verticalGlue, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_6.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new CompoundBorder(new EmptyBorder(2, 0, 2, 0), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("PAUSE!");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 34));
		panel_7.add(btnNewButton);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new CompoundBorder(new EmptyBorder(2, 0, 0, 2), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_9.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JButton btnQuit = new JButton("Quit");
		panel_11.add(btnQuit);
		btnQuit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new CompoundBorder(new EmptyBorder(2, 2, 0, 0), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_9.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_1 = new JButton("Settings");
		panel_12.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		getContentPane().add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_13.add(panel_15, BorderLayout.NORTH);
		panel_15.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblHistory = new JLabel("History:");
		lblHistory.setVerticalAlignment(SwingConstants.TOP);
		panel_15.add(lblHistory);
		lblHistory.setFont(new Font("Tahoma", Font.BOLD, 28));
		
		JPanel panel_14 = new JPanel();
		panel_13.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_14.add(scrollPane_2);
		
		JList list_2 = new JList();
		scrollPane_2.setViewportView(list_2);
		
	}
}
