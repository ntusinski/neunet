package pl.agh.neunet.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pl.agh.neunet.action.ActionType;
import pl.agh.neunet.view.dialogs.FileNameDialog;

public class MainToolbar extends JMenuBar {
	private static final long serialVersionUID = 4289699331641521168L;

	public MainToolbar() {
		add(createActionsMenu());
	}

	private JMenu createActionsMenu() {
		final JMenu actions = new JMenu("File");

		final JMenuItem defaultFileItem = new JMenuItem("Create default file");
		final JMenuItem exitItem = new JMenuItem("Exit");

		defaultFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FileNameDialog(ActionType.CREATE_DEFAULT_PROPERTY_FILE)
						.setVisible(true);
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		actions.add(defaultFileItem);
		actions.addSeparator();
		actions.add(exitItem);

		return actions;
	}
}
