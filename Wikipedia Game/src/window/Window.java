package window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import interfaces.IClientView;
import interfaces.IWebBrowser;
import window.browser.WikipediaBrowser;
import window.browser.WikipediaURLStreamHandlerFactory;

public class Window extends JFrame implements IClientView, ActionListener, MouseListener, KeyListener{
	private static final long serialVersionUID = 1L;

	WikipediaBrowser wikipediaBrowser = new WikipediaBrowser();
	
	JButton pauseButton = new JButton("PAUSE!");

	String name = "";
	
	public void makeBrowserPaused(){
		wikipediaBrowser.setVisible(false);
	}
	
	public void pauseRequestMenu(){
		
	}
	
	public static void main(String[] args) {
		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		new Window().setVisible(true);
	}
	
	public Window() {
		
		this.setSize(1080, 720);
		
		this.setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
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
		lblStats.setFont(new Font("Verdana", Font.BOLD, 24));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		stats.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_5.add(panel_1);
		panel_1.setBorder(null);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(lblTime);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(100);
		panel_1.add(horizontalStrut_1);
		
		JLabel label = new JLabel("00:00.000");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(label);
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4);
		panel_4.setBorder(null);
		
		JLabel lblNumberOfClicks = new JLabel("Number of clicks:");
		lblNumberOfClicks.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblNumberOfClicks);
		
		Component horizontalStrut = Box.createHorizontalStrut(76);
		panel_4.add(horizontalStrut);
		
		JLabel label_1 = new JLabel("0");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(label_1);
		
		JPanel target = new JPanel();
		target.setMinimumSize(new Dimension(1, 168));
		target.setBorder(new CompoundBorder(new EmptyBorder(6, 0, 6, 0), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		target.setPreferredSize(new Dimension(1, 168));
		panel_6.add(target);
		target.setLayout(new BoxLayout(target, BoxLayout.Y_AXIS));
		
		JPanel panel_10 = new JPanel();
		target.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblTargets = new JLabel("Target(s): ");
		lblTargets.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTargets.setHorizontalAlignment(SwingConstants.LEFT);
		panel_10.add(lblTargets);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		target.add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setMinimumSize(new Dimension(23, 23));
		scrollPane_1.setViewportView(list_1);
		
		JPanel players = new JPanel();
		players.setPreferredSize(new Dimension(225, 270));
		players.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(1, 1, 1, 1), new EtchedBorder(EtchedBorder.LOWERED, null, null)), new EmptyBorder(3, 3, 3, 3)));
		panel_6.add(players);
		players.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		players.add(panel_8, BorderLayout.NORTH);
		panel_8.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Players: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel_8.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 2147483647));
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
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_16 = new JPanel();
		panel_3.add(panel_16, BorderLayout.SOUTH);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_16.add(panel_7, BorderLayout.NORTH);
		panel_7.setBorder(new CompoundBorder(new EmptyBorder(2, 0, 2, 0), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		panel_7.setLayout(new BorderLayout(0, 0));
		
		pauseButton.setFont(new Font("Tahoma", Font.PLAIN, 34));
		pauseButton.addActionListener(this);
		panel_7.add(pauseButton);
		
		JPanel panel_9 = new JPanel();
		panel_16.add(panel_9, BorderLayout.SOUTH);
		panel_9.setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new CompoundBorder(new EmptyBorder(2, 0, 0, 2), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
		panel_9.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JButton quit = new JButton("Quit");
		quit.addActionListener(this);
		panel_11.add(quit);
		quit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
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
		
		JPanel panel_17 = new JPanel();
		getContentPane().add(panel_17, BorderLayout.NORTH);
		panel_17.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_18 = new JPanel();
		panel_17.add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JLabel lblYouAre = new JLabel("You are:");
		lblYouAre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_18.add(lblYouAre, BorderLayout.EAST);
		
		JPanel panel_19 = new JPanel();
		panel_17.add(panel_19);
		panel_19.setLayout(new BorderLayout(0, 0));
		
		JLabel lblName = new JLabel(" name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_19.add(lblName);
		
		getContentPane().add(wikipediaBrowser, BorderLayout.CENTER);
		
		
	}

	@Override
	public void playerJoined(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLeft(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerComplete(String playerName, long time, int clickCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerQuit(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pauseRequested() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void togglePause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTime(long milliSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClicks(int clickCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeTargetDesination(String desination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSiteToPath(String site) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStartPage(String page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDesitination(String page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepNewRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReadyPanel(JPanel readyPanel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IWebBrowser getBrowser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "PAUSE!"){
			makeBrowserPaused();
			//TODO Record who sent the request and unpause
		}
		
		if(e.getActionCommand() == "Quit"){
			System.exit(0);
		}
		
		
	}

	@Override
	public void playerStatsChanged(String playerName, long time, int clickCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayerRank(String playerName, int rank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDone(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerPlaying(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerSpectating(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerWin(String playerName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerProgress(String playerName, int percentage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPercatageAgree(int perc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayersNeedToPause(int newCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void allowJumpBack(boolean allow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToHistory(String page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startNewRound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nowSpectating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nowPlaying() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nowDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
