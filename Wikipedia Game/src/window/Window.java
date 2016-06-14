package window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollBar;
import javax.swing.JList;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;

	public Window() {
		
		JLabel requestTab = new JLabel("Someone wants to pause the game, is that okay?");
		
		JButton acceptPause = new JButton("That's fine");
		
		JButton dealWithIt = new JButton("Deal with it, we're not stopping this game!");
		
		JLabel timer = new JLabel("Time: 0:00");
		
		JLabel lblNumberOfClicks = new JLabel("Number of clicks:");
		
		JButton requestPause = new JButton("Request Pause");
		
		JButton quit = new JButton("Quit");
		
		JLabel lblOrderOfWinners = new JLabel("Order of Winners: ");
		
		JLabel lblBrowser = new JLabel("Browser");
		
		JLabel lblLog = new JLabel("Previously explored Sites:");
		
		JList list = new JList();
		list.setToolTipText("");
		
		JLabel actualNumberOfClicks = new JLabel("0");
		
		JLabel startLocation = new JLabel("Where you must start:");
		
		JLabel endLocation = new JLabel("Where you need to get to:");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(requestPause))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(40)
							.addComponent(quit))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(125)
									.addComponent(requestTab))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(timer)
									.addGap(111)
									.addComponent(startLocation)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(endLocation)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(acceptPause)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(dealWithIt)))))
					.addContainerGap(78, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOrderOfWinners)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNumberOfClicks)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(actualNumberOfClicks, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 501, Short.MAX_VALUE)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addGap(114))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(679, Short.MAX_VALUE)
					.addComponent(lblLog)
					.addGap(39))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(395)
					.addComponent(lblBrowser)
					.addContainerGap(423, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(acceptPause)
						.addComponent(requestTab)
						.addComponent(dealWithIt))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(timer)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(actualNumberOfClicks)
								.addComponent(lblNumberOfClicks)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(startLocation)
								.addComponent(endLocation))))
					.addGap(2)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblOrderOfWinners)
							.addPreferredGap(ComponentPlacement.RELATED, 293, Short.MAX_VALUE)
							.addComponent(requestPause)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(quit)
							.addGap(30))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
							.addGap(55)
							.addComponent(lblLog)
							.addGap(56)
							.addComponent(lblBrowser)
							.addGap(244))))
		);
		getContentPane().setLayout(groupLayout);
	
	}
}
