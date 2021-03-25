import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;

public class CursuriStud {

	Connection con;
	private static JFrame frmVizualizareCursuri;
	int x;
	int y;
	String nume;
	String prenume;
	String cnp;
	String nume_curs;
	int id_student;

	CursuriStud(int x, int y, String nume, String prenume, String cnp, int id_student, Connection c) {
		con = c;
		frmVizualizareCursuri = new JFrame("Vizualizare Informatii");
		frmVizualizareCursuri.setTitle("Vizualizare cursuri");
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		this.cnp = cnp;
		this.id_student = id_student;
		frmVizualizareCursuri.setLocation(this.x, this.y);
		frmVizualizareCursuri.setDefaultCloseOperation(frmVizualizareCursuri.EXIT_ON_CLOSE);
		frmVizualizareCursuri.setSize(578, 308);
		frmVizualizareCursuri.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Cursuri");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(244, 10, 120, 50);
		frmVizualizareCursuri.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Inscriere curs");
		btnNewButton.setBounds(10, 62, 211, 36);
		frmVizualizareCursuri.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Vizualizare activitati curente");
		btnNewButton_1.setBounds(10, 108, 211, 36);
		btnNewButton_1.addActionListener(new ActivitatiCurenteListener());
		frmVizualizareCursuri.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Descarcare activitati curente");
		btnNewButton_2.setBounds(10, 154, 211, 36);
		btnNewButton_2.addActionListener(new DescarcareActCrListener());
		frmVizualizareCursuri.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Parasire curs");
		btnNewButton_3.setBounds(10, 200, 211, 36);
		frmVizualizareCursuri.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Inapoi");
		btnNewButton_4.setForeground(Color.RED);
		btnNewButton_4.addActionListener(new BackListener());
		btnNewButton_4.setBounds(477, 247, 85, 21);

		frmVizualizareCursuri.getContentPane().add(btnNewButton_4);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(244, 70, 211, 21);
		frmVizualizareCursuri.getContentPane().add(comboBox);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(244, 208, 211, 21);
		frmVizualizareCursuri.getContentPane().add(comboBox_2);

		comboBox.addItem("Alege curs");
		comboBox_2.addItem("Alege curs");

		JButton btnNewButton_5 = new JButton("Vizualizare activitati viitoare");
		btnNewButton_5.setBounds(244, 108, 211, 36);
		btnNewButton_5.addActionListener(new ActivitatiViitoareListener());
		frmVizualizareCursuri.getContentPane().add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("Descarcare activitati viitoare");
		btnNewButton_6.setBounds(244, 154, 211, 36);
		btnNewButton_6.addActionListener(new DescarcareActViitListener());
		frmVizualizareCursuri.getContentPane().add(btnNewButton_6);

		try {
			CallableStatement mySt = null;
			mySt = con.prepareCall("{call cautare_cursuri()}");
			mySt.execute();
			ResultSet util = mySt.getResultSet();
			ResultSetMetaData rsmd = util.getMetaData();
			while (util.next()) {
				Object obj = util.getObject(1);
				String data = obj.toString();
				comboBox.addItem(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {

			String mySt = "select descriere from cursuri, inscriere_curs where id_student='" + id_student
					+ "'  and inscriere_curs.id_curs = cursuri.id_curs group by cursuri.descriere;";
			Statement getUtil = con.createStatement();
			getUtil.execute(mySt);
			ResultSet util = getUtil.getResultSet();
			while (util.next()) {
				Object obj = util.getObject(1);
				String data = obj.toString();
				comboBox_2.addItem(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query;
					Statement getUtil;
					ResultSet util;

					// aleg cursul cu cei mai putini studenti inscrisi la momentul respectiv
					query = "select min(numar_studenti_inscrisi), numar_maxim_studenti, id_curs from cursuri where cursuri.descriere = '"
							+ comboBox.getSelectedItem().toString()
							+ "' and numar_maxim_studenti <> numar_studenti_inscrisi;";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					util.next();
					int nr = util.getInt(1);
					int nr_max = util.getInt(2);
					int id_curs = util.getInt(3);

					// verific daca studentul e deja inscris la cursul respectiv
					query = "select id_inscriere from inscriere_curs where id_curs = '" + id_curs
							+ "' and id_student = '" + id_student + "';";

					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					// daca nu e inscris il inscriu la cursul ales
					if (util.next() == false) {

						if (nr < nr_max) {
							// salvez ora si ziua cursului selectat
							query = "select ora_inceput, zi from calendar where id_curs = '" + id_curs + "'";
							getUtil = con.createStatement();
							getUtil.execute(query);
							util = getUtil.getResultSet();
							util.next();
							String ora = util.getString(1);
							String zi = util.getString(2);

							// caut un alt curs cu aceasi ora si zi
							query = "select id_curs from calendar where ora_inceput = '" + ora + "' and zi='" + zi
									+ "' and id_curs<>'" + id_curs + "';";

							getUtil = con.createStatement();
							getUtil.execute(query);
							util = getUtil.getResultSet();
							// daca nu gasesc un alt curs inscriu studentul
							if (util.next() == false) {
								CallableStatement mySt = null;
								mySt = con.prepareCall("{call inscriere_curs(?, ?, ?)}");
								mySt.setString(1, comboBox.getSelectedItem().toString());
								mySt.setInt(2, id_student);
								mySt.setInt(3, nr);
								mySt.execute();
								Succes succ = new Succes("Operatie realizata cu succes");
							} else {

								query = "select id_curs from calendar where ora_inceput = '" + ora + "' and zi='" + zi
										+ "' and id_curs<>'" + id_curs + "';";

								getUtil = con.createStatement();
								getUtil.execute(query);
								util = getUtil.getResultSet();
								// util.next();
								int i = 1;
								if (util.next() != false) {
									int id_curss = util.getInt(i++);
									// daca gasesc un alt curs verific daca studentul e inscris la cursul gasit ca
									// suprapunere
									query = "select id_inscriere from inscriere_curs where id_curs = '" + id_curss
											+ "' and id_student = '" + id_student + "';";

									getUtil = con.createStatement();
									getUtil.execute(query);
									util = getUtil.getResultSet();
									// daca nu e inscris la un curs care se suprapune il inscriu la cursul ales
									if (util.next() == false) {
										CallableStatement mySt = null;
										mySt = con.prepareCall("{call inscriere_curs(?, ?, ?)}");
										mySt.setString(1, comboBox.getSelectedItem().toString());
										mySt.setInt(2, id_student);
										mySt.setInt(3, nr);
										mySt.execute();
										Succes succ = new Succes("Operatie realizata cu succes");
									} else {
										Eroare err = new Eroare("Activitatea se suprapune");
									}
								}
							}

						} else {
							Eroare err = new Eroare("Numarul maxim de studenti inscrisi la acest curs a fost atins");
						}

					} else {
						Eroare err = new Eroare("Studentul este deja inscris la acest curs");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CallableStatement mySt = null;
					mySt = con.prepareCall("{call parasire_curs(?, ?)}");
					mySt.setString(1, cnp);
					mySt.setString(2, comboBox_2.getSelectedItem().toString());
					mySt.execute();
					Succes succ = new Succes("Operatie realizata cu succes");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		frmVizualizareCursuri.setVisible(true);
	}

	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PaginaPrincipalaStudent pag = new PaginaPrincipalaStudent(x, y, nume, prenume, cnp, id_student, con);
			frmVizualizareCursuri.dispose();
		}
	}

	class DescarcareActCrListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// descarcam activitatile curente
			// ne folosim de un fisier + de procedura vizualizare activitati curente
			try {
				CallableStatement a = null;
				String s = "call vizualizare_activitati_curente_AS_student(?,?)";
				a = con.prepareCall(s);
				a.setString(1, nume);
				a.setInt(2, id_student);
				ResultSet r = null;
				r = a.executeQuery();
				String st;
				String stFinal = "";
				int i = 1;
				while (r.next()) {
					if (i == 1) {

						stFinal = "Studentul: " + nume + " " + prenume;

						stFinal = stFinal + " are activitatile curente:\n";
						i++;
					}
					st = (r.getString(2));
					stFinal = stFinal + st + " la ";
					st = (r.getString(1));
					stFinal = stFinal + st + " cu profesorul: ";
					st = (r.getString(3));
					stFinal = stFinal + st + " ";
					st = (r.getString(4));
					stFinal = stFinal + st + " in ziua de: ";
					st = (r.getString(5));
					stFinal = stFinal + st + " de la ora: ";
					st = (r.getString(6));
					stFinal = stFinal + st + " pana la ora: ";
					st = (r.getString(7));
					stFinal = stFinal + st + " incepand cu data de: ";
					st = (r.getString(8));
					stFinal = stFinal + st + " pana la data de: ";
					st = (r.getString(9));
					stFinal += st + "\n";

				}
				try {
					LocalDateTime time = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
					String s2 = time.format(formatter).toString();
					s2 = s2 + "_";
					s2 = s2 + nume;
					s2 = s2 + "_";
					s2 = s2 + prenume;
					s2 = s2 + "_activitati_curente.txt";
					FileWriter myF = new FileWriter(s2);
					if (stFinal == "")
						myF.write("Nu sunt activitati curente");
					else {
						myF.write(stFinal);
						Succes1 suc = new Succes1("Descarcare realizata cu succes");
					}
					myF.close();
					System.out.println(stFinal);

				} catch (IOException ex) {
					System.out.println("Eroare");
					ex.printStackTrace();
				}

			} catch (Exception e1) {
				Eroare er = new Eroare(" ");
				e1.printStackTrace();
			}
		}
	}

	class DescarcareActViitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// descarcam activitatile curente
			// ne folosim de un fisier + de procedura vizualizare activitati curente
			try {
				CallableStatement a = null;
				String s = "call vizualizare_activitati_viitoare_AS_student(?,?)";
				a = con.prepareCall(s);
				a.setString(1, nume);
				a.setInt(2, id_student);
				ResultSet r = null;
				r = a.executeQuery();
				String st;
				String stFinal = "";
				int i = 1;
				while (r.next()) {
					if (i == 1) {

						stFinal = "Studentul: " + nume + " " + prenume;

						stFinal = stFinal + " are activitatile viitoare:\n";
						i++;
					}
					st = (r.getString(2));
					stFinal = stFinal + st + " la ";
					st = (r.getString(1));
					stFinal = stFinal + st + " cu profesorul: ";
					st = (r.getString(3));
					stFinal = stFinal + st + " ";
					st = (r.getString(4));
					stFinal = stFinal + st + " in ziua de: ";
					st = (r.getString(5));
					stFinal = stFinal + st + " de la ora: ";
					st = (r.getString(6));
					stFinal = stFinal + st + " pana la ora: ";
					st = (r.getString(7));
					stFinal = stFinal + st + " incepand cu data de: ";
					st = (r.getString(8));
					stFinal = stFinal + st + " pana la data de: ";
					st = (r.getString(9));
					stFinal += st + "\n";
				}
				try {
					LocalDateTime time = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
					String s2 = time.format(formatter).toString();
					s2 = s2 + "_";
					s2 = s2 + nume;
					s2 = s2 + "_";
					s2 = s2 + prenume;
					s2 = s2 + "_activitati_viitoare.txt";
					FileWriter myF = new FileWriter(s2);
					if (stFinal == "")
						myF.write("Nu sunt activitati viitoare");
					else {
						myF.write(stFinal);
						Succes1 suc = new Succes1("Descarcare realizata cu succes");
					}
					myF.close();
					System.out.println(stFinal);

				} catch (IOException ex) {
					System.out.println("Eroare");
					ex.printStackTrace();
				}

			} catch (Exception e1) {
				Eroare er = new Eroare(" ");
				e1.printStackTrace();
			}
		}
	}

	class ActivitatiViitoareListener implements ActionListener {
		String[][] result = null;

		public void actionPerformed(ActionEvent e) {
			ResultSet util = null;
			int colCount = 0;
			int rowCount = 0;
			try {

				CallableStatement mySt = null;
				mySt = con.prepareCall("{call vizualizare_activitati_viitoare_AS_student(?, ?)}");
				mySt.setString(1, cnp);
				mySt.setInt(2, id_student);
				mySt.execute();
				util = mySt.getResultSet();
				ResultSetMetaData rsmd = util.getMetaData();
				colCount = rsmd.getColumnCount();
				rowCount = 0;
				result = new String[50][colCount + 1];
				while (util.next()) {
					for (int i = 1; i <= colCount; i++) {
						Object obj = util.getObject(i);
						String data = obj.toString();
						result[rowCount][i - 1] = data;
					}
					rowCount++;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			String[] columnNames = { "Curs", "Activitate", "Nume profesor", "Prenume profesor", "Zi", "Ora inceput",
					"Ora terminare", "Data inceput", "Data terminare" };

			VizualizareTabel activViit = new VizualizareTabel(result, columnNames);
		}
	}

	class ActivitatiCurenteListener implements ActionListener {
		String[][] result = null;

		public void actionPerformed(ActionEvent e) {
			ResultSet util = null;
			int colCount = 0;
			int rowCount = 0;
			try {

				CallableStatement mySt = null;
				mySt = con.prepareCall("{call vizualizare_activitati_curente_AS_student(?, ?)}");
				mySt.setString(1, cnp);
				mySt.setInt(2, id_student);
				mySt.execute();
				util = mySt.getResultSet();
				ResultSetMetaData rsmd = util.getMetaData();
				colCount = rsmd.getColumnCount();
				rowCount = 0;
				result = new String[50][colCount + 1];
				while (util.next()) {
					for (int i = 1; i <= colCount; i++) {
						Object obj = util.getObject(i);
						String data = obj.toString();
						result[rowCount][i - 1] = data;
					}
					rowCount++;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			String[] columnNames = { "Curs", "Activitate", "Nume profesor", "Prenume profesor", "Zi", "Ora inceput",
					"Ora terminare", "Data inceput", "Data terminare" };

			VizualizareTabel activCurente = new VizualizareTabel(result, columnNames);

		}
	}
}
