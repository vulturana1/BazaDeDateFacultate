import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InserareProf {
	private static JFrame frame;
	Connection con;
	int x;
	int y;
	String nume;
	String prenume;
	JTextField tf1 = new JTextField("");
	JTextField tf2 = new JTextField("");
	JTextField tf3 = new JTextField("");
	JTextField tf4 = new JTextField("");
	JTextField tf5 = new JTextField("");
	
	InserareProf(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame = new JFrame("Inserare Profesor");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(800, 300);
		frame.setLocationRelativeTo(null);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6=new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8=new JPanel();


		JLabel s = new JLabel("Inserare Profesor");
		s.setFont(new Font("", Font.BOLD, 20));
		panel1.add(s);
		panel1.setLayout(new FlowLayout());
		
		JLabel cnp=new JLabel("ID utiliator");
        tf1.setColumns(10);
        panel2.add(cnp);
        panel2.add(tf1);
        panel2.setLayout(new FlowLayout());
        
        JLabel nume1=new JLabel("NR min ore");
        tf2.setColumns(10);
        panel3.add(nume1);
        panel3.add(tf2);
        panel3.setLayout(new FlowLayout());
        
        JLabel prenume1=new JLabel("NR max ore");
        tf3.setColumns(10);
        panel4.add(prenume1);
        panel4.add(tf3);
        panel4.setLayout(new FlowLayout());
        
        JLabel Adresa=new JLabel("Departamentul");
        tf4.setColumns(10);
        panel5.add(Adresa);
        panel5.add(tf4);
        panel5.setLayout(new FlowLayout());
        
        JLabel iban=new JLabel("NR studenti");
        tf5.setColumns(10);
        panel6.add(iban);
        panel6.add(tf5);
        panel6.setLayout(new FlowLayout());
        
        
        JButton ok = new JButton("Inserare");
		ok.addActionListener(new InsProf());
		panel7.add(ok);
		panel5.setLayout(new FlowLayout());
		
		JButton inapoi = new JButton("Inapoi");
		inapoi.addActionListener(new Inapoi());
		inapoi.setForeground(Color.red);
		panel8.add(inapoi);
		panel8.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel p = new JPanel();
		p.add(panel1);
		p.add(panel2);
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		p.add(panel6);
		p.add(panel7);
		p.add(panel8);
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
	class InsProf implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement mySt = null;
				mySt = con.prepareCall("{call insert_profesor(?, ?, ?,?,?)}");
				mySt.setString(1, tf1.getText());
				mySt.setString(2, tf2.getText());
				mySt.setString(3, tf3.getText());
				mySt.setString(4, tf4.getText());
				mySt.setString(5, tf5.getText());
				mySt.execute();
				new Succes("A fost inserat cu succes");
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}

		}

	}
}


