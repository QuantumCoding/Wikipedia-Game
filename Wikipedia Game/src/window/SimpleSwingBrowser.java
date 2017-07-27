package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

public class SimpleSwingBrowser extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    
    private final JLabel lblStatus = new JLabel();

    private final JButton btnGo = new JButton("Go");
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar(); 

    public SimpleSwingBrowser() {
        super();
        initComponents();
    }

    private void initComponents() {
    	setLayout(new BorderLayout());
    	
    	Platform.runLater(() -> { createScene(); });
    
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String url = txtURL.getText();
            	Platform.runLater(() -> { 
            		 String tmp = toURL(url);
            		 
                     if (tmp == null) {
                         tmp = toURL("http://" + url);
                     }
      
                     engine.load(tmp); });
            }
        };

        btnGo.addActionListener(al);
        txtURL.addActionListener(al);

        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);

        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        topBar.add(txtURL, BorderLayout.CENTER);
        topBar.add(btnGo, BorderLayout.EAST);

        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(lblStatus, BorderLayout.CENTER);
        statusBar.add(progressBar, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(jfxPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(1024, 600));
        
        setVisible(true);
    }
    
    private void createScene() {
    	 WebView view = new WebView();
         engine = view.getEngine();

         engine.titleProperty().addListener(new ChangeListener<String>() {
             public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                 SwingUtilities.invokeLater(() -> {
                 });
             }
         });

         engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
             public void handle(final WebEvent<String> event) {
                 SwingUtilities.invokeLater(() -> {
                     lblStatus.setText(event.getData());
                 });
             }
         });

         engine.locationProperty().addListener(new ChangeListener<String>() {
             public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
                 SwingUtilities.invokeLater(() -> {
                     txtURL.setText(newValue);
                 });
             }
         });

         engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
             public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
                 SwingUtilities.invokeLater(() -> {
                     progressBar.setValue(newValue.intValue());
                 });
             }
         });

         engine.getLoadWorker()
                 .exceptionProperty()
                 .addListener(new ChangeListener<Throwable>() {
                     public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                         if (engine.getLoadWorker().getState() == State.FAILED) {
                             SwingUtilities.invokeLater(() -> {
                                 JOptionPane.showMessageDialog(
                                         SimpleSwingBrowser.this,
                                         (value != null) ?
                                         engine.getLocation() + "\n" + value.getMessage() :
                                         engine.getLocation() + "\nUnexpected error.",
                                         "Loading error...",
                                         JOptionPane.ERROR_MESSAGE);
                             });
                         }
                     }
                 });

         jfxPanel.setScene(new Scene(view));
     }
    
    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }
    
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setTitle("Web Browser - Wrapper");
    	frame.add(new SimpleSwingBrowser());
    	
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
	}
}