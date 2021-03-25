import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AdaugareActivitati {

	Connection con;
	private static JFrame frmGrupuri;
	int x;
	int y;
	String nume;
	String prenume;
	String cnp;
	String grup;
	String prof;
	int id_student;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	AdaugareActivitati(int x, int y, String nume, String prenume, String cnp, int id_student, Connection c, String grup) {
		con = c;
		frmGrupuri = new JFrame("Adaugare Activitati");
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		this.cnp = cnp;
		this.id_student = id_student;
		this.grup = grup;
		frmGrupuri.setLocation(this.x, this.y);
		frmGrupuri.setDefaultCloseOperation(frmGrupuri.EXIT_ON_CLOSE);
		frmGrupuri.setSize(392, 297);
		frmGrupuri.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Denumire:");
		lblNewLabel.setBounds(10, 44, 77, 28);
		frmGrupuri.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Data (YYYY-MM-DD):");
		lblNewLabel_1.setBounds(10, 82, 152, 27);
		frmGrupuri.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Ora (HH:MM): ");
		lblNewLabel_2.setBounds(10, 119, 179, 28);
		frmGrupuri.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Numar minim participanti:");
		lblNewLabel_3.setBounds(10, 153, 179, 28);
		frmGrupuri.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Durata expirare:");
		lblNewLabel_4.setBounds(10, 191, 115, 28);
		frmGrupuri.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Adaugare");
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setBounds(10, 229, 115, 21);
		frmGrupuri.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Inapoi");
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(283, 229, 85, 21);
		btnNewButton_1.addActionListener(new BackListener());
		frmGrupuri.getContentPane().add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(231, 49, 96, 19);
		frmGrupuri.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(231, 86, 96, 19);
		frmGrupuri.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(231, 124, 96, 19);
		frmGrupuri.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(231, 158, 96, 19);
		frmGrupuri.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(231, 196, 96, 19);
		frmGrupuri.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					prof="nu";

					JFrame frame2=new JFrame();
					frame2.setDefaultCloseOperation(frame2.DISPOSE_ON_CLOSE);
					frame2.setLocation(x+10, y+10);
					frame2.getContentPane().setLayout(null);
					frame2.setSize(430, 170);
					Statement getUtil = con.createStatement();

					JLabel titlu=new JLabel("Doriti sa fie implicat si un profesor la activitate?");
					titlu.setBounds(40, 10, 400, 28);
					titlu.setFont(new Font("", Font.BOLD, 15));
					JComboBox profesori=new JComboBox();
					profesori.setBounds(140, 52, 120, 20);
					String query="select nume,prenume\r\n"
							+ "from utilizator    \r\n"
							+ "where utilizator_ID in(\r\n"
							+ "select ID_utilizator\r\n"
							+ "from profesor\r\n"
							+ "inner join (select curs_profesor.ID_profesor \r\n"
							+ "from curs_profesor \r\n"
							+ "inner join cursuri on (cursuri.descriere='"+grup
							+"' and cursuri.ID_curs=curs_profesor.ID_curs)) as x on profesor.ID_profesor=x.ID_profesor)";
					getUtil.execute(query);
					ResultSet util=getUtil.getResultSet();
					while(util.next()) {
						String nume= util.getObject(1).toString()+" "+util.getObject(2).toString();
						profesori.addItem(nume);
					}
					JButton da=new JButton("Da");
					da.setBounds(140, 90, 50, 20);
					da.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							prof=profesori.getSelectedItem().toString();
							frame2.dispose();
						}
					});
					JButton nu=new JButton("Nu");
					nu.setBounds(210, 90, 50, 20);
					nu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							frame2.dispose();
						}
					});

					frame2.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent w) {
							try {
								String query = "select id_grupa from grupa_studiu where nume_grup = '" + grup + "';";
								getUtil.execute(query);
								ResultSet util = getUtil.getResultSet();
								util.next();
								int id_gr = util.getInt(1);

								CallableStatement mySt = null;
								mySt = con.prepareCall("{call adaugare_activitate_grup(?, ?, ?, ?, ?, ?, ?)}");
								mySt.setInt(1, id_gr);
								mySt.setString(2, textField.getText());
								mySt.setString(3, textField_1.getText());
								mySt.setString(4, textField_2.getText());
								mySt.setInt(5, Integer.parseInt(textField_3.getText()));
								mySt.setInt(6, Integer.parseInt(textField_4.getText()));
								mySt.setString(7, prof);
								mySt.execute();
								Succes succ = new Succes("Activitate adaugata cu succes");
							} catch (SQLException e) {
								e.printStackTrace();
							}

						}
					});

					frame2.add(titlu);
					frame2.add(profesori);
					frame2.add(da);
					frame2.add(nu);

					frame2.setResizable(false);
					frame2.setVisible(true);

					System.out.println(grup);


				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		frmGrupuri.setVisible(true);
	}

	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GrupuriDeStudiu pag = new GrupuriDeStudiu(x, y, nume, prenume, cnp, id_student, con);
			frmGrupuri.dispose();
		}
	}
}
