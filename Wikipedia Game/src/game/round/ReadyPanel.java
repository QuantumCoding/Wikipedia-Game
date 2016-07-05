package game.round;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Util.Formater;
import game.Communication;
import game.GameClient;

public class ReadyPanel extends JPanel {
	private static final int PHASE_DURATION = 4;
	
	private static final int LAYER_WIDTH = 3;
	private static final int LAYER_COUNT = 5;
	private static final Color LAYER_COLOR = new Color(150, 150, 150, 75);
	
	private static final long serialVersionUID = 1L;
	private JButton readyButton;
	private JButton cancleButton;
	private JSeparator separator;
	private JLabel timeLabel;
	private JProgressBar percentReadyProgress;
	private JPanel viewport;
	private JButton spectateButton;
	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private String number;
	private Color textColor;
	private int fade;
	
	private GameClient client;
	private boolean newRoundReadyShow;
	private int playersNeedToStart;
	
	private Thread autoReadyThread;
	private boolean autoReadyThreadStop;
	
	public ReadyPanel(GameClient client) {
		this.client = client;
		
		int boarderPadding = LAYER_WIDTH * LAYER_COUNT;
		setBorder(new EmptyBorder(boarderPadding, boarderPadding, boarderPadding, boarderPadding));
		setBackground(new Color(255, 255, 255, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//CardLayout(0, 0));
		
		mainPanel = new JPanel();
		add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel southPanel = new JPanel();
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.setBackground(new Color(255, 255, 255, 0));
		
		separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255, 0));
		southPanel.add(separator);
		
		buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(2, 0, 0, 0));
		southPanel.add(buttonPanel);
		buttonPanel.setLayout(new CardLayout(0, 0));
		buttonPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel readyupPanel = new JPanel();
		buttonPanel.add(readyupPanel, "readyup");
		readyupPanel.setLayout(new GridLayout(0, 2, 5, 0));
		readyupPanel.setBackground(new Color(255, 255, 255, 0));
		
		readyButton = new JButton("Ready to Start");
		readyupPanel.add(readyButton);
		readyButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		readyButton.addActionListener((e) -> {
			client.sendMessage(Communication.PLAYER_READY);
			((CardLayout) buttonPanel.getLayout()).show(buttonPanel, "cancle");
			
//			new Thread(() -> {
//				long startVal = 1 * 1000;
//				long val = startVal;
//				while(val > 0) {
//					val -= 1;
//					setTimer(val, startVal);
//					try{Thread.sleep(1);}catch(InterruptedException w) {}
//				}
//				
//				for(int i = 0; i < 5; i ++) {
//					timeLabel.setVisible(false);
//					try{Thread.sleep(100);}catch(InterruptedException w) {}
//					
//					timeLabel.setVisible(true);
//					try{Thread.sleep(100);}catch(InterruptedException w) {}
//				}
//				
//				for(int i = 3; i > 0; i --) {
//					countDown(i);
//					try { Thread.sleep(2000); } catch(InterruptedException w) {}
//				}
//				
//				try { Thread.sleep(500); } catch(InterruptedException w) {}
//				countDown(0);
//			}).start();
		});
		
		readyButton.setFocusable(false);
		readyButton.setBackground(new Color(255, 255, 255, 0));
		
		spectateButton = new JButton("Spectate");
		spectateButton.addActionListener((e) -> {
			client.sendMessage(Communication.PLAYER_SPECTATING);
			((CardLayout) buttonPanel.getLayout()).show(buttonPanel, "cancle");
		});
		
		spectateButton.setFocusable(false);
		spectateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		readyupPanel.add(spectateButton);
		spectateButton.setBackground(new Color(255, 255, 255, 0));
		
		cancleButton = new JButton("Cancle");
		cancleButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancleButton.setFocusable(false);
		cancleButton.addActionListener((e) -> {
			client.sendMessage(Communication.PLAYER_UNREADY);
			((CardLayout) buttonPanel.getLayout()).show(buttonPanel, "readyup");
		});
		
		buttonPanel.add(cancleButton, "cancle");
		cancleButton.setBackground(new Color(255, 255, 255, 0));
		
		JPanel statusPanel = new JPanel();
		mainPanel.add(statusPanel, BorderLayout.NORTH);
		statusPanel.setLayout(new GridLayout(0, 2, 0, 0));
		statusPanel.setBackground(new Color(255, 255, 255, 0));
		
