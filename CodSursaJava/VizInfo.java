import java.awt.*;
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

public class VizInfo {

	Connection con;
	private static JFrame frame;
	int x;
	int y;
	String nume;
	String prenume;

	VizInfo(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		frame = new JFrame("Vizualizare Informatii");
		this.x = x;
		this.y = y;
		this.nume = nume;
		this.prenume = prenume;
		frame.setLocation(this.x, this.y);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);

		JLabel sal = new JLabel("Vizualizare informatii");
		sal.setFont(new Font("", Font.BOLD, 20));

		JPanel mijloc = new JPanel();
		try {
			CallableStatement mySt = null;
			mySt = con.prepareCall("{call viz_info(?, ?)}");
			mySt.setString(1, nume);
			mySt.setString(2, prenume);
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

			JPanel panel = new JPanel();
			for (String s : inf) {
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
		p.add(jos);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	class BackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new PaginaPrincipalaAdministrator(x, y, nume, prenume, con);
			frame.dispose();
		}
	}

}
