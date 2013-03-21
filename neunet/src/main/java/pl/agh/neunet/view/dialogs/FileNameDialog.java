package pl.agh.neunet.view.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pl.agh.neunet.action.ActionType;
import pl.agh.neunet.viewmodel.impl.NeuronNetVM;

public class FileNameDialog extends JDialog {
	private static final long serialVersionUID = 6490754154703083900L;

	private static final String FILENAME_ARGUMENT_NAME = "filename";

	private final ActionType type;

	public FileNameDialog(ActionType type) {
		this.type = type;

		setSize(320, 240);
		setModal(true);
		setLocationRelativeTo(null);

		final JLabel label = new JLabel("Filename");
		final JTextField textField = new JTextField("neunet.xml");
		final JButton okButton = new JButton("OK");

		final GridLayout gridLayout = new GridLayout(2, 2);

		gridLayout.setHgap(7);
		gridLayout.setVgap(3);

		setLayout(gridLayout);
		setModal(true);
		setSize(250, 75);
		setLocationRelativeTo(null);

		label.setHorizontalAlignment(JLabel.CENTER);
		textField.setHorizontalAlignment(JTextField.CENTER);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, Object> arguments = new HashMap<String, Object>();

				arguments.put(FILENAME_ARGUMENT_NAME, textField.getText());

				NeuronNetVM.getInstance().requestAction(
						FileNameDialog.this.type, arguments);

				setVisible(false);
			}
		});

		add(label);
		add(textField);
		add(Box.createGlue());
		add(okButton);
	}
}
