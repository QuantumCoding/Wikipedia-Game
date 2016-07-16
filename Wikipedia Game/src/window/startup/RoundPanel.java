package window.startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.GameServer;
import game.round.RoundModel;
import window.browser.WikipediaSearch;

public class RoundPanel extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private JTextField searchDestinationTextField;
	
	private JButton addDestingationButton;
	private JButton searchDesinationButton;
	private JButton moveDestUpButton;
	private JButton moveDestDownButton;
	private JButton removeDestButton;
	private JButton newRoundButton;
	
	private JList<RoundModel> roundsList;
	private JList<String> desinationsList;
	private JList<String> resultList;
	
	private RoundModel selectedRound;
	private DefaultListModel<RoundModel> roundsModel;
	private DefaultListModel<String> desinationsModel;
	private DefaultListModel<String> resultsModel;
	private JLabel winnerNameLabel;
	private JButton deleteRoundButton;
	private JButton queueButton;
	
	private GameServer server;
	
	public RoundPanel(GameServer server) {
		this.server = server;
		setLayout(new BorderLayout(0, 0));
		
		JPanel roundListPanel = new JPanel();
		roundListPanel.setPreferredSize(new Dimension(225, 400));
		add(roundListPanel, BorderLayout.WEST);
		roundListPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel roundLabelButtonPanel = new JPanel();
		roundListPanel.add(roundLabelButtonPanel, BorderLayout.NORTH);
		roundLabelButtonPanel.setLayout(new BoxLayout(roundLabelButtonPanel, BoxLayout.X_AXIS));
		
		JLabel roundsLabel = new JLabel("Rounds");
		roundLabelButtonPanel.add(roundsLabel);
		roundsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		roundLabelButtonPanel.add(horizontalGlue_2);
		
		newRoundButton = new JButton("New");
		roundLabelButtonPanel.add(newRoundButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, 2));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roundListPanel.add(scrollPane);
		
		roundsModel = new DefaultListModel<>();
		roundsList = new JList<>(roundsModel);
		roundsList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scrollPane.setViewportView(roundsList);
		
		JPanel roundBodyPanel = new JPanel();
		roundBodyPanel.setBorder(new CompoundBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)), new EmptyBorder(2, 2, 2, 2)));
		add(roundBodyPanel, BorderLayout.CENTER);
		roundBodyPanel.setLayout(new GridLayout(0, 2, 3, 0));
		
		JPanel destinationsPanel = new JPanel();
		destinationsPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		roundBodyPanel.add(destinationsPanel);
		destinationsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel destinationLabelPanel = new JPanel();
		destinationsPanel.add(destinationLabelPanel, BorderLayout.NORTH);
		destinationLabelPanel.setLayout(new BoxLayout(destinationLabelPanel, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		destinationLabelPanel.add(horizontalGlue);
		
		JLabel destinationOrderLabel = new JLabel("Destination Order");
		destinationLabelPanel.add(destinationOrderLabel);
		destinationOrderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		destinationOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		destinationLabelPanel.add(horizontalGlue_1);
		
		JScrollPane desinationsScrollPane = new JScrollPane();
		desinationsScrollPane.setBorder(new CompoundBorder(new EmptyBorder(0, 2, 0, 2), new LineBorder(new Color(130, 135, 144))));
		desinationsScrollPane.setMinimumSize(new Dimension(32767, 32767));
		destinationsPanel.add(desinationsScrollPane, BorderLayout.CENTER);
		desinationsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		desinationsModel = new DefaultListModel<>();
		desinationsList = new JList<>(desinationsModel);
		desinationsList.setFont(new Font("Tahoma", Font.PLAIN, 16));
		desinationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		desinationsScrollPane.setViewportView(desinationsList);
		
		JPanel desinationControlls = new JPanel();
		desinationControlls.setBorder(new EmptyBorder(3, 0, 0, 0));
		destinationsPanel.add(desinationControlls, BorderLayout.SOUTH);
		desinationControlls.setLayout(new GridLayout(0, 3, 3, 0));
		
		moveDestUpButton = new JButton("Move Up");
		desinationControlls.add(moveDestUpButton);
		
		moveDestDownButton = new JButton("Move Down");
		desinationControlls.add(moveDestDownButton);
		
		removeDestButton = new JButton("Remove");
		desinationControlls.add(removeDestButton);
		
		JPanel rightPanel = new JPanel();
		roundBodyPanel.add(rightPanel);
		rightPanel.setLayout(new GridLayout(2, 1, 0, 3));
		
		JPanel searchPanel = new JPanel();
		rightPanel.add(searchPanel);
		searchPanel.setPreferredSize(new Dimension(200, 1));
		searchPanel.setMinimumSize(new Dimension(1, 1));
		searchPanel.setMaximumSize(new Dimension(1, 1));
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
		
		resultsModel = new DefaultListModel<>();
		resultList = new JList<>(resultsModel);
		resultList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultScrollPane.setViewportView(resultList);
		
		JPanel addDestinationPanel = new JPanel();
		addDestinationPanel.setMaximumSize(new Dimension(10, 32767));
		addDestinationPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		searchPanel.add(addDestinationPanel, BorderLayout.SOUTH);
		addDestinationPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		addDestingationButton = new JButton("Add");
		addDestinationPanel.add(addDestingationButton);
		
		JPanel infoOptionsPanel = new JPanel();
		rightPanel.add(infoOptionsPanel);
		infoOptionsPanel.setLayout(new BoxLayout(infoOptionsPanel, BoxLayout.Y_AXIS));
		infoOptionsPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		
		JPanel winnerPanel = new JPanel();
		winnerPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new EmptyBorder(2, 2, 2, 2)));
		infoOptionsPanel.add(winnerPanel);
		winnerPanel.setLayout(new BoxLayout(winnerPanel, BoxLayout.X_AXIS));
		
		JLabel winnerLabel = new JLabel("Winner: ");
		winnerPanel.add(winnerLabel);
		winnerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Component horizontalGlue_3 = Box.createHorizontalGlue();
		winnerPanel.add(horizontalGlue_3);
		
		winnerNameLabel = new JLabel("<No Winner>");
		winnerPanel.add(winnerNameLabel);
		winnerNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		Component verticalGlue = Box.createVerticalGlue();
		infoOptionsPanel.add(verticalGlue);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setMaximumSize(new Dimension(32767, 18));
		buttonPanel.setMinimumSize(new Dimension(10, 0));
		infoOptionsPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 1));
		
		queueButton = new JButton("Set as Next Round");
		queueButton.setForeground(new Color(0, 204, 0));
		queueButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		buttonPanel.add(queueButton);
		
		deleteRoundButton = new JButton("Delete");
		deleteRoundButton.setForeground(new Color(165, 42, 42));
		deleteRoundButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		buttonPanel.add(deleteRoundButton);
		
		newRoundButton.addActionListener(this);
		
		moveDestUpButton.addActionListener(this);
		moveDestDownButton.addActionListener(this);
		removeDestButton.addActionListener(this);
		
		addDestingationButton.addActionListener(this);
		searchDesinationButton.addActionListener(this);
		searchDestinationTextField.addActionListener(this);
		
		roundsList.addListSelectionListener(this);
		desinationsList.addListSelectionListener(this);
		resultList.addListSelectionListener(this);
		
		queueButton.addActionListener(this);
		deleteRoundButton.addActionListener(this);
		
		roundsList.setSelectedIndex(-1);
		valueChanged(new ListSelectionEvent(roundsList, -1, -1, false));
	}

	private void checkQueueButton() {
		if(!selectedRound.isValid())
			queueButton.setEnabled(false);
		else
			queueButton.setEnabled(!selectedRound.isNextRound());
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newRoundButton) {
			roundsModel.addElement(new RoundModel());
			return;
		}
		
		if(e.getSource() == searchDesinationButton || e.getSource() == searchDestinationTextField) {
			resultsModel.clear();
			addDestingationButton.setEnabled(false);
			for(String result : WikipediaSearch.lookUp(searchDestinationTextField.getText()))
				resultsModel.addElement(result);
			return;
		}
		
		if(e.getSource() == addDestingationButton) {
			String location = resultsModel.get(resultList.getSelectedIndex());
			desinationsModel.addElement(location);
			selectedRound.addDestination(location);
			checkQueueButton();
			return;
		}
		
		if(e.getSource() == moveDestDownButton) {
			int index = desinationsList.getSelectedIndex();
			desinationsList.setSelectedIndex(index + 1);
			valueChanged(new ListSelectionEvent(desinationsList, index + 1, index + 1, false));
			selectedRound.moveDown(index);
			
			String hold = desinationsModel.get(index);
			desinationsModel.set(index, desinationsModel.get(index + 1));
			desinationsModel.set(index + 1, hold);
			return;
		}
		
		if(e.getSource() == moveDestUpButton) {
			int index = desinationsList.getSelectedIndex();
			desinationsList.setSelectedIndex(index - 1);
			valueChanged(new ListSelectionEvent(desinationsList, index - 1, index - 1, false));
			selectedRound.moveUp(index);
			
			String hold = desinationsModel.get(index);
			desinationsModel.set(index, desinationsModel.get(index - 1));
			desinationsModel.set(index - 1, hold);
			return;
		}
		
		if(e.getSource() == removeDestButton) {
			int index = desinationsList.getSelectedIndex();
			selectedRound.removeDestination(index);
			desinationsModel.remove(index);
			checkQueueButton();
			return;
		}
		
		if(e.getSource() == deleteRoundButton) {
			int index = roundsList.getSelectedIndex();
			roundsModel.get(index).delete();
			roundsModel.remove(index);
			
			valueChanged(new ListSelectionEvent(roundsList, -1, -1, false));
			return;
		}
		
		if(e.getSource() == queueButton) {
			selectedRound.makeNextRound();
			queueButton.setEnabled(false);
			if(server.isRoundOver()) {
				server.prepNextRound(RoundModel.getSelectedModel().creatRound(server));
				server.readyNextRound();
			}
			
			return;
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == resultList) {
			addDestingationButton.setEnabled(resultList.getSelectedIndex() >= 0);			
			return;
		}
		
		if(e.getSource() == roundsList) {
			boolean isItemSelected = roundsList.getSelectedIndex() >= 0;
			desinationsModel.clear();
			
			addDestingationButton.setEnabled(false);
			moveDestUpButton.setEnabled(isItemSelected);
			moveDestDownButton.setEnabled(isItemSelected);
			removeDestButton.setEnabled(isItemSelected);
			
			deleteRoundButton.setEnabled(isItemSelected);
			queueButton.setEnabled(isItemSelected);
			
			searchDesinationButton.setEnabled(isItemSelected);
			searchDestinationTextField.setEnabled(isItemSelected);
			
			if(!isItemSelected) return;
			selectedRound = roundsModel.get(roundsList.getSelectedIndex());
			for(String destination : selectedRound.getLocations())
				desinationsModel.addElement(destination);

			checkQueueButton();
			winnerNameLabel.setText(selectedRound.getWinner());
			
			return;
		}
		
		if(e.getSource() == desinationsList) {
			boolean isItemSelected = desinationsList.getSelectedIndex() >= 0;
			moveDestDownButton.setEnabled(isItemSelected && desinationsList.getSelectedIndex() < desinationsModel.size() - 1);
			moveDestUpButton.setEnabled(isItemSelected && desinationsList.getSelectedIndex() > 0);
			removeDestButton.setEnabled(isItemSelected);
			
			return;
		}
	}
}
