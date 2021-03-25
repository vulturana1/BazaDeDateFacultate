import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CautaCurs {
	private static JFrame frame;
	Connection con;
	int x;
	int y;
	String nume;
	String prenume;
	JTextField tf1 = new JTextField("");

	CautaCurs(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame = new JFrame("Cauta Curs");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);

		JLabel sal = new JLabel("Cauta Curs");
		sal.setFont(new Font("", Font.BOLD, 20));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		panel1.add(sal);
		panel1.setLayout(new FlowLayout());

		JLabel nume2 = new JLabel("Nume curs");

		tf1.setColumns(10);

		panel2.add(nume2);
		panel2.add(tf1);

		panel2.setLayout(new FlowLayout());

		JButton ok = new JButton("Nume profesor");
		JButton ok1 = new JButton("Studenti");
		ok.addActionListener(new Ok());
		ok1.addActionListener(new Stud());
		panel3.add(ok);
		panel3.add(ok1);
		panel3.setLayout(new FlowLayout());

		JButton inapoi = new JButton("Inapoi");
		inapoi.addActionListener(new Inapoi());
		inapoi.setForeground(Color.red);
		panel4.add(inapoi);
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JPanel p = new JPanel();
		p.add(panel1);
		p.add(panel2);
		p.add(panel3);
		p.add(panel4);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	class Inapoi implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new PaginaPrincipalaAdministrator(x, y, nume, prenume, con);
			frame.dispose();

		}

	}

	class Ok implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new VizProf(x, y, nume, prenume, tf1.getText(), con);
			frame.dispose();

		}

	}

	class Stud implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new VizStud(x, y, nume, prenume, tf1.getText(), con);
			frame.dispose();

		}

	}
}
