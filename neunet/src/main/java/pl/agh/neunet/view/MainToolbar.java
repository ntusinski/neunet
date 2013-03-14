package pl.agh.neunet.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainToolbar extends JMenuBar {
	private static final long serialVersionUID = 4289699331641521168L;

	public MainToolbar() {
		add(createActionsMenu());
	}

	private JMenu createActionsMenu() {
		final JMenu actions = new JMenu("Actions");
		final JMenuItem setNeurons = new JMenuItem("Set neurons");
		final JMenuItem exit = new JMenuItem("Exit");

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		actions.add(setNeurons);
		actions.addSeparator();
		actions.add(exit);

		return actions;
	}
}
