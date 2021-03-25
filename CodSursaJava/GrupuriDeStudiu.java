import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class GrupuriDeStudiu {

	Connection con;
	private static JFrame frmGrupuri;
	int x;
	int y;
	String nume;
	String prenume;
	String cnp;
	private JTextField txtIntroducetiUnMesaj;
	int id_student;
	private JComboBox c;

	GrupuriDeStudiu(int x, int y, String nume, String prenume, String cnp, int id_student, Connection c) {
		con = c;
		frmGrupuri = new JFrame("Vizualizare Informatii");
		frmGrupuri.setTitle("Grupuri");
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		this.cnp = cnp;
		this.id_student = id_student;
		frmGrupuri.setLocation(this.x, this.y);
		frmGrupuri.setDefaultCloseOperation(frmGrupuri.EXIT_ON_CLOSE);
		frmGrupuri.setSize(520, 349);
		frmGrupuri.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Grupuri de studiu");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(147, 20, 192, 37);
		frmGrupuri.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Inscriere grup");
		btnNewButton.setBounds(33, 72, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton);

		JButton sugestii = new JButton("Sugestie membrii");
		sugestii.setBounds(338, 103, 147, 21);
		frmGrupuri.getContentPane().add(sugestii);

		JButton btnNewButton_1 = new JButton("Membrii");
		btnNewButton_1.setBounds(33, 103, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Adaugare activitate");
		btnNewButton_2.setBounds(33, 134, 147, 21);

		frmGrupuri.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Parasire grup");
		btnNewButton_3.setBounds(33, 165, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton_3);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(190, 72, 138, 21);
		frmGrupuri.getContentPane().add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(190, 165, 138, 21);
		frmGrupuri.getContentPane().add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(190, 103, 138, 21);
		frmGrupuri.getContentPane().add(comboBox_2);

		JButton btnNewButton_4 = new JButton("Inapoi");
		btnNewButton_4.setForeground(Color.RED);
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_4.setBounds(411, 289, 85, 21);
		btnNewButton_4.addActionListener(new BackListener());
		frmGrupuri.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Scrie mesaj");
		btnNewButton_5.setBounds(33, 196, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton_5);

		txtIntroducetiUnMesaj = new JTextField();
		txtIntroducetiUnMesaj.setText("Introduceti un mesaj");
		txtIntroducetiUnMesaj.setBounds(338, 198, 158, 19);
		frmGrupuri.getContentPane().add(txtIntroducetiUnMesaj);
		txtIntroducetiUnMesaj.setColumns(10);

		JButton btnNewButton_6 = new JButton("Afiseaza mesaje");

		btnNewButton_6.setBounds(33, 227, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton_6);

		comboBox.addItem("Alege grup");
		comboBox_1.addItem("Alege grup");
		comboBox_2.addItem("Alege grup");

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(190, 196, 138, 21);
		comboBox_3.addItem("Alege grup");
		frmGrupuri.getContentPane().add(comboBox_3);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(190, 227, 138, 21);
		comboBox_4.addItem("Alege grup");
		frmGrupuri.getContentPane().add(comboBox_4);

		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(190, 134, 138, 21);
		comboBox_5.addItem("Alege grup");
		frmGrupuri.getContentPane().add(comboBox_5);

		JButton btnNewButton_7 = new JButton("Sugestii pentru activitati");
		btnNewButton_7.setBounds(338, 134, 158, 21);
		frmGrupuri.getContentPane().add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("Inscriere activitate");
		btnNewButton_8.setBounds(33, 258, 147, 21);
		frmGrupuri.getContentPane().add(btnNewButton_8);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setBounds(190, 258, 138, 21);
		comboBox_6.addItem("Alege grup");
		frmGrupuri.getContentPane().add(comboBox_6);

		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setBounds(338, 258, 158, 21);
		comboBox_7.addItem("Alege activitate");
		frmGrupuri.getContentPane().add(comboBox_7);

		btnNewButton_7.addActionListener(new SugestiiListener());

		try {
			CallableStatement mySt = null;
			mySt = con.prepareCall("{call cautare_grupuri()}");
			mySt.execute();
			ResultSet util = mySt.getResultSet();
			ResultSetMetaData rsmd = util.getMetaData();
			while (util.next()) {
				Object obj = util.getObject(1);
				String data = obj.toString();
				comboBox.addItem(data);
				comboBox_1.addItem(data);
				comboBox_2.addItem(data);
				comboBox_3.addItem(data);
				comboBox_4.addItem(data);
				comboBox_5.addItem(data);
				comboBox_6.addItem(data);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		comboBox_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_6.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					ResultSet util = getUtil.getResultSet();
					util.next();

					int id_gr = util.getInt(1);
					CallableStatement mySt = null;
					mySt = con.prepareCall("{call cautare_activitati(?)}");
					mySt.setInt(1, id_gr);
					mySt.execute();
					util = mySt.getResultSet();
					ResultSetMetaData rsmd = util.getMetaData();
					while (util.next()) {
						Object obj = util.getObject(1);
						String data = obj.toString();
						comboBox_7.addItem(data);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_6.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					ResultSet util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
					} else {
						query = "select id_activitate from activitate where denumire = '"
								+ comboBox_7.getSelectedItem().toString() + "';";
						getUtil = con.createStatement();
						getUtil.execute(query);
						util = getUtil.getResultSet();
						util.next();
						int id_act = util.getInt(1);

						CallableStatement mySt = null;
						mySt = con.prepareCall("{call inscriere_activitate_grup(?)}");
						mySt.setInt(1, id_act);
						mySt.execute();
						Succes succ = new Succes("Operatie realizata cu succes");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		this.c = comboBox_5;

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select materie_id from grupa_studiu where nume_grup = '"
							+ comboBox.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					ResultSet util = getUtil.getResultSet();
					util.next();
					int id_curs = util.getInt(1);

					query = "select id_curs from inscriere_curs where inscriere_curs.id_student = '" + id_student
							+ "' and inscriere_curs.id_curs = '" + id_curs + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se poate inscrie in acest grup");
					} else {
						query = "select id_grupa from grupa_studiu where nume_grup = '"
								+ comboBox.getSelectedItem().toString() + "';";
						getUtil = con.createStatement();
						getUtil.execute(query);
						util = getUtil.getResultSet();
						util.next();
						int id_grup = util.getInt(1);
						
						query = "select id_student from membrii_grupa where id_grupa = '" + id_grup +  "';";
						getUtil = con.createStatement();
						getUtil.execute(query);
						util = getUtil.getResultSet();
						if(util.next() == false) {
							CallableStatement mySt = null;
							mySt = con.prepareCall("{call inscriere_grup(?, ?)}");
							mySt.setString(1, comboBox.getSelectedItem().toString());
							mySt.setString(2, cnp);
							mySt.execute();
							Succes succ = new Succes("Operatie realizata cu succes");
						}
						else {
							Eroare err = new Eroare("Studentul este deja inscris in acest grup");
						}
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		sugestii.addActionListener(new ActionListener() {
			String[][] result = null;

			public void actionPerformed(ActionEvent e) {
				ResultSet util = null;
				int colCount = 0;
				int rowCount = 0;
				int ok = 1;
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_2.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
						ok = 0;
					} else {

						CallableStatement mySt = null;
						mySt = con.prepareCall("{call sugestii_grup(?)}");
						mySt.setString(1, comboBox_2.getSelectedItem().toString());
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

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (ok == 1) {
					String[] columnNames = { "Nume", "Prenume" };
					VizMembriiGrup membrii = new VizMembriiGrup(result, columnNames);
				}
				// frmGrupuri.dispose();
			}
		});

		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_1.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					ResultSet util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
					} else {
						CallableStatement mySt = null;
						mySt = con.prepareCall("{call parasire_grup(?, ?)}");
						mySt.setInt(1, id_student);
						mySt.setString(2, comboBox_1.getSelectedItem().toString());
						mySt.execute();
						Succes succ = new Succes("Operatie realizata cu succes");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_3.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					ResultSet util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
					} else {
						CallableStatement mySt = null;
						mySt = con.prepareCall("{call scrie_mesaje(?, ?, ?)}");
						mySt.setString(1, txtIntroducetiUnMesaj.getText());

						mySt.setInt(2, id_student);
						mySt.setString(3, comboBox_3.getSelectedItem().toString());

						mySt.execute();
						Succes succ = new Succes("Operatie realizata cu succes");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnNewButton_6.addActionListener(new ActionListener() {
			String[][] result = null;

			public void actionPerformed(ActionEvent e) {
				ResultSet util = null;
				int colCount = 0;
				int rowCount = 0;
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_4.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
					} else {
						CallableStatement mySt = null;
						mySt = con.prepareCall("{call afiseaza_mesaje(?)}");
						mySt.setString(1, comboBox_4.getSelectedItem().toString());
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
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				String[] columnNames = { "Nume", "Prenume", "Mesaj" };
				VizualizareTabel mesaje = new VizualizareTabel(result, columnNames);
			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {
			String[][] result = null;

			public void actionPerformed(ActionEvent e) {
				ResultSet util = null;
				int colCount = 0;
				int rowCount = 0;
				int ok = 1;
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_2.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
						ok = 0;
					} else {

						CallableStatement mySt = null;
						mySt = con.prepareCall("{call vizualizare_membrii_grup(?)}");
						mySt.setString(1, comboBox_2.getSelectedItem().toString());
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

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (ok == 1) {
					String[] columnNames = { "Nume", "Prenume" };
					VizMembriiGrup membrii = new VizMembriiGrup(result, columnNames);
				}
				// frmGrupuri.dispose();
			}
		});

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet util = null;
				try {
					String query = "select id_grupa from grupa_studiu where nume_grup = '"
							+ comboBox_5.getSelectedItem().toString() + "';";
					Statement getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();
					util.next();
					int id_gr = util.getInt(1);

					query = "select id_membru from membrii_grupa where id_grupa = '" + id_gr + "' and id_student = '"
							+ id_student + "';";
					getUtil = con.createStatement();
					getUtil.execute(query);
					util = getUtil.getResultSet();

					if (util.next() == false) {
						Eroare err = new Eroare("Studentul nu se afla in acest grup");
					} else {

						AdaugareActivitati act = new AdaugareActivitati(x, y, nume, prenume, cnp, id_student, con,
								comboBox_5.getSelectedItem().toString());
						frmGrupuri.dispose();

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		frmGrupuri.setVisible(true);
	}

	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PaginaPrincipalaStudent pag = new PaginaPrincipalaStudent(x, y, nume, prenume, cnp, id_student, con);
			frmGrupuri.dispose();
		}
	}

	class SugestiiListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s = "call vizualizare_membrii_grup(?)";
			CallableStatement a = null;
			int sem = 0;
			String[] cnp = new String[100];
			String[] ore = new String[100];
			String[] zi = new String[100];
			int[] id = new int[100];
			int indice1 = 0;
			int indice2 = 0;
			int indice3 = 0;
			int indice4 = 0;
			try {
				a = con.prepareCall(s);
				a.setString(1, c.getSelectedItem().toString());
				ResultSet r = null;
				r = a.executeQuery();
				while (r.next()) {
					String sel = "call viz_cnp(?,?)";
					CallableStatement a1 = null;
					try {
						a1 = con.prepareCall(sel);
						a1.setString(1, r.getString(1));
						a1.setString(2, r.getString(2));
						ResultSet r1 = null;
						r1 = a1.executeQuery();
						while (r1.next())
							cnp[indice1++] = r1.getString(1);
						String sel2 = "call viz_id(?,?)";
						CallableStatement a2 = null;
						try {
							a2 = con.prepareCall(sel2);
							a2.setString(1, r.getString(1));
							a2.setString(2, r.getString(2));
							ResultSet r2 = null;
							r2 = a2.executeQuery();
							while (r2.next())
								id[indice2++] = r2.getInt(1);
							sem = 1;
						} catch (Exception ex) {
							Eroare er = new Eroare("Nu s-au gasit datele cerute");
							ex.printStackTrace();
						}
					} catch (Exception ex) {
						Eroare er = new Eroare("Nu s-au gasit datele cerute");
						ex.printStackTrace();
					}
				}

			} catch (Exception ex) {
				Eroare er = new Eroare("Nu s-au gasit datele cerute");
				ex.printStackTrace();
			}
			if (sem == 0) {
				Eroare err;
				err = new Eroare("Nu sunt studenti in acest grup");
			} else {
				// luam pe rand studentii dupa cnp si id si verificam activitatile
				String selO = "call viz_ore(?,?)";
				String selD = "call viz_zile(?,?)";
				for (int i = 0; i < indice1; i++) {
					try {
						CallableStatement aO = null;
						aO = con.prepareCall(selO);
						aO.setString(1, cnp[i]);
						aO.setInt(2, id[i]);
						ResultSet rO = null;
						rO = aO.executeQuery();
						while (rO.next()) {
							ore[indice3++] = rO.getString(1);
						}

						CallableStatement aD = null;
						aD = con.prepareCall(selD);
						aD.setString(1, cnp[i]);
						aD.setInt(2, id[i]);
						ResultSet rD = null;
						rD = aD.executeQuery();
						while (rD.next()) {
							zi[indice4++] = rD.getString(1);
						}
					} catch (Exception ex) {
						Eroare er = new Eroare("Nu s-au gasit datele cerute");
						ex.printStackTrace();
					}
				}
			}
			int[][] orar = new int[7][7];
			for (int i = 0; i < indice3; i++) {
				int linie = -1, coloana = -1;
				if (zi[i].equals("Monday"))
					coloana = 0;
				if (zi[i].equals("Tuesday"))
					coloana = 1;
				if (zi[i].equals("Wednesday"))
					coloana = 2;
				if (zi[i].equals("Thursday"))
					coloana = 3;
				if (zi[i].equals("Friday"))
					coloana = 4;
				if (zi[i].equals("Saturday"))
					coloana = 5;
				if (zi[i].equals("Sunday"))
					coloana = 6;
				if (ore[i].equals("08:00"))
					linie = 0;
				if (ore[i].equals("10:00"))
					linie = 1;
				if (ore[i].equals("12:00"))
					linie = 2;
				if (ore[i].equals("14:00"))
					linie = 3;
				if (ore[i].equals("16:00"))
					linie = 4;
				if (ore[i].equals("18:00"))
					linie = 5;
				if (ore[i].equals("20:00"))
					linie = 6;
				if (linie != -1 && coloana != -1)
					orar[linie][coloana] = 1;

			}
			String[][] ferestre = new String[7][8];
			String[] col = new String[8];
			col[0] = "Ore";
			for (int i = 0; i <= 6; i++) {
				if (i == 0)
					col[i + 1] = "Luni: ";
				if (i == 1)
					col[i + 1] = "Marti: ";
				if (i == 2)
					col[i + 1] = "Miercuri: ";
				if (i == 3)
					col[i + 1] = "Joi: ";
				if (i == 4)
					col[i + 1] = "Vineri: ";
				if (i == 5)
					col[i + 1] = "Sambata: ";
				if (i == 6)
					col[i + 1] = "Duminica: ";
				ferestre[0][0] = "08:00\n";

				ferestre[1][0] = "10:00\n";

				ferestre[2][0] = "12:00\n";

				ferestre[3][0] = "14:00\n";

				ferestre[4][0] = "16:00\n";

				ferestre[5][0] = "18:00\n";

				ferestre[6][0] = "20:00\n";

			}
			for (int i = 0; i < 7; i++)
				for (int j = 0; j < 7; j++) {
					if (orar[i][j] == 1)
						ferestre[i][j + 1] = "NU SE POATE";
					else
						ferestre[i][j + 1] = "SE POATE";

				}
			VizualizareTabel t = new VizualizareTabel(ferestre, col);

		}
	}
}
