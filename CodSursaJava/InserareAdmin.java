import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InserareAdmin {
	private static JFrame frame;
	Connection con;
	int x;
	int y;
	String nume;
	String prenume;
	JTextField tf1 = new JTextField("");
	JTextField tf2 = new JTextField("");

	
	InserareAdmin(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame = new JFrame("Inserare Admin");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(800, 300);
		frame.setLocationRelativeTo(null);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JPanel panel5 = new JPanel();
		JPanel panel6=new JPanel();

		JLabel s = new JLabel("Inserare Administrator");
		s.setFont(new Font("", Font.BOLD, 20));
		panel1.add(s);
		panel1.setLayout(new FlowLayout());
		
		JLabel cnp=new JLabel("ID utiliator");
        tf1.setColumns(10);
        panel2.add(cnp);
        panel2.add(tf1);
        panel2.setLayout(new FlowLayout());
        
        JLabel nume1=new JLabel("Super-Administartor");
        tf2.setColumns(10);
        panel3.add(nume1);
        panel3.add(tf2);
        panel3.setLayout(new FlowLayout());
        
        
        JButton ok = new JButton("Inserare");
		ok.addActionListener(new InsAdmin());
		panel5.add(ok);
		panel5.setLayout(new FlowLayout());
		
		JButton inapoi = new JButton("Inapoi");
		inapoi.addActionListener(new Inapoi());
		inapoi.setForeground(Color.red);
		panel6.add(inapoi);
		panel6.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel p = new JPanel();
		p.add(panel1);
		p.add(panel2);
		p.add(panel3);;
		p.add(panel5);
		p.add(panel6);
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
	class InsAdmin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				CallableStatement mySt2 = null;
				mySt2 = con.prepareCall("{call este_super(?, ?)}");
				mySt2.setString(1, nume);
				mySt2.setString(2, prenume);
				mySt2.execute();
				ResultSet util1 = mySt2.getResultSet();
				ResultSetMetaData rsmd1 = util1.getMetaData();
				if (util1.next() == false) {
					new Eroare("Nu esti super administrator");
				} else {
			try {
				CallableStatement mySt = null;
				mySt = con.prepareCall("{call insert_administrator(?, ?, ?,?)}");
				mySt.setString(1, tf1.getText());
				mySt.setString(2, tf2.getText());
				mySt.setString(3, nume);
				mySt.setString(4, prenume);
				mySt.execute();
				new Succes("A fost inserat cu succes");
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}
				}
			} catch (SQLException e1) {
				new Eroare("nu s-a inserat");
				e1.printStackTrace();
			}

		}

	}

}



