package window;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import interfaces.IClientInterface;
import javax.swing.JProgressBar;

public class PausePanel extends JPanel implements ComponentListener, ActionListener {
	private static final MouseAdapter MOUSE_BLOCK = new MouseAdapter() {};
	
	private static final Color PAUSE_TEXT_COLOR = new Color(230, 230, 0);
	private static final Color PAUSE_COLOR = new Color(175, 175, 175, 200);
	private static final Font PAUSE_FONT = new Font("", Font.BOLD, 72);
	private static final String PAUSE_MESSAGE = "Paused";
	private static Rectangle2D PAUSE_BOUNDS;
	
	private static final String PAUSE_REQUESTED = "A Pause has been Requested:";
	private static final String UNPAUSE_REQUEST = "Are you ready to Resume:";
	
	private static final long serialVersionUID = 1L;
	private JButton acceptButton;
	private JButton declineButton;
	
	private boolean isPaused;

	private JPanel topPanel;
	private JLabel label;
	
	private IClientInterface client;
	private JPanel requestPanel;
	private JPanel resumePanel;
	private JButton resumeGameButton;
	private JPanel optionsPanel;
	private JProgressBar progressBar;
	private JPanel progressBarPanel;
	
	private int playersNeedToPause;
	
	public PausePanel(IClientInterface client, Rectangle defaultSize) {
		this.client = client;
		setBounds(defaultSize);
		
		setBackground(new Color(255, 255, 255, 0));
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		topPanel = new JPanel();
		topPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		label = new JLabel(PAUSE_REQUESTED);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		topPanel.add(label);
		
		optionsPanel = new JPanel();
		optionsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		topPanel.add(optionsPanel);
		optionsPanel.setLayout(new CardLayout(0, 0));
		
		requestPanel = new JPanel();
		optionsPanel.add(requestPanel, "requestPanel");
		requestPanel.setLayout(new GridLayout(0, 2, 5, 0));
		
		acceptButton = new JButton("Accept");
		requestPanel.add(acceptButton);
		acceptButton.setForeground(new Color(0, 128, 0));
		acceptButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		declineButton = new JButton("Decline");
		requestPanel.add(declineButton);
		declineButton.setForeground(new Color(139, 0, 0));
		declineButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		resumePanel = new JPanel();
		optionsPanel.add(resumePanel, "resumePanel");
		resumePanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		resumeGameButton = new JButton("Resume Game");
		resumeGameButton.setForeground(new Color(153, 0, 153));
		resumeGameButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		resumePanel.add(resumeGameButton);
		
		progressBarPanel = new JPanel();
		progressBarPanel.setBorder(new EmptyBorder(5, 1, 0, 1));
		topPanel.add(progressBarPanel);
		progressBarPanel.setLayout(new BoxLayout(progressBarPanel, BoxLayout.X_AXIS));
		
		progressBar = new JProgressBar();
		progressBar.setToolTipText("Current Progress to Pause");
		progressBar.setValue(63);
		progressBar.setForeground(new Color(0, 153, 51));
		progressBar.setStringPainted(true);
		progressBarPanel.add(progressBar);
		
		declineButton.addActionListener(this);
		acceptButton.addActionListener(this);
		resumeGameButton.addActionListener(this);

		topPanel.setVisible(false);
	}
	
	public void paintComponent(Graphics g) {
		if(PAUSE_BOUNDS == null) {
			g.setFont(PAUSE_FONT);
			PAUSE_BOUNDS = g.getFontMetrics().getStringBounds(PAUSE_MESSAGE, g);
		}
		
		super.paintComponent(g);
		
		if(isPaused) {
			g.setColor(PAUSE_COLOR);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setColor(PAUSE_TEXT_COLOR);
			g.setFont(PAUSE_FONT); g.drawString(PAUSE_MESSAGE, 
					(int) ((getWidth() - PAUSE_BOUNDS.getWidth()) / 2), 
					(int) ((getHeight() - PAUSE_BOUNDS.getHeight()) / 2 + PAUSE_BOUNDS.getHeight()/2));
		}
	}

	public void setPlayersNeedToPause(int newCount) { playersNeedToPause = newCount; }
	public void setPercatageAgree(int perc) {
		progressBar.setValue(perc);
		String readySection = "Willing: " + Math.round(perc / 100.0f * playersNeedToPause);
		String needSection = "Needed: " + playersNeedToPause;
		
		Graphics g = progressBar.getGraphics();
		int width = progressBar.getWidth();
		if(g == null) {
			progressBar.setString(perc + "%");
			return;
		}
		
		int spaceWidth = (int) g.getFontMetrics().getStringBounds(" ", g).getWidth();
		int readyWidth = (int) g.getFontMetrics().getStringBounds(readySection, g).getWidth();
		int needWidth = (int) g.getFontMetrics().getStringBounds(needSection, g).getWidth();
		int divWidth = (int) g.getFontMetrics().getStringBounds(" || ", g).getWidth();
		
		int sumWidth = readyWidth + needWidth + divWidth;
		int spaceComp = (width - sumWidth) / 4 / spaceWidth;
		String spacer = ""; for(int i = 0; i < spaceComp; i ++) spacer += " ";
		
		progressBar.setString(spacer + readySection + spacer + " || " + spacer + needSection + spacer);
	}
	
	public void requestPause() {
		label.setText(PAUSE_REQUESTED);
		topPanel.setVisible(true);
	}
	
	public void togglePause() { 
		isPaused = !isPaused;
		topPanel.setVisible(isPaused);
		resumeGameButton.setEnabled(true);
		label.setText(isPaused ? UNPAUSE_REQUEST : PAUSE_REQUESTED);
		((CardLayout) optionsPanel.getLayout()).show(optionsPanel, isPaused ? "resumePanel" : "requestPanel");
		setVisible(isPaused);
		repaint();
		
		if(isPaused)
			addMouseListener(MOUSE_BLOCK);
		else
			removeMouseListener(MOUSE_BLOCK);
	}

	public void componentResized(ComponentEvent e) {
		setBounds(e.getComponent().getBounds());
		Component component = getParent();
		while(!(component instanceof JFrame))
			component = component.getParent();
		component.setVisible(true);
	}

	public void componentMoved(ComponentEvent e) { }
	public void componentShown(ComponentEvent e) { }
	public void componentHidden(ComponentEvent e) { }

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == acceptButton) {
			setVisible(false);
			client.acceptPause();
			return;
		}
		
		if(e.getSource() == declineButton) {
			setVisible(false);
			client.rejectPause();
			return;
		}
		
		if(e.getSource() == resumeGameButton) {
			resumeGameButton.setEnabled(false);
			client.requestUnpause();
			return;
		}
	}
}
