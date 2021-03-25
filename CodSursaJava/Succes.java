import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Succes {
	private static JFrame frame;
	int x;
	int y;

	Succes(String s) {
		frame = new JFrame("Succes");
		frame.setSize(400, 130);
		frame.setLocation(600, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel succ = new JLabel("SUCCES");
		JLabel text = new JLabel(s);

		succ.setFont(new Font("", Font.BOLD, 20));
		text.setFont(new Font("", Font.BOLD, 12));
		succ.setForeground(Color.green);
		text.setForeground(Color.green);

		JButton ok = new JButton("OK");

		ok.addActionListener(new CloseListener());

		JPanel p = new JPanel();
		succ.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(succ);
		p.add(Box.createVerticalStrut(10));
		p.add(text);
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(Box.createVerticalStrut(5));
		p.add(ok);
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);

		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	class CloseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}

}
