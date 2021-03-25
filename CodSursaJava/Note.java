import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Note {
    private static JFrame frame;
    private int x, y;
    private Connection con;
    private String nume, prenume;
    private JComboBox st,cu;
    private JTextField c,s,l;
    private String CNP;
    private String stud;
    private String materie;
    Note(int x, int y, Connection con, String nume, String prenume,String CNP) {
        this.CNP=CNP;
        this.frame = new JFrame("Vizualizare note");
        this.nume = nume;
        this.prenume = prenume;
        this.x = x;
        this.y = y;
        this.con = con;
        this.frame.setLocation(x, y);
        this.frame.setSize(500, 300);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);


        JLabel proc1=new JLabel("Nota laborator");
        proc1.setBorder(new EmptyBorder(0,0,0,50));
        JLabel proc2=new JLabel("Nota seminar");
        proc2.setBorder(new EmptyBorder(0,0,0,58));
        JLabel proc3=new JLabel("Nota curs");
        proc3.setBorder(new EmptyBorder(0,0,0,78));

        JTextField n1=new JTextField("nota laborator");
        n1.setSize(100,50);
        this.l=n1;
        this.l.setSize(100,50);
        JTextField n2=new JTextField(" nota seminar ");
        n2.setSize(100,50);
        this.s=n2;
        this.s.setSize(100,50);
        JTextField n3=new JTextField("  nota curs   ");
        n3.setSize(100,50);
        this.c=n3;
        this.c.setSize(100,50);
        JButton OK=new JButton("OK");
        OK.addActionListener(new Viz());
        JComboBox curs=new JComboBox();
        JComboBox student=new JComboBox();
        try{
            curs.removeAllItems();
            CallableStatement a=null;
            String s="call vizualizare_cursuri(?)";
            a=con.prepareCall(s);
            a.setString(1,this.CNP);
            ResultSet r=null;
            r=a.executeQuery();
            while(r.next())
            {
                curs.addItem(r.getString(1));
            }
        }catch(Exception e){
            Eroare er=new Eroare("Nu s-au gasit datele cerute");
            e.printStackTrace();
        }
        materie=curs.getSelectedItem().toString();
        CallableStatement a=null;
        String s="call lista_studenti(?,?)";
        try {
            a=con.prepareCall(s);
            a.setString(1,materie);
            a.setString(2,CNP);
            ResultSet r=null;
            r=a.executeQuery();
            String st;
            String stFinal="";
            int i=1;
            while(r.next())
            {
                String stud="";
                stud+=r.getString(1);
                stud+=" ";
                stud+=r.getString(2);
                student.addItem(stud);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            student.removeAllItems();
            CallableStatement a1=null;
            String s1="call lista_studenti(?,?)";
            a1=con.prepareCall(s1);
            a1.setString(1,materie);
            a1.setString(2,CNP);
            ResultSet r=null;
            r=a1.executeQuery();
            String st;
            String stFinal="";
            int i=1;
            while(r.next())
            {
                String stud="";
                stud+=r.getString(1);
                stud+=" ";
                stud+=r.getString(2);
                student.addItem(stud);
            }
        }catch(Exception e1){
            Eroare er=new Eroare(" ");
            e1.printStackTrace();
        }
        if(student.getSelectedItem()!=null)
            stud=student.getSelectedItem().toString();
        else
        {
            Eroare er=new Eroare("Nu sunt studenti inscrisi la acest curs");
        }
        curs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                materie=curs.getSelectedItem().toString();
                try{
                    if (student!=null) {
                        student.removeAllItems();
                    }
                    CallableStatement a=null;
                    String s="call lista_studenti(?,?)";
                    a=con.prepareCall(s);
                    a.setString(1,materie);
                    a.setString(2,CNP);
                    ResultSet r=null;
                    r=a.executeQuery();
                    String st;
                    String stFinal="";
                    int i=1;
                    while(r.next())
                    {
                        String stud="";
                        stud+=r.getString(1);
                        stud+=" ";
                        stud+=r.getString(2);
                        student.addItem(stud);
                    }
                }catch(Exception e1){
                    Eroare er=new Eroare(" ");
                    e1.printStackTrace();
                }
            }
        });


        student.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(student.getSelectedItem()!=null)
                    stud=student.getSelectedItem().toString();
                else
                {
                    Eroare er=new Eroare("Nu sunt studenti inscrisi la acest curs");
                }

            }
        });
        this.st=student;
        this.cu=curs;
        curs.setBorder(new EmptyBorder(0,77,0,0));

        JLabel l1=new JLabel("Selectare curs si student");
        JPanel sus=new JPanel();
        sus.setLayout(new FlowLayout(FlowLayout.LEFT));
        sus.add(Box.createRigidArea(new Dimension(30,0)));
        sus.add(l1);
        sus.add(Box.createRigidArea(new Dimension(10,0)));
        sus.add(curs);
        sus.add(Box.createRigidArea(new Dimension(10,0)));
        sus.add(student);
        sus.add(Box.createHorizontalGlue());


        JPanel pCont=new JPanel();
        JPanel L1=new JPanel();
        L1.setLayout(new FlowLayout(FlowLayout.LEFT));
        L1.add(Box.createRigidArea(new Dimension(20,0)));
        L1.add(proc1);
        L1.add(Box.createRigidArea(new Dimension(10,0)));
        L1.add(n1);

        JPanel L2=new JPanel();
        L2.setLayout(new FlowLayout(FlowLayout.LEFT));
        L2.add(Box.createRigidArea(new Dimension(20,0)));
        L2.add(proc2);
        L2.add(Box.createRigidArea(new Dimension(10,0)));
        L2.add(n2);

        JPanel L3=new JPanel();
        L3.setLayout(new FlowLayout(FlowLayout.LEFT));
        L3.add(Box.createRigidArea(new Dimension(20,0)));
        L3.add(proc3);
        L3.add(Box.createRigidArea(new Dimension(10,0)));
        L3.add(n3);


        pCont.add(Box.createRigidArea(new Dimension(40,5)));
        pCont.add(L1);
        pCont.add(Box.createRigidArea(new Dimension(0,5)));
        pCont.add(L2);
        pCont.add(Box.createRigidArea(new Dimension(0,5)));
        pCont.add(L3);
        pCont.add(Box.createRigidArea(new Dimension(0,5)));
        pCont.setLayout(new BoxLayout(pCont,BoxLayout.Y_AXIS));
        //  pCont.setBorder(new EmptyBorder(0, 180, 0, 0));

        JPanel mijloc=new JPanel();
        mijloc.setLayout(new BorderLayout(10,0));
        mijloc.add(pCont,BorderLayout.LINE_START);
        pCont.add(Box.createHorizontalGlue());

        JPanel jos=new JPanel();
        jos.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jos.add(OK);

        JButton back=new JButton("Inapoi");
        back.addActionListener(new Back());
        JPanel p=new JPanel();
        p.add(sus);
        p.add(mijloc);
        jos.add(back);
        p.add(jos);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.frame.setContentPane(p);
        this.frame.setVisible(true);


    }

    class Back implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            CatalogProfesor c=new CatalogProfesor(x,y,con,nume,prenume,CNP);
        }
    }
    class Viz implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String nu="";
                String pre="";
                String intreg="";
                intreg=stud;
                int i=0;
                while(intreg.charAt(i)!=' ')
                    nu+=intreg.charAt(i++);
                i++; //trecem peste spatiu
                while(i<intreg.length())
                {
                    pre+=intreg.charAt(i++);
                }


                CallableStatement a=null;
                String s1="call viz_note(?,?,?)";
                a=con.prepareCall(s1);
                a.setString(1,nu);
                a.setString(2,pre);
                a.setString(3,materie);
                ResultSet r=null;
                r=a.executeQuery();
                int sem=1;

                while(r.next())
                {
                    sem=0;
                    c.setText(r.getString(1));
                    l.setText(r.getString(2));
                    s.setText(r.getString(3));
                    System.out.println(c.getText()
                    );
                }
                if(sem==1)
                {
                    c.setText("Nu are nota");
                    l.setText("Nu are nota");
                    s.setText("Nu are nota");
                }
            }catch(Exception ero){
                Eroare er=new Eroare("Notele nu au fost introduse");
                ero.printStackTrace();
            }
        }
    }
}
