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

public class Stergere {
	private static JFrame frame;
	Connection con;
	int x;
	int y;
	String nume;
	String prenume;
	String tabela;
	JTextField tf1 = new JTextField("");
	JTextField tf2 = new JTextField("");
	JTextField tf3 = new JTextField("");

	Stergere(int x, int y, String nume, String prenume, String tabela, Connection c) {
		frame = new JFrame("Stergere Tabela");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(800, 300);
		frame.setLocationRelativeTo(null);
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		this.tabela = tabela;
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		JLabel s = new JLabel("Stergere " + tabela);
		s.setFont(new Font("", Font.BOLD, 20));
		panel1.add(s);
		panel1.setLayout(new FlowLayout());

		JLabel nume1 = new JLabel("Nume");
		JLabel prenume1 = new JLabel("Prenume");
		JLabel cnp = new JLabel("CNP");

		tf1.setColumns(10);
		tf2.setColumns(10);
		tf3.setColumns(10);
		panel2.add(nume1);
		panel2.add(tf1);
		panel2.add(prenume1);
		panel2.add(tf2);
		panel2.add(cnp);
		panel2.add(tf3);
		panel2.setLayout(new FlowLayout());

		JButton ok = new JButton("Stergere");
		ok.addActionListener(new Oks());
		panel3.add(ok);
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

	class Oks implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabela.equals("student")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista student");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call delete_student(?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.execute();
							new Succes("Studentul a fost sters");
						} catch (SQLException e1) {
							new Eroare("nu s-a sters");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a sters");
					e1.printStackTrace();
				}

			}
			if (tabela.equals("profesor")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista profesor");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call delete_profesor(?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.execute();
							new Succes("Profesorul a fost sters");
						} catch (SQLException e1) {
							new Eroare("nu s-a sters");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a sters");
					e1.printStackTrace();
				}
			}
			if (tabela.equals("administrator")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista administrator");
					} else {
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
									mySt = con.prepareCall("{call delete_administrator(?, ?, ? , ?,?)}");
									mySt.setString(1, tf1.getText());
									mySt.setString(2, tf2.getText());
									mySt.setString(3, nume);
									mySt.setString(4, prenume);
									mySt.setString(5, tf3.getText());
									mySt.execute();
									new Succes("Administratorul a fost sters");
								} catch (SQLException e1) {
									new Eroare("nu s-a sters");
									e1.printStackTrace();
								}
							}
						} catch (SQLException e1) {
							new Eroare("nu s-a sters");
							e1.printStackTrace();
						}
					}

				} catch (SQLException e1) {
					new Eroare("nu s-a sters");
					e1.printStackTrace();
				}

			}
		}
	}
}
