import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class CatalogProfesor extends JFrame{
    private static JFrame frame;
    private int x, y;
    private Connection con;
    private String nume,prenume;
    private String materie;
    private JComboBox curs;
    private String CNP;
    private String numeC;

    CatalogProfesor(int x,int y,Connection con,String nume,String prenume,String CNP){
        this.CNP=CNP;
        this.frame=new JFrame("Catalog");
        this.nume=nume;
        this.prenume=prenume;
        this.x=x-10;
        this.y=y+10;
        this.con=con;
        this.frame.setLocation(x,y);
        this.frame.setSize(300,300);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        JButton b1=new JButton("Lista studenti");
        b1.addActionListener(new Viz1());
        JButton b2=new JButton("Modificare note");
        b2.addActionListener(new Modif());
        JButton b3=new JButton("Vizualizare note");
        b3.addActionListener(new Viz2());
        JButton b4=new JButton("Inapoi");
        b4.addActionListener(new Back());
        JComboBox c1 = new JComboBox();
        try{
            c1.removeAllItems();
            CallableStatement a=null;
            String s="call vizualizare_cursuri(?)";
            a=con.prepareCall(s);
            a.setString(1,this.CNP);
            ResultSet r=null;
            r=a.executeQuery();
            while(r.next())
            {
                c1.addItem(r.getString(1));
            }
        }catch(Exception e){
            Eroare er=new Eroare("Nu s-au gasit datele cerute");
            e.printStackTrace();
        }
        c1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                numeC=c1.getSelectedItem().toString();
                materie=numeC;
            }
        });
        curs=c1;
        JPanel p=new JPanel();
        JPanel p1=new JPanel();
        p1.add(Box.createRigidArea(new Dimension(10,0)));
        p1.add(c1);
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));



        JPanel p2=new JPanel();
        p2.add(Box.createRigidArea(new Dimension(20,0)));
        p2.add(b1);
        p2.add(Box.createRigidArea(new Dimension(0,10)));
        p2.add(b2);
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel p3=new JPanel();
        p3.add(Box.createRigidArea(new Dimension(20,0)));
        JButton b41=new JButton("Descarca ");
        b41.addActionListener(new Descarca());
        p3.add(b41);
        p3.add(Box.createRigidArea(new Dimension(20,0)));
        p3.add(b3);
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));


        JPanel p4=new JPanel();
        p2.add(Box.createRigidArea(new Dimension(20,0)));
        p4.add(b4);
        p4.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel butoane=new JPanel();
        butoane.add(Box.createRigidArea(new Dimension(40,0)));
        butoane.add(p1);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.add(p2);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.add(p3);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.add(p4);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.setLayout(new BoxLayout(butoane,BoxLayout.Y_AXIS));

        JPanel mijloc=new JPanel();
        mijloc.setLayout(new BorderLayout(10,0));

        mijloc.add(butoane,BorderLayout.LINE_START);
        butoane.add(Box.createHorizontalGlue());

        p.add(Box.createRigidArea(new Dimension(0,30)));
        p.add(mijloc);
        p.add(p4);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.materie=numeC;

        frame.setContentPane(p);
        frame.setVisible(true);
        if(c1.getSelectedItem()!=null)
            numeC=c1.getSelectedItem().toString();
        else
        {
            Eroare er=new Eroare("Profesorul nu este titular la niciun curs");
            PaginaPrincipalaProfesor pag=new PaginaPrincipalaProfesor(x,y,nume,prenume,con,CNP);
            frame.dispose();
        }

    }
    class Descarca implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                CallableStatement a=null;
                String s="call lista_studenti(?,?)";
                a=con.prepareCall(s);
                if(numeC!=null)
                {
                    String materie = numeC;
                    a.setString(1,materie);
                    a.setString(2,CNP);
                    ResultSet r=null;
                    r=a.executeQuery();
                    String st;
                    int i=1;
                    String note="Notele la materia "+numeC+"\n";
                    while(r.next())
                    {
                        String numeS="", prenumeS="";
                        numeS+=r.getString(1);
                        note+=numeS;
                        note+=" ";
                        prenumeS+=r.getString(2);
                        note+=prenumeS+":";
                        note+="\n";

                        CallableStatement a2=null;
                        String s1="call viz_note(?,?,?)";
                        a2=con.prepareCall(s1);
                        a2.setString(1,numeS);
                        a2.setString(2,prenumeS);
                        a2.setString(3,numeC);
                        ResultSet r1=null;
                        r1=a2.executeQuery();
                        while(r1.next())
                        {
                            note+="nota curs: ";
                            note+=(r1.getString(1));
                            note+="\nnota laborator: ";
                            note+=(r1.getString(2));
                            note+="\nnota seminar: ";
                            note+=(r1.getString(3));
                            note+=("\nmedie: ");
                            note+=(r1.getString(4));

                        }
                        note+="\n";
                    }
                    String nuC="catalog";
                    nuC+=" "+numeC+" "+nume+" "+prenume;
                    FileWriter myF = new FileWriter(nuC);
                    if (note == "Notele la materia "+numeC+"\n")
                        myF.write("Nu sunt note");
                    else
                    {
                        myF.write(note);
                        Succes suc=new Succes("Descarcare realizata cu succes");
                    }
                    myF.close();
                }
                else
                {
                    Eroare eroare1=new Eroare("Nu sunt cursuri");
                }


            }catch(Exception e1){
                Eroare er=new Eroare(" ");
                e1.printStackTrace();
            }
        }
    }
    class Viz1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            VizualizareStudenti c=new VizualizareStudenti(x,y,con,nume,prenume,materie,CNP);
        }
    }
    class Viz2 implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Note c=new Note(x,y,con,nume,prenume,CNP);
        }
    }
    class Modif implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            ModificareNote c=new ModificareNote(x,y,con,nume,prenume,CNP);
        }
    }
    class Back implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            frame.dispose();
            CursuriProfesor c=new CursuriProfesor(con,x,y,nume,prenume,CNP);
        }
    }



}
