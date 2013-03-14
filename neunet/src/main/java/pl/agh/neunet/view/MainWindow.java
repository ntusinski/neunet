package pl.agh.neunet.view;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1123283457654214183L;

	public MainWindow() {
		super("Neunet");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		setContentPane(new DrawingSurface());
		setJMenuBar(new MainToolbar());

		setVisible(true);
	}
}
