import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CursuriProfesor {

    private static JFrame frame;
    private int x,y;
    private String nume;
    private String prenume;
    Connection con;
    private String CNP;
    CursuriProfesor(Connection c,int x,int y,String nume, String prenume,String CNP){
        frame=new JFrame("Cursuri-profesor");
        this.CNP=CNP;
        this.con=c;
        this.x=x-10;
        this.y=y+10;
        this.nume=nume;
        this.prenume=prenume;
        this.frame.setLocation(this.x,this.y);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.frame.setSize(500,500);

        JPanel pSus=new JPanel();
        pSus.setLayout(new FlowLayout(FlowLayout.CENTER));
        Label text=new Label("Cursuri");
        text.setFont(new Font("", Font.BOLD, 20));
        pSus.add(text);

        JPanel p=new JPanel();
        JButton Programare=new JButton("Programare activitati");
        Programare.addActionListener(new Prog());
        JButton Catalog=new JButton("Catalog");
        Catalog.addActionListener(new Cata());
        JButton Procentaj=new JButton("Procentaj note");
        Procentaj.addActionListener(new Procent());
        JButton Vizualizare1=new JButton("Vizualizare activitati curente");
        Vizualizare1.addActionListener(new Viz1());
        JButton Vizualizare2=new JButton("Vizualizare activitati viitoare");
        Vizualizare2.addActionListener(new Viz2());
        JButton Descarcare=new JButton("Descarcare activitati");
        Descarcare.addActionListener(new Desc());



        JPanel pBut1=new JPanel();

        JPanel pBut=new JPanel();
        JPanel pBut2=new JPanel();
        JPanel pBut3=new JPanel();
        JPanel pBut4=new JPanel();
        JPanel pBut5=new JPanel();
        JPanel mijloc=new JPanel();

        pBut1.add(Box.createRigidArea(new Dimension(20,0)));
        pBut1.add(Programare);
        pBut1.add(Box.createRigidArea(new Dimension(0,10)));
        pBut1.setLayout(new FlowLayout(FlowLayout.LEFT));

        pBut2.add(Box.createRigidArea(new Dimension(20,0)));
        pBut2.add(Catalog);
        pBut2.setLayout(new FlowLayout(FlowLayout.LEFT));


        pBut3.add(Box.createRigidArea(new Dimension(20,0)));
        pBut3.add(Procentaj);
        pBut3.add(Box.createRigidArea(new Dimension(0,10)));
        pBut3.setLayout(new FlowLayout(FlowLayout.LEFT));

        pBut4.add(Box.createRigidArea(new Dimension(20,0)));
        pBut4.add(Vizualizare1);
        pBut4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel pBut41=new JPanel();
        pBut41.add(Box.createRigidArea(new Dimension(20,0)));
        pBut41.add(Vizualizare2);
        pBut41.setLayout(new FlowLayout(FlowLayout.LEFT));

        pBut5.add(Box.createRigidArea(new Dimension(20,0)));
        pBut5.add(Descarcare);
        pBut5.setLayout(new FlowLayout(FlowLayout.LEFT));

        pBut.add(Box.createRigidArea(new Dimension(60,0)));
        pBut.add(pBut1);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.add(pBut2);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.add(pBut3);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.add(pBut4);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.add(pBut41);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.add(pBut5);
        pBut.add(Box.createRigidArea(new Dimension(0,10)));
        pBut.setLayout(new BoxLayout(pBut,BoxLayout.Y_AXIS));

        mijloc.setLayout(new BorderLayout(10,0));
        mijloc.add(pBut,BorderLayout.LINE_START);
        pBut.add(Box.createHorizontalGlue());

        JButton Back=new JButton("Inapoi");
        Back.addActionListener(new BackListener());
        JPanel jos=new JPanel();
        jos.add(Back);
        jos.setLayout(new FlowLayout(FlowLayout.RIGHT));

        p.add(pSus);
        p.add(Box.createRigidArea(new Dimension(0,30)));
        p.add(mijloc);
        p.add(jos);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.frame.setContentPane(p);
        this.frame.setVisible(true);
    }
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            frame.dispose();
            PaginaPrincipalaProfesor p=new PaginaPrincipalaProfesor(x,y,nume,prenume,con,CNP);
        }
    }
    class Procent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Procentaj p=new Procentaj(con,x,y,nume,prenume,CNP);
        }
    }
    class Prog implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            ProgramareActivitati p=new ProgramareActivitati(con,x,y,nume,prenume,CNP);
        }
    }
    class Viz1 implements ActionListener{
        String[][] result = null;
        public void actionPerformed(ActionEvent e) {
            ResultSet util = null;
            int colCount = 0;
            int rowCount = 0;
            try {

                CallableStatement mySt = null;
                mySt = con.prepareCall("{call vizualizare_activitati_curente_AS_profesor(?)}");
                mySt.setString(1, CNP);
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

            String[] columnNames = { "Nume", "Prenume","Activitate curenta", "Data inceput", "Ora inceput", "Ora sfarsit", "Zi"};

            VizualizareTabel activViit = new VizualizareTabel(result, columnNames);

        }
    }

    class Viz2 implements ActionListener{
        String[][] result = null;
        public void actionPerformed(ActionEvent e) {
            ResultSet util = null;
            int colCount = 0;
            int rowCount = 0;
            try {

                CallableStatement mySt = null;
                mySt = con.prepareCall("{call vizualizare_activitati_viitoare_AS_profesor(?)}");
                mySt.setString(1, CNP);
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

            String[] columnNames = { "Nume", "Prenume","Activitate curenta", "Data inceput", "Ora inceput", "Ora sfarsit", "Zi"};

            VizualizareTabel activViit = new VizualizareTabel(result, columnNames);

        }
    }

    class Cata implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            CatalogProfesor p=new CatalogProfesor(x,y,con,nume,prenume,CNP);
        }
    }
    class Desc implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //descarcam activitatile curente
            //ne folosim de un fisier + de procedura vizualizare activitati curente
            try {
                CallableStatement a = null;
                String s = "call vizualizare_activitati_curente_AS_profesor(?)";
                a = con.prepareCall(s);
                a.setString(1, CNP);
                ResultSet r = null;
                r = a.executeQuery();
                String st;
                String stFinal = "";
                int i = 1;
                while (r.next()) {
                    if (i == 1) {
                        st = (r.getString(1));
                        stFinal = "Profesorul: " + st + " ";
                        st = (r.getString(2));
                        stFinal = stFinal + st + " are activitatile curente:\n";
                        i++;
                    }
                    st = (r.getString(3));
                    stFinal = stFinal + st + " incepand cu data de: ";
                    st = (r.getString(4));
                    stFinal = stFinal + st;
                    st = (r.getString(5));
                    stFinal += " de la ora: " + st;
                    st = (r.getString(6));
                    stFinal += " pana la ora: " + st;
                    st = (r.getString(7));
                    stFinal += " in ziua de: " + st + "\n";


                }
                try {
                    LocalDateTime time = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
                    String s2=time.format(formatter).toString();
                    s2+="_profesor_"+nume;
                    s2+="_"+prenume;
                    s2+=".txt";
                    FileWriter myF = new FileWriter(s2);
                    if (stFinal == "")
                        myF.write("Nu sunt activitati curente");
                    else
                    {
                        myF.write(stFinal);
                        Succes suc=new Succes("Descarcare realizata cu succes");
                    }
                    myF.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            } catch (Exception e1) {
                Eroare er = new Eroare(" ");
                e1.printStackTrace();
            }
        }
    }

    public class CreateFile {
        public void main(String[] args) {
            try {
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
                String s2=time.format(formatter).toString();
                s2+=".txt";
                File myObj = new File(s2);
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}

