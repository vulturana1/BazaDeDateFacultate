import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

//import VizInfo.BackListener;

public class VizTabela {
	Connection con;
	private static JFrame frame;
	int x;
	int y;
	String nume;
	String prenume;
	String tabela;

	VizTabela(int x, int y, String nume, String prenume, String tabela, Connection c) {
		con = c;
		frame = new JFrame("Vizualizare Tabela");
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame.setLocation(this.x, this.y);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(500, 1000);

		JLabel sal = new JLabel("Vizualizare Tabela:" + tabela);
		sal.setFont(new Font("", Font.BOLD, 20));

		JPanel mijloc = new JPanel();
		if (tabela.equals("student")) {
			try {
				CallableStatement inf = null;
				inf = con.prepareCall("{call filtare_studenti()}");
				inf.execute();
				ResultSet util = inf.getResultSet();
				ResultSetMetaData rsmd = util.getMetaData();
				int colCount = rsmd.getColumnCount();
				int rowCount = 0;
				ArrayList<String> info = new ArrayList<>();
				while (util.next()) {
					for (int i = 1; i <= colCount; i++) {
						Object obj = util.getObject(i);
						if (obj == null)
							System.out.print(" ");
						else {
							String data = obj.toString();
							data = rsmd.getColumnName(i) + ": " + data;
							info.add(data);
						}
					}
					System.out.println();
					rowCount++;
				}

				JPanel panel = new JPanel();
				for (String s : info) {
					JTextPane text = new JTextPane();
					text.setText(s);
					text.setEditable(false);
					panel.add(Box.createRigidArea(new Dimension(10, 0)));
					panel.add(text);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				}
				mijloc.setLayout(new GridLayout(1, 2));
				mijloc.add(panel);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			JButton inapoi = new JButton("Inapoi");
			inapoi.setForeground(Color.red);
			inapoi.addActionListener(new Inapoi());

			JPanel sus = new JPanel();
			sus.add(sal);
			sus.setLayout(new FlowLayout(FlowLayout.CENTER));

			JPanel jos = new JPanel();
			jos.add(inapoi);
			jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JPanel p = new JPanel();
			p.add(sus);
			p.add(mijloc);
			p.add(jos);
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

			frame.setContentPane(p);
			frame.setVisible(true);
		}
		if (tabela.equals("profesor")) {
			try {
				CallableStatement inf = null;
				inf = con.prepareCall("{call filtare_profesori()}");
				inf.execute();
				ResultSet util = inf.getResultSet();
				ResultSetMetaData rsmd = util.getMetaData();
				int colCount = rsmd.getColumnCount();
				int rowCount = 0;
				ArrayList<String> info = new ArrayList<>();
				while (util.next()) {
					for (int i = 1; i <= colCount; i++) {
						Object obj = util.getObject(i);
						if (obj == null)
							System.out.print(" ");
						else {
							String data = obj.toString();
							data = rsmd.getColumnName(i) + ": " + data;
							info.add(data);
						}
					}
					System.out.println();
					rowCount++;
				}

				JPanel panel = new JPanel();
				for (String s : info) {
					JTextPane text = new JTextPane();
					text.setText(s);
					text.setEditable(false);
					panel.add(Box.createRigidArea(new Dimension(10, 0)));
					panel.add(text);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				}
				mijloc.setLayout(new GridLayout(1, 2));
				mijloc.add(panel);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			JButton inapoi = new JButton("Inapoi");
			inapoi.setForeground(Color.red);
			inapoi.addActionListener(new Inapoi());

			JPanel sus = new JPanel();
			sus.add(sal);
			sus.setLayout(new FlowLayout(FlowLayout.CENTER));

			JPanel jos = new JPanel();
			jos.add(inapoi);
			jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JPanel p = new JPanel();
			p.add(sus);
			p.add(mijloc);
			p.add(jos);
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

			frame.setContentPane(p);
			frame.setVisible(true);
		}

		if (tabela.equals("administrator")) {
			try {
				CallableStatement inf = null;
				inf = con.prepareCall("{call filtare_administratori()}");
				inf.execute();
				ResultSet util = inf.getResultSet();
				ResultSetMetaData rsmd = util.getMetaData();
				int colCount = rsmd.getColumnCount();
				int rowCount = 0;
				ArrayList<String> info = new ArrayList<>();
				while (util.next()) {
					for (int i = 1; i <= colCount; i++) {
						Object obj = util.getObject(i);
						if (obj == null)
							System.out.print(" ");
						else {
							String data = obj.toString();
							data = rsmd.getColumnName(i) + ": " + data;
							info.add(data);
						}
					}
					// System.out.println();
					rowCount++;
				}

				JPanel panel = new JPanel();
				for (String s : info) {
					JTextPane text = new JTextPane();
					text.setText(s);
					text.setEditable(false);
					panel.add(Box.createRigidArea(new Dimension(10, 0)));
					panel.add(text);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				}
				mijloc.setLayout(new GridLayout(1, 2));
				mijloc.add(panel);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			JButton inapoi = new JButton("Inapoi");
			inapoi.setForeground(Color.red);
			inapoi.addActionListener(new Inapoi());

			JPanel sus = new JPanel();
			sus.add(sal);
			sus.setLayout(new FlowLayout(FlowLayout.CENTER));

			JPanel jos = new JPanel();
			jos.add(inapoi);
			jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

			JPanel p = new JPanel();
			p.add(sus);
			p.add(mijloc);
			p.add(jos);
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

			frame.setContentPane(p);
			frame.setVisible(true);
		}
	}

	class Inapoi implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PaginaPrincipalaAdministrator pag = new PaginaPrincipalaAdministrator(x, y, nume, prenume, con);
			frame.dispose();
		}
	}

}
