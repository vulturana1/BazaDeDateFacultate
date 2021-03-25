import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Procentaj {
    private String CNP;
    private static JFrame frame;
    private int x, y;
    Connection con;
    String nume,prenume;
    JTextField n1;
    JTextField n2;
    JTextField n3;
    JComboBox curs;
    Procentaj(Connection c, int x, int y,String n,String pr,String CNP) {
        this.CNP=CNP;
        frame = new JFrame("Procentaj Note");
        this.con = c;
        this.x = x ;
        this.y = y ;
        this.nume=n;
        this.prenume=pr;
        this.frame.setLocation(this.x, this.y);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.frame.setSize(400, 250);
        JLabel proc1=new JLabel("Procentaj laborator");
        proc1.setBorder(new EmptyBorder(0,0,0,50));
        JLabel proc2=new JLabel("Procentaj seminar");
        proc2.setBorder(new EmptyBorder(0,0,0,58));
        JLabel proc3=new JLabel("Procentaj curs");
        proc3.setBorder(new EmptyBorder(0,0,0,78));

        JTextField n1=new JTextField("Introduceti procentaj");
        this.n1=n1;
      //  n1.setBorder(new EmptyBorder(0,20,0,0));
        JTextField n2=new JTextField("Introduceti procentaj");
        this.n2=n2;
       // n2.setBorder(new EmptyBorder(0,33,0,0));
        JTextField n3=new JTextField("Introduceti procentaj");
        this.n3=n3;
       // n3.setBorder(new EmptyBorder(0,25,0,0));
        JButton OK=new JButton("OK");
        OK.addActionListener(new Seteaza());
        JButton back=new JButton("Inapoi");
        back.addActionListener(new Back());
        JComboBox curs=new JComboBox();
        this.curs=curs;
        try{
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
        curs.setBorder(new EmptyBorder(0,77,0,0));

        JLabel l1=new JLabel("Selectare curs");
        JPanel sus=new JPanel();
        sus.setLayout(new FlowLayout(FlowLayout.LEFT));
        sus.add(Box.createRigidArea(new Dimension(20,0)));
        sus.add(l1);
        sus.add(Box.createRigidArea(new Dimension(10,0)));
        sus.add(curs);

        //sus.setBorder(new EmptyBorder(0, 180, 0, 0));

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
        jos.add(back);

        JPanel p=new JPanel();
        p.add(sus);
        p.add(mijloc);
        p.add(jos);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.frame.setContentPane(p);
        this.frame.setVisible(true);


    }
    class Back implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            frame.dispose();
            CursuriProfesor c=new CursuriProfesor(con,x,y,nume,prenume,CNP);
        }
    }
    class Seteaza implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int x1 = Integer.parseInt(n1.getText());
            int x2 = Integer.parseInt(n2.getText());
            int x3 = Integer.parseInt(n3.getText());  //salvam procentajele in variabile
            try{
                CallableStatement a=null;
                String s="call GestionarePonderi(?,?,?,?,?)";
                a=con.prepareCall(s);
                a.setInt(1,x1);
                a.setInt(2,x3);
                a.setInt(3,x2);
                String c=String.valueOf(curs.getSelectedItem());
                a.setString(4,c);


                String se="call utID(?)";
                CallableStatement a1=null;
                a1=con.prepareCall(se);
                a1.setString(1,CNP);
                ResultSet rs =null;
                rs=a1.executeQuery();
                int id=0;
                while(rs.next())
                    id=rs.getInt(1);
                a.setInt(5,id);
                if(x1+x2+x3!=100)
                    a.setString(4,null);
                ResultSet r=null;
                r=a.executeQuery();
                Succes su=new Succes("Modificarile s-au efectuat cu succes");
            }catch(Exception ero){
                Eroare er=new Eroare("Suma procentajelor notelor nu este 100");
                ero.printStackTrace();
            }

        }
    }
}


