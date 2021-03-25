import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Logare {
	Connection con;
	private static JFrame frame;
	JTextField textNume;
	JTextField textCNP;
	JButton buttonLogin;
	int x;
	int y;

	Logare(Connection c) {
		frame = new JFrame("Login User");
		x = 600;
		y = 300;
		con = c;
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JButton buttonLogin = new JButton("Confirmare");

		buttonLogin.addActionListener(new LoginListener());

		JLabel numeUtil = new JLabel("Nume Prenume");
		JLabel CNP = new JLabel("CNP");
		JLabel login = new JLabel("Logare");
		login.setFont(new Font("", Font.BOLD, 20));

		textNume = new JTextField("");
		textNume.setColumns(10);
		textCNP = new JTextField("");
		textCNP.setColumns(10);

		JPanel panel1 = new JPanel();
		panel1.add(login);
		JPanel panel2 = new JPanel();
		JPanel panel2_1 = new JPanel();
		JPanel panel2_2 = new JPanel();

		panel2_1.add(numeUtil);
		panel2_1.add(textNume);
		panel2_1.setLayout(new FlowLayout());

		panel2_2.add(Box.createHorizontalStrut(30));
		panel2_2.add(CNP);
		panel2_2.add(Box.createHorizontalStrut(20));
		panel2_2.add(textCNP);
		panel2_2.setLayout(new FlowLayout());

		panel2.add(panel2_1);
		panel2.add(panel2_2);
		panel2.setLayout(new GridLayout(0, 1));

		JPanel panel3 = new JPanel();
		panel3.add(buttonLogin);

		JPanel p = new JPanel();
		p.add(panel1);

		p.add(panel2);

		p.add(panel3);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] nume = textNume.getText().split(" ");
			String cnp = textCNP.getText();

			if (nume.length < 2) {
				Eroare err = new Eroare("Nu este nume intreg");
				return;
			}
			try {
				Statement getUtil = con.createStatement();
				getUtil.execute("select nume,prenume,utilizator_id as id from utilizator where nume='" + nume[0]
						+ "' and prenume='" + nume[1] + "' and CNP='" + cnp + "';");
				ResultSet util = getUtil.getResultSet();
				if (util.next() == false) {
					Eroare err = new Eroare("Utilizator inexistent");
					return;
				}

				String id = util.getString("id");
				int id_student = 0;
				if (id != null) {
					int rol = 0;
					ResultSet rol_info = null;
					getUtil.execute("select ID_student as id from student where ID_utilizator='" + id + "';");
					rol_info = getUtil.getResultSet();
					if (rol_info.next() == false) {
						getUtil.execute("select id_profesor as id from profesor where id_utilizator='" + id + "';");
						rol_info = getUtil.getResultSet();
						if (rol_info.next() == false) {
							getUtil.execute(
									"select id_utilizator as id from administrator where id_utilizator='" + id + "';");
							rol_info = getUtil.getResultSet();
							if (rol_info.next() == false) {
								{
									Eroare err = new Eroare("Utilizatorul nu are rol desemnat");
									return;
								}
							} else
								rol = 3;
						} else
							rol = 2;
					} else
						rol = 1;
					switch (rol) {
					case 1: {
						//System.out.println(rol_info.toString());
						ResultSet rs = null;
						getUtil.execute("select ID_student as id from student where ID_utilizator='" + id + "';");
						rs = getUtil.getResultSet();
						rs.next();
					    id_student = rs.getInt(1);
						
						PaginaPrincipalaStudent user = new PaginaPrincipalaStudent(x, y, nume[0], nume[1], cnp, id_student, con);
						break;
					}
					case 2: {
						PaginaPrincipalaProfesor user = new PaginaPrincipalaProfesor(x, y, nume[0], nume[1], con,textCNP.getText());
						break;
					}
					case 3: {
						PaginaPrincipalaAdministrator user = new PaginaPrincipalaAdministrator(x, y, nume[0], nume[1],
								con);
						break;
					}
					}
					frame.dispose();
				}

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		}
	}
}
