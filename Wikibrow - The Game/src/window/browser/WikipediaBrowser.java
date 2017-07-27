package window.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import interfaces.ILoadListener;
import interfaces.ISiteChangeListener;
import interfaces.IWebBrowser;
import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WikipediaBrowser extends JPanel implements IWebBrowser {
	private static final long serialVersionUID = 1L;

	private WebEngine engine;
	private JFXPanel webPanel;
	
	private JTextField searchBar;
	private JButton goButton;
	
	private JLabel statusLabel;
	private JProgressBar loadingProgress;
	
	private ArrayList<ISiteChangeListener> siteListeners;
	private ArrayList<ILoadListener> loadListeners;
	
	public WikipediaBrowser() {
        super();
        initComponents();
		siteListeners = new ArrayList<>();
		loadListeners = new ArrayList<>();
    }

    private void initComponents() {
    	setLayout(new BorderLayout());

		webPanel = new JFXPanel();
		Platform.runLater(() -> { createScene(); });
		
		JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
		searchPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		searchPanel.add(searchBar = new JTextField(), BorderLayout.CENTER);
		searchPanel.add(goButton = new JButton("Go"), BorderLayout.EAST);
		
		loadingProgress = new JProgressBar();
		loadingProgress.setPreferredSize(new Dimension(150, 18));
		loadingProgress.setStringPainted(true);
		
		JPanel statusPanel = new JPanel(new BorderLayout(5, 0));
		statusPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusPanel.add(statusLabel = new JLabel(), BorderLayout.CENTER);
		statusPanel.add(loadingProgress, BorderLayout.EAST);
		
		JPanel basePanel = new JPanel(new BorderLayout(5, 0));
		basePanel.add(searchPanel, BorderLayout.CENTER);
		basePanel.add(statusPanel, BorderLayout.EAST);
		
		add(webPanel, BorderLayout.CENTER);
		add(basePanel, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(1024, 600));
		
		ActionListener searchAction = (e) -> { setSite(searchBar.getText()); };
		searchBar.addActionListener(searchAction);
		goButton.addActionListener(searchAction);
	}

	private void createScene() {
		WebView view = new WebView();
		engine = view.getEngine();
		
		engine.titleProperty().addListener((ov, oldValue, newValue) -> {
        	SwingUtilities.invokeLater(() -> { });
        });

        engine.setOnStatusChanged((event) -> {
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText(event.getData());
            });
        });
		
        engine.locationProperty().addListener((ov, oldValue, newValue) -> {
    		String currentLocation = engine.getLocation();
        	SwingUtilities.invokeLater(() -> { 
        		searchBar.setText(currentLocation); 
        		for(ISiteChangeListener listener : siteListeners)
        			listener.siteChanges(WikipediaBrowser.this, oldValue, currentLocation);
        	});
        });
        
        engine.getLoadWorker().workDoneProperty().addListener((observableValue, oldValue, newValue) -> {
            SwingUtilities.invokeLater(() -> { 
            	loadingProgress.setValue(newValue.intValue()); 
            });
            
            for(ILoadListener listener : loadListeners)
        		listener.pageLoaded(this, newValue.floatValue());
        });
		
        engine.getLoadWorker().exceptionProperty().addListener((o, old, value) -> {
			if(engine.getLoadWorker().getState() == State.FAILED) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(
						WikipediaBrowser.this, value != null ?
							engine.getLocation() + "\n" + value.getMessage() :
							engine.getLocation() + "\nUnexpected error.",
						"Loading error...",
						JOptionPane.ERROR_MESSAGE
					);
				});
			}
		});
        
		webPanel.setScene(new Scene(view));
	}
	
	private static String toURL(String str) {
        try { return new URL(str).toExternalForm(); } 
        catch(MalformedURLException exception) { return null; }
    }

	public void setSite(String site) {
		Platform.runLater(() -> {
			String tmp = toURL(site);
            if(tmp == null)
                tmp = toURL("wikipedia://" + site);
            engine.load(tmp);
		});
	}
	
	public String getSite() { return engine.getLocation(); }
	
	public void addSiteChangeListener(ISiteChangeListener listener) {
		siteListeners.add(listener);
	}

	public void addLoadListener(ILoadListener listener) {
		loadListeners.add(listener);
	}

	public void enableSearch(boolean enable) {
		searchBar.setEnabled(enable);
		goButton.setEnabled(enable);
	}
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }

		URL.setURLStreamHandlerFactory(new WikipediaURLStreamHandlerFactory());
		
		JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setTitle("Wikipedia Browser - Wrapper");
    	frame.add(new WikipediaBrowser());
    	
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
	}
}
