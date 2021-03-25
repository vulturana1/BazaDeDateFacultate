import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.*;

public class PaginaPrincipalaStudent {
	Connection con;
	private static JFrame frame;
	int x;
	int y;
	String nume;
	String prenume;
	String cnp;
	int id_student;
	// JTextArea orar;

	PaginaPrincipalaStudent(int x, int y, String nume, String prenume, String cnp, int id_student, Connection c) {
		con = c;
		frame = new JFrame("Pagina " + nume + " " + prenume);
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		this.cnp = cnp;
		this.id_student = id_student;
		frame.setLocation(this.x, this.y);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(620, 339);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Bine ai venit " + nume + " " + prenume);
		lblNewLabel.setBounds(20, 10, 337, 37);
		lblNewLabel.setFont(new Font("", Font.BOLD, 20));
		frame.getContentPane().add(lblNewLabel);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		JLabel date = new JLabel("Data: " + dtf.format(now));
		date.setFont(new Font("Tahoma", Font.PLAIN, 11));
		JLabel orar_mess = new JLabel("Orarul pentru astazi:");
		orar_mess.setFont(new Font("Tahoma", Font.PLAIN, 11));
		date.setBounds(20, 43, 147, 37);
		orar_mess.setBounds(177, 43, 337, 37);
		frame.getContentPane().add(date);
		frame.getContentPane().add(orar_mess);

		JButton btnNewButton = new JButton("Vizualizare informatii");
		btnNewButton.setBounds(10, 90, 147, 28);
		btnNewButton.addActionListener(new InfoListener());
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cursuri");
		btnNewButton_1.setBounds(10, 139, 147, 28);
		btnNewButton_1.addActionListener(new CursListener());
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Grupuri de studiu");
		btnNewButton_2.setBounds(10, 189, 147, 28);
		btnNewButton_2.addActionListener(new GrupListener());
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Note");
		btnNewButton_3.setBounds(10, 233, 147, 28);
		btnNewButton_3.addActionListener(new NoteListener());
		frame.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Deconectare");
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_4.setForeground(Color.RED);
		btnNewButton_4.setBounds(488, 264, 108, 28);
		btnNewButton_4.addActionListener(new DeconectListener());
		frame.getContentPane().add(btnNewButton_4);

		JPanel panel = new JPanel();
		
		panel.setBounds(177, 90, 419, 171);
		frame.getContentPane().add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		try {
			CallableStatement mySt = null;
			mySt = con.prepareCall("{call vizualizare_activitati_zi_curenta_AS_student(?, ?)}");
			mySt.setString(1, cnp);
			mySt.setInt(2, id_student);
			mySt.execute();
			ResultSet util = mySt.getResultSet();
			ResultSetMetaData rsmd = util.getMetaData();
			int colCount = rsmd.getColumnCount();
			int rowCount = 0;
			ArrayList<String> inf = new ArrayList<>();
			while (util.next()) {
				for (int i = 1; i <= colCount; i++) {
					Object obj = util.getObject(i);
					if (obj == null)
						System.out.print(" ");
					else {
						String data = obj.toString();
						data = rsmd.getColumnName(i) + ": " + data;
						inf.add(data);
					}
				}
				System.out.println();
				rowCount++;
			}
			panel.setLayout(new GridLayout(rowCount, colCount, 0, 0));
			for(String s: inf) {
				JTextPane text = new JTextPane();
				text.setFont(new Font("Tahoma", Font.PLAIN, 8));
				text.setText(s);
				text.setEditable(false);
				panel.add(text);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		frame.setVisible(true);
	}

	class DeconectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Logare log = new Logare(con);
			frame.dispose();
		}
	}

	class NoteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			NoteStud n = new NoteStud(x, y, nume, prenume, cnp, id_student, con);
			frame.dispose();
		}
	}

	class InfoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			VizualizareInfo inf = new VizualizareInfo(x, y, nume, prenume, cnp, id_student, con);
			frame.dispose();
		}
	}

	class CursListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CursuriStud curs = new CursuriStud(x, y, nume, prenume, cnp, id_student, con);
			frame.dispose();
		}
	}

	class GrupListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GrupuriDeStudiu gr = new GrupuriDeStudiu(x, y, nume, prenume, cnp, id_student, con);
			frame.dispose();
		}
	}
}
