import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;

public class NoteStud {

	Connection con;
	private static JFrame frame;
	int x;
	int y;
	String nume;
	String prenume;
	String cnp;
	int id_student;

	NoteStud(int x, int y, String nume, String prenume, String cnp, int id_student, Connection c) {
		con = c;
		frame = new JFrame("Vizualizare Informatii");
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		this.cnp = cnp;
		this.id_student = id_student;
		frame.setLocation(this.x, this.y);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(700, 300);

		JLabel sal = new JLabel("Note");
		sal.setFont(new Font("", Font.BOLD, 20));

		JPanel mijloc = new JPanel();
		JPanel panel = new JPanel();

		try {
			CallableStatement mySt = null;
			mySt = con.prepareCall("{call vizualizare_note(?)}");
			mySt.setString(1, cnp);
			mySt.execute();
			ResultSet util = mySt.getResultSet();
			ResultSetMetaData rsmd = util.getMetaData();
			int colCount = rsmd.getColumnCount();
			int rowCount = 0;
			ArrayList<String> inf = new ArrayList<>();
			while (util.next()) {

				for (int i = 1; i <= colCount; i++) {
					Object obj = util.getObject(i);
					if (obj == null) {
						String data = " ";
						inf.add(data);
					} else {
						String data = obj.toString();
						data = rsmd.getColumnName(i) + ": " + data;
						inf.add(data);
					}
				}
				System.out.println();

				rowCount++;
			}

			panel.setLayout(new GridLayout(rowCount, 5));
			for (int i = 0; i < inf.size() - 1; i = i + 5) {
				JTextPane text1 = new JTextPane();
				text1.setText(inf.get(i));
				text1.setEditable(false);
				panel.add(text1);
				JTextPane text2 = new JTextPane();
				text2.setText(inf.get(i + 1));
				text1.setEditable(false);
				panel.add(text2);
				JTextPane text3 = new JTextPane();
				text3.setText(inf.get(i + 2));
				text3.setEditable(false);
				panel.add(text3);
				JTextPane text4 = new JTextPane();
				text4.setText(inf.get(i + 3));
				text4.setEditable(false);
				panel.add(text4);
				JTextPane text5 = new JTextPane();
				text5.setText(inf.get(i + 4));
				text5.setEditable(false);
				panel.add(text5);
			}
			mijloc.add(panel);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JButton inapoi = new JButton("Inapoi");
		inapoi.setForeground(Color.red);
		inapoi.addActionListener(new BackListener());

		JPanel sus = new JPanel();
		sus.add(sal);
		sus.setLayout(new FlowLayout(FlowLayout.CENTER));

		JPanel jos = new JPanel();
		jos.add(inapoi);
		jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JPanel p = new JPanel();
		p.add(sus);
		p.add(mijloc);
		mijloc.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		mijloc.add(scrollPane);

		scrollPane.setViewportView(panel);
		p.add(jos);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PaginaPrincipalaStudent pag = new PaginaPrincipalaStudent(x, y, nume, prenume, cnp, id_student, con);
			frame.dispose();
		}
	}
}
