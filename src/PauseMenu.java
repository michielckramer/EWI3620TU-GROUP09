//import java.awt.Button;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Frame;
//import java.awt.Panel;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.media.opengl.GL;
//
//public class PauseMenu implements ActionListener {
//
//	private Frame frame = new Frame();
//	private Button resumeButton = new Button("Resume Game");
//	private Button exitButton = new Button("Exit Game");
//
//	public void display(GL gl) {
//		frame.setAlwaysOnTop(true);
//		frame.toFront();
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//		frame.setUndecorated(true);
//		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
//		frame.setVisible(true);
//		resumeButton.setEnabled(true);
//		exitButton.setEnabled(true);
//		resumeButton.addActionListener(this);
//		exitButton.addActionListener(this);
//
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
//
//		resumeButton.setBounds(width / 10, height / 10, width / 5, height / 10);
//		exitButton.setBounds(7 * width / 10, height / 10, width / 5,
//				height / 10);
//
//		Panel content = new Panel();
//		content.setLayout(null);
//		content.add(resumeButton);
//		content.add(exitButton);
//		frame.add(content);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent event) {
//		Object src = event.getSource();
//		if (src == resumeButton) {
//			StateManager.resume();
//			frame.dispose();
//		}
//		if (src == exitButton) {
//			frame.dispose();
//			MazeRunner.exit();
//		}
//	}
// }
