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

//import AsignareProfesor.Inapoi;

public class CautaUtilizator {
	Connection con;
	private static JFrame frame;
	int x;
	int y;
	String nume;
	String prenume;
	JTextField tf1 = new JTextField("");
	JTextField tf2 = new JTextField("");

	CautaUtilizator(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		frame = new JFrame("Cauta utilizator");
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame.setLocation(this.x, this.y);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);

		JLabel sal = new JLabel("Cauta utilizator");
		sal.setFont(new Font("", Font.BOLD, 20));

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		panel1.add(sal);
		panel1.setLayout(new FlowLayout());

		JLabel nume1 = new JLabel("Nume ");
		JLabel prenume1 = new JLabel("Prenume");
		tf1.setColumns(10);
		tf2.setColumns(10);

		panel2.add(nume1);
		panel2.add(tf1);
		panel2.add(prenume1);
		panel2.add(tf2);
		panel2.setLayout(new FlowLayout());

		JButton ok = new JButton("Cauta");
		panel3.add(ok);
		panel3.setLayout(new FlowLayout());
		ok.addActionListener(new Vizualizare());

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

	class Vizualizare implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new VizUtilizator(x, y, nume, prenume, con, tf1.getText(), tf2.getText());
			frame.dispose();

		}

	}

}
