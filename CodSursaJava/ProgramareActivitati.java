import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProgramareActivitati {
    private static JFrame frame;
    private int x, y;
    Connection con;
    private String nume,prenume;
    private JComboBox act,materie;
    private JTextField t1,t2,t3,t4,t5;
    private JComboBox c1;
    private String CNP;
    ProgramareActivitati(Connection c, int x, int y,String nume, String prenume,String CNP) {
        this.CNP=CNP;
        frame = new JFrame("Programare activitati");
        this.con = c;
        this.x = x;
        this.y = y;
        this.nume=nume;
        this.prenume=prenume;
        this.frame.setLocation(this.x, this.y);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.frame.setSize(650, 600);

        JButton back=new JButton("Inapoi");
        back.addActionListener(new BackClass());

        JButton OK=new JButton("OK");

        JComboBox numeMaterie=new JComboBox();
        try{
            numeMaterie.removeAllItems();
            CallableStatement a=null;
            String s="call vizualizare_cursuri(?)";
            a=con.prepareCall(s);
            a.setString(1,this.CNP);
            ResultSet r=null;
            r=a.executeQuery();
            while(r.next())
            {
               numeMaterie.addItem(r.getString(1));
            }
        }catch(Exception e){
            Eroare er=new Eroare("Nu s-au gasit datele cerute");
            e.printStackTrace();
        }
        this.materie=numeMaterie;
        String []sir={"curs","seminar","laborator","colocviu","examen"};
        JComboBox numeActivitate=new JComboBox();
        act=numeActivitate;
        for(int i=0;i<5;i++)
            numeActivitate.addItem(sir[i]);
        JComboBox zi=new JComboBox();
        String[] ziua={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        for(int i=0;i<7;i++)
            zi.addItem(ziua[i]);


        JLabel l1=new JLabel("materie");
        l1.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l2=new JLabel("activitate");
        l2.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l4=new JLabel("data inceput");
        l4.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l5=new JLabel("data incheiere");
        l5.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l6=new JLabel("numar participanti");
        l6.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l7=new JLabel("Datele de inceput si de incheiere trebuie sa fie introduse sub forma 'yyyy-mm-dd'!");
        l7.setFont(new Font("", Font.CENTER_BASELINE,15));
        l7.setForeground(Color.red);
        JLabel l8=new JLabel("ora de inceput");
        l8.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l9=new JLabel("ora de incheiere");
        l9.setFont(new Font("", Font.CENTER_BASELINE,15));
        JLabel l10=new JLabel("ziua saptamanii");
        l10.setFont(new Font("", Font.CENTER_BASELINE,15));


        JTextField t1=new JTextField("introduceti data");
        JTextField t2=new JTextField("introduceti data");
        JTextField t3=new JTextField("introduceti numarul de participanti");
        JTextField t4=new JTextField("introduceti ora");
        JTextField t5=new JTextField("introduceti ora");



        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
        this.t5=t5;
        this.c1=zi;

        JPanel sus=new JPanel();
        JLabel sal=new JLabel("Profesorul "+nume+" "+prenume);
        sal.setFont(new Font("", Font.BOLD, 20));
        sus.add(sal);
        sus.setLayout(new GridLayout(2,1));

        JPanel p1=new JPanel();
        p1.add(Box.createRigidArea(new Dimension(20,0)));
        p1.add(l1);
        p1.add(Box.createRigidArea(new Dimension(0,10)));
        p1.add(numeMaterie);
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p2=new JPanel();
        p2.add(Box.createRigidArea(new Dimension(20,0)));
        p2.add(l2);
        p2.add(Box.createRigidArea(new Dimension(0,10)));
        p2.add(numeActivitate);
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));


        JPanel p4=new JPanel();
        p4.add(Box.createRigidArea(new Dimension(20,0)));
        p4.add(l4);
        p4.add(Box.createRigidArea(new Dimension(0,10)));
        p4.add(t1);
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p5=new JPanel();
        p5.add(Box.createRigidArea(new Dimension(20,0)));
        p5.add(l5);
        p5.add(Box.createRigidArea(new Dimension(0,10)));
        p5.add(t2);
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p6=new JPanel();
        p6.add(Box.createRigidArea(new Dimension(20,0)));
        p6.add(l6);
        p6.add(Box.createRigidArea(new Dimension(0,10)));
        p6.add(t3);
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p7=new JPanel();
        p7.add(Box.createRigidArea(new Dimension(20,0)));
        p7.add(l8);
        p7.add(Box.createRigidArea(new Dimension(0,10)));
        p7.add(t4);
        p7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p8=new JPanel();
        p8.add(Box.createRigidArea(new Dimension(20,0)));
        p8.add(l9);
        p8.add(Box.createRigidArea(new Dimension(0,10)));
        p8.add(t5);
        p8.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p9=new JPanel();
        p9.add(Box.createRigidArea(new Dimension(20,0)));
        p9.add(l10);
        p9.add(Box.createRigidArea(new Dimension(0,10)));
        p9.add(zi);
        p9.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel pT=new JPanel();
        pT.add(Box.createRigidArea(new Dimension(90,5)));
        pT.add(p1);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p2);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p4);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p5);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p6);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p7);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p8);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.add(p9);
        pT.add(Box.createRigidArea(new Dimension(0,5)));
        pT.setLayout(new BoxLayout(pT,BoxLayout.Y_AXIS));

        JPanel mijloc=new JPanel();
        mijloc.setLayout(new BorderLayout(10,0));
        mijloc.add(pT,BorderLayout.LINE_START);
        pT.add(Box.createHorizontalGlue());


        OK.addActionListener(new Programeaza());

        JPanel jos=new JPanel();
        jos.add(OK);
        jos.add(back);
        jos.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sus.add(l7);
        JPanel p=new JPanel();
        p.add(sus);
        p.add(Box.createRigidArea(new Dimension(0,30)));
        p.add(mijloc);
        p.add(jos);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.frame.setContentPane(p);
        this.frame.setVisible(true);


    }
    class BackClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CursuriProfesor c=new CursuriProfesor(con,x,y,nume,prenume,CNP);
            frame.dispose();
        }
    }
    class Programeaza implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
               Date data1=java.sql.Date.valueOf(t1.getText());
               Date data2=java.sql.Date.valueOf(t2.getText());
            try{
                CallableStatement a=null;
                String m = String.valueOf(materie.getSelectedItem());
                String actv = String.valueOf(act.getSelectedItem());
                String s="call adaugaActivitate(?,?,?,?,?,?,?,?,?)";
                a=con.prepareCall(s);
                a.setString(1,actv);
                a.setString(6,m);
                a.setString(2,CNP);

                a.setDate(3,data1);
                a.setDate(4,data2);
                a.setInt(5,Integer.parseInt(t3.getText()));
                a.setString(7, t4.getText());
                a.setString(8,t5.getText());
                a.setString(9,c1.getSelectedItem().toString());
                ResultSet r=null;
                r=a.executeQuery();
                Succes su=new Succes("Programare realizata cu succes");
            }catch(Exception ero){
                Eroare er=new Eroare("Nu s-a putut realiza programarea");
                ero.printStackTrace();
            }

        }
    }


}
