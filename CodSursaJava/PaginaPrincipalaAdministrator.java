import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

public class PaginaPrincipalaAdministrator {
	Connection con;
	int x;
	int y;
	String nume;
	String prenume;

	private static JFrame frame;

	String[] tabela = new String[] { "Nume tabela", "student", "profesor", "administrator" };

	JComboBox aleg_tab = new JComboBox(tabela);
	// aleg_tab.setSelectedIndex(0);

	PaginaPrincipalaAdministrator(int x, int y, String nume, String prenume, Connection c) {
		con = c;
		frame = new JFrame("Pagina " + nume + " " + prenume);
		this.x = x - 10;
		this.y = y + 10;
		this.nume = nume;
		this.prenume = prenume;
		frame.setLocation(this.x, this.y);
		//frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);

		JLabel sal = new JLabel("Bine ai venit " + nume + " " + prenume);
		sal.setFont(new Font("", Font.BOLD, 20));
		// JLabel data=new JLabel("data:");

		JButton info = new JButton("Vizualizare info");
		JButton ins = new JButton("Insereaza utilizator");
		JButton uti = new JButton("Cauta utilizator");
		// info.setPreferredSize(new Dimension(26,100));
		JButton viz = new JButton("Vizualizare tabela");
		JButton mod = new JButton("Modificare tabela");
		JButton ste = new JButton("Stergere tabela");
		JButton asig = new JButton("Asignare profesor");
		JButton curs = new JButton("Cursuri");
		// curs.setPreferredSize(new Dimension(26,100));
		JButton decon = new JButton("Deconectare");
		decon.setForeground(Color.red);

		decon.addActionListener(new DeconectListener());
		asig.addActionListener(new AsigProf());
		ste.addActionListener(new StergereTabela());
		mod.addActionListener(new ModificareTabela());
		info.addActionListener(new VizualizareInfo());
		viz.addActionListener(new VizualizareTabela());
		uti.addActionListener(new Cauta());
		curs.addActionListener(new Curs());
		ins.addActionListener(new Insereaza());

		JPanel sus = new JPanel();
		sus.add(sal);
		sus.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel butoane = new JPanel();
		JPanel mijloc = new JPanel();
		JPanel mijloc1 = new JPanel();
		// orar.set

		// butoane.add(sal);

		butoane.add(Box.createRigidArea(new Dimension(10, 0)));
		butoane.add(info);
		butoane.add(Box.createRigidArea(new Dimension(0, 10)));
		butoane.add(ins);
		butoane.add(Box.createRigidArea(new Dimension(0, 10)));
		butoane.add(uti);
		butoane.add(Box.createRigidArea(new Dimension(0, 10)));
		butoane.add(asig);
		butoane.add(Box.createRigidArea(new Dimension(0, 10)));
		butoane.add(curs);
		butoane.add(Box.createRigidArea(new Dimension(0, 10)));
		butoane.setLayout(new BoxLayout(butoane, BoxLayout.Y_AXIS));
		mijloc.setLayout(new GridLayout(1, 2));

		mijloc1.add(aleg_tab);
		mijloc1.add(Box.createRigidArea(new Dimension(0, 10)));
		mijloc1.add(ste);
		mijloc1.add(Box.createRigidArea(new Dimension(0, 10)));
		mijloc1.add(viz);
		mijloc1.add(Box.createRigidArea(new Dimension(0, 10)));
		mijloc1.add(mod);

		mijloc1.setLayout(new FlowLayout(FlowLayout.LEFT));

		mijloc.add(butoane);
		mijloc.add(mijloc1);
		JPanel jos = new JPanel();
		jos.add(decon);
		jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JPanel p = new JPanel();

		p.add(sus);
		p.add(Box.createRigidArea(new Dimension(10, 0)));
		// p.add(eticheteZi);
		p.add(mijloc);

		p.add(jos);

		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	class DeconectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Logare log = new Logare(con);
			frame.dispose();
		}
	}

	class AsigProf implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AsignareProfesor(x, y, nume, prenume, con);
			frame.dispose();

		}

	}

	class Curs implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new CautaCurs(x, y, nume, prenume, con);
			frame.dispose();

		}

	}

	class VizualizareInfo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new VizInfo(x, y, nume, prenume, con);
			frame.dispose();

		}

	}

	class VizualizareTabela implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new VizTabela(x, 0, nume, prenume, aleg_tab.getSelectedItem().toString(), con);
			frame.dispose();

		}

	}

	class StergereTabela implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new Stergere(x, y, nume, prenume, aleg_tab.getSelectedItem().toString(), con);
			frame.dispose();

		}

	}

	class Cauta implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new CautaUtilizator(x, y, nume, prenume, con);
			frame.dispose();

		}

	}
	class Insereaza implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new Inserare(x, y, nume, prenume, con);
			frame.dispose();

		}

	}

	class ModificareTabela implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Modificare(x, y, nume, prenume, aleg_tab.getSelectedItem().toString(), con);
			frame.dispose();

		}

	}
}
