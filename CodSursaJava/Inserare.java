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



public class Inserare {
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
	JTextField tf6 = new JTextField("");
	JTextField tf7 = new JTextField("");
	JTextField tf8 = new JTextField("");
	Inserare(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame = new JFrame("Inserare utilizator");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(300, 800);
		frame.setLocationRelativeTo(null);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JPanel panel6 = new JPanel();
		JPanel panel7 = new JPanel();
		JPanel panel8 = new JPanel();
		JPanel panel9 = new JPanel();
		JPanel panel10 = new JPanel();
		JPanel panel11 = new JPanel();
		
		
		
		JLabel s = new JLabel("Inserare utilizator");
		s.setFont(new Font("", Font.BOLD, 20));
		panel1.add(s);
		panel1.setLayout(new FlowLayout());
		
		JLabel cnp=new JLabel("CNP");
        tf1.setColumns(10);
        panel2.add(cnp);
        panel2.add(tf1);
        panel2.setLayout(new FlowLayout());
        
        JLabel nume1=new JLabel("Nume");
        tf2.setColumns(10);
        panel3.add(nume1);
        panel3.add(tf2);
        panel3.setLayout(new FlowLayout());
        
        JLabel prenume1=new JLabel("Prenume");
        tf3.setColumns(10);
        panel4.add(prenume1);
        panel4.add(tf3);
        panel4.setLayout(new FlowLayout());
 
        JLabel Adresa=new JLabel("Adresa");
        tf4.setColumns(10);
        panel5.add(Adresa);
        panel5.add(tf4);
        panel5.setLayout(new FlowLayout());
        
        JLabel iban=new JLabel("IBAN");
        tf5.setColumns(10);
        panel6.add(iban);
        panel6.add(tf5);
        panel6.setLayout(new FlowLayout());
        
        JLabel tel=new JLabel("NR telefon");
        tf6.setColumns(10);
        panel7.add(tel);
        panel7.add(tf6);
        panel7.setLayout(new FlowLayout());
        
        JLabel contract=new JLabel("NR contract");
        tf7.setColumns(10);
        panel8.add(contract);
        panel8.add(tf7);
        panel8.setLayout(new FlowLayout());
        
        JLabel email=new JLabel("Email");
        tf8.setColumns(10);
        panel9.add(email);
        panel9.add(tf8);
        panel9.setLayout(new FlowLayout());
        
        JButton ok1 = new JButton("Inserare student");
        ok1.addActionListener(new Stud());
        JButton ok2 = new JButton("Inserare profesor");
        ok2.addActionListener(new Prof());
        JButton ok3 = new JButton("Inserare administrator");
        ok3.addActionListener(new Admin());
		panel10.add(ok1);
		panel10.add(ok2);
		panel10.add(ok3);
		panel10.setLayout(new FlowLayout());
		
		JButton inapoi = new JButton("Inapoi");
		inapoi.addActionListener(new Inapoi());
		inapoi.setForeground(Color.red);
		panel11.add(inapoi);
		panel11.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel p = new JPanel();
		p.add(panel1);
		p.add(panel2);
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
		p.add(panel6);
		p.add(panel7);
		p.add(panel8);
		p.add(panel9);
		p.add(panel10);
		p.add(panel11);
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
	class Stud implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement mySt = null;
				mySt = con.prepareCall("{call insert_utilizator(?, ?, ?,?,?,?,?,?)}");
				mySt.setString(1, tf1.getText());
				mySt.setString(2, tf2.getText());
				mySt.setString(3, tf3.getText());
				mySt.setString(4, tf4.getText());
				mySt.setString(5, tf5.getText());
				mySt.setString(6, tf6.getText());
				mySt.setString(7, tf7.getText());
				mySt.setString(8, tf8.getText());
				mySt.execute();
				new InserareStudent(x,y,nume,prenume,con);
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}
			frame.dispose();

		}

	}
	class Prof implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement mySt = null;
				mySt = con.prepareCall("{call insert_utilizator(?, ?, ?,?,?,?,?,?)}");
				mySt.setString(1, tf1.getText());
				mySt.setString(2, tf2.getText());
				mySt.setString(3, tf3.getText());
				mySt.setString(4, tf4.getText());
				mySt.setString(5, tf5.getText());
				mySt.setString(6, tf6.getText());
				mySt.setString(7, tf7.getText());
				mySt.setString(8, tf8.getText());
				mySt.execute();
				new InserareProf(x,y,nume,prenume,con);
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}
			frame.dispose();

		}

	}
	class Admin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement mySt = null;
				mySt = con.prepareCall("{call insert_utilizator(?, ?, ?,?,?,?,?,?)}");
				mySt.setString(1, tf1.getText());
				mySt.setString(2, tf2.getText());
				mySt.setString(3, tf3.getText());
				mySt.setString(4, tf4.getText());
				mySt.setString(5, tf5.getText());
				mySt.setString(6, tf6.getText());
				mySt.setString(7, tf7.getText());
				mySt.setString(8, tf8.getText());
				mySt.execute();
				new InserareAdmin(x,y,nume,prenume,con);
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}
			frame.dispose();

		}

	}

}
