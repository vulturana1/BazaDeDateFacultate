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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Modificare {
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
	String[] atribut = new String[] { "Atribut", "Adresa", "Email", "Telefon", "Nr contract", "IBAN", "Nr ore",
			"An studiu" };
	JComboBox aleg_atr = new JComboBox(atribut);

	Modificare(int x, int y, String nume, String prenume, String tabela, Connection c) {
		con = c;
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame = new JFrame("Modificare Tabela");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(800, 300);
		frame.setLocationRelativeTo(null);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();

		JLabel s = new JLabel("Modificare " + tabela);
		s.setFont(new Font("", Font.BOLD, 20));
		panel1.add(s);
		panel1.setLayout(new FlowLayout());

		JLabel nume1 = new JLabel("Nume");
		JLabel prenume1 = new JLabel("Prenume");
		JLabel cnp=new JLabel("CNP");

		tf1.setColumns(10);
		tf2.setColumns(10);
		tf4.setColumns(10);
		panel2.add(nume1);
		panel2.add(tf1);
		panel2.add(prenume1);
		panel2.add(tf2);
		panel2.add(cnp);
		panel2.add(tf4);
		panel2.setLayout(new FlowLayout());

		aleg_atr.setSelectedIndex(0);

		tf3.setColumns(10);
		panel3.add(aleg_atr);
		panel3.add(tf3);
		panel3.setLayout(new FlowLayout());

		JButton ok = new JButton("Modificare");
		ok.addActionListener(new Modifica());
		panel4.add(ok);
		panel4.setLayout(new FlowLayout());

		JButton inapoi = new JButton("Inapoi");
		inapoi.addActionListener(new Inapoi());
		inapoi.setForeground(Color.red);
		panel5.add(inapoi);
		panel5.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JPanel p = new JPanel();
		p.add(panel1);
		p.add(panel2);
		p.add(panel3);
		p.add(panel4);
		p.add(panel5);
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

	class Modifica implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (aleg_atr.getSelectedItem().toString().equals("Adresa")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {

							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_adresa(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("Email")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_email(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("Telefon")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_telefon(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("Nr contract")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_contract(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("IBAN")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_iban(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("Nr ore")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_nrore(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}
			if (aleg_atr.getSelectedItem().toString().equals("An studiu")) {
				try {
					CallableStatement mySt1 = null;
					mySt1 = con.prepareCall("{call cauta_id(?, ?)}");
					mySt1.setString(1, tf1.getText());
					mySt1.setString(2, tf2.getText());
					mySt1.execute();
					ResultSet util = mySt1.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					if (util.next() == false) {
						new Eroare("Nu exista utilizator");
					} else {
						try {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call update_an(?, ?, ?,?)}");
							mySt.setString(1, tf1.getText());
							mySt.setString(2, tf2.getText());
							mySt.setString(3, tf3.getText());
							mySt.setString(4, tf4.getText());
							mySt.execute();
							new Succes("A fost modificat cu succes");
						} catch (SQLException e1) {
							new Eroare("nu s-a modificat");
							e1.printStackTrace();
						}
					}
				} catch (SQLException e1) {
					new Eroare("nu s-a modificat");
					e1.printStackTrace();
				}
			}

		}

	}

}