		JPanel timePanel = new JPanel();
		statusPanel.add(timePanel);
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		timePanel.setBackground(new Color(255, 255, 255, 0));
		
		JLabel timeRemainingLabel = new JLabel("Time Remaining: ");
		timeRemainingLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timeRemainingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		timePanel.add(timeRemainingLabel);
		timeRemainingLabel.setBackground(new Color(255, 255, 255, 0));
		
		Component horizontalStrut = Box.createHorizontalStrut(3);
		timePanel.add(horizontalStrut);
		horizontalStrut.setBackground(new Color(255, 255, 255, 0));
		
		timeLabel = new JLabel("00:00.000");
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		timePanel.add(timeLabel);
		timeLabel.setBackground(new Color(255, 255, 255, 0));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		timePanel.add(horizontalGlue);
		horizontalGlue.setBackground(new Color(255, 255, 255, 0));
		
		JPanel readyPercentPanel = new JPanel();
		readyPercentPanel.setBorder(new EmptyBorder(1, 0, 1, 1));
		statusPanel.add(readyPercentPanel);
		readyPercentPanel.setLayout(new BoxLayout(readyPercentPanel, BoxLayout.X_AXIS));
		readyPercentPanel.setBackground(new Color(255, 255, 255, 0));
		
		percentReadyProgress = new JProgressBar();
		percentReadyProgress.setValue(50);
		percentReadyProgress.setToolTipText("Players Ready");
		percentReadyProgress.setStringPainted(true);
		percentReadyProgress.setForeground(new Color(204, 0, 0));
		percentReadyProgress.setFont(new Font("Tahoma", Font.PLAIN, 14));
		readyPercentPanel.add(percentReadyProgress);
		percentReadyProgress.setBackground(new Color(255, 255, 255, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBackground(new Color(255, 255, 255, 0));
		
		viewport = new JPanel();
		scrollPane.setViewportView(viewport);
		viewport.setBackground(new Color(255, 255, 255, 0));
		viewport.setLayout(new BoxLayout(viewport, BoxLayout.Y_AXIS));
		
		newRoundReadyShow = true;		
		goFadeCount = 100;
		repaint();
	}
	
	private double startNum;
	public void countDown(int number) {
		if(number > startNum) {
			startNum = number - 1;
		}
		
		goFadeCount = 100;
		mainPanel.setVisible(false);
		this.number = number + "...";
		
		switch(number) {
			case 0: 
				textColor = new Color(250, 250, 250);
				this.number = "Go!";
				fade = 255;
			return;
		}
		
		number -= 1;
		double halfStart = startNum / 2;
		if(number >= halfStart)
			textColor = new Color(255 - (int) ((number - halfStart) / halfStart * 255), 225, 0);
		else
			textColor = new Color(225, (int) (number / halfStart * 255), 0);
		
		fade = 0;
		
		new Thread(() -> {
			double factor = 1000;
			for(int i = 0; i < factor; i ++) {
				double val = i < factor / 2.0 ? 
						Math.pow((i / factor) * 2 - 1, 5) + 1 :
						Math.pow(-(i / factor) * 2 + 1, 5) + 1;
				
				val = val > 1 ? 1 : val < 0 ? 0 : val;
				fade = (int) (val * 255);
				try { Thread.sleep(i < factor / 2.0 ? 1 : 2); } catch(InterruptedException e) {}
			}
		}, "Fade Control Thread").start();
	}
	
	private int goFadeCount;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.clearRect(0, 0, getWidth(), getHeight());
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, goFadeCount / 100.0f));
		
		g.setColor(LAYER_COLOR);
		for(int i = 0; i < LAYER_COUNT; i ++) {
			int offset = 0 + i * LAYER_WIDTH;
			int sizeW = getWidth() - i * LAYER_WIDTH * 2;
			int sizeH = getHeight() - i * LAYER_WIDTH * 2;
			
			g.fillRoundRect(offset, offset, sizeW, sizeH, 10, 10);
		}
		
		if(number != null) {
			g.setColor(textColor);
			g.setFont(new Font("", Font.BOLD, 128));
			if(number.endsWith("..."))
				((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade / 255.0f));
			Rectangle2D fontBound = g.getFontMetrics().getStringBounds(number, g);
			g.drawString(number, (int) ((getWidth() - fontBound.getWidth()) / 2), 
								 (int) ((getHeight() + fontBound.getHeight()) / 2 - fontBound.getHeight()/4));
			
			if(!number.endsWith("...")) {
				goFadeCount -= 5;
				
				if(goFadeCount <= 0) {
					setVisible(false);
					startNum = 0;
					number = "";
				}
			}
		}
		
		try {
			Thread.sleep(1);
		} catch(InterruptedException e) {}
	
		repaint();
	}
	
	public void setVisible(boolean visible) {
		if(visible == true) {
			((CardLayout) buttonPanel.getLayout()).show(buttonPanel, newRoundReadyShow ? "readyup" : "cancle");
			newRoundReadyShow = false;
			number = null;
			goFadeCount = 100;
		}
		
		super.setVisible(visible);
	}
	
	public void addPlayer(String username) { viewport.add(new PlayerReadyupPanel(username)); viewport.invalidate(); }
	public void removePlayer(String username) { viewport.remove(getPlayerPanel(username)); viewport.invalidate(); }

	public void playerSpectate(String username) { getPlayerPanel(username).spectate(); }
	public void playerReady(String username, boolean ready) { getPlayerPanel(username).ready(ready); }
	
	public void stopTimer() {
		if(autoReadyThread == null)
			return;
		
		try {
			autoReadyThreadStop = true;
			autoReadyThread.interrupt();
			autoReadyThread.join();
			
			autoReadyThread = null;
		} catch(InterruptedException e) {}
	}
	
	public void startTimer(long startTime) {
		autoReadyThread = new Thread(() -> {
			autoReadyThreadStop = false;
			long val = startTime;
			while(val > 0 && !autoReadyThreadStop) {
				val -= 1;
				setTimer(val, startTime);
				try{ Thread.sleep(1); }catch(InterruptedException w) {}
			}
		}, "ReadyPanel Auto-Ready Timer");
		
		autoReadyThread.start();
	}
	
	public void setTimer(long milliseconds, long startVal) {
		double percent = (double) milliseconds / (double) startVal;
		double red   = Math.max(Math.min(Math.pow((-percent * PHASE_DURATION + PHASE_DURATION), 3) / 3, 1), 0);
		double green = Math.max(Math.min(Math.pow(( percent * PHASE_DURATION), 3) / 3, 1), 0);
		timeLabel.setForeground(new Color((float) red, (float) green, 0));
		timeLabel.setText(Formater.formatTime(milliseconds));
	}
	
	public void setPercatageReady(int perc) {
		percentReadyProgress.setValue(perc);
		String readySection = "Ready: " + Math.round(perc / 100.0f * playersNeedToStart);
		String needSection = "Needed: " + playersNeedToStart;
		
		Graphics g = percentReadyProgress.getGraphics();
		int width = percentReadyProgress.getWidth();
		
		int spaceWidth = (int) g.getFontMetrics().getStringBounds(" ", g).getWidth();
		int readyWidth = (int) g.getFontMetrics().getStringBounds(readySection, g).getWidth();
		int needWidth = (int) g.getFontMetrics().getStringBounds(needSection, g).getWidth();
		int divWidth = (int) g.getFontMetrics().getStringBounds(" || ", g).getWidth();
		
		int sumWidth = readyWidth + needWidth + divWidth;
		int spaceComp = (width - sumWidth) / 4 / spaceWidth;
		String spacer = ""; for(int i = 0; i < spaceComp; i ++) spacer += " ";
		
		percentReadyProgress.setString(spacer + readySection + spacer + " || " + spacer + needSection + spacer);
	}
	
	public void setPlayersNeedToStart(int newCount) { playersNeedToStart = newCount; }
	
	public void start() { newRoundReadyShow = true; }
	
	private PlayerReadyupPanel getPlayerPanel(String username) {
		for(Component component : viewport.getComponents()) {
			if(!(component instanceof PlayerReadyupPanel)) continue;
			if(!component.getName().equals(username)) continue;
			return (PlayerReadyupPanel) component;
		}
		
		return null;
	}
	
	public void setSize(Dimension dimetion) {
		int width = dimetion.width * 3 / 4;
		int height = dimetion.height * 3 / 4;
		height = width = Math.min(width, height);
		
		super.setLocation((dimetion.width - width) / 2, (dimetion.height - height) / 2);
		super.setSize(width, height);
	}
	
	public String getName() { return client.getUsername(); }
	
	public static void main(String args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		main(null, false);
	}
	
	public static void main(JPanel panel, boolean b) {
		if(panel == null) 
			panel = new ReadyPanel(null);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Wrapper Frame: " + panel.getName());
		frame.setSize(500, 500);
		
		frame.getContentPane().add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
