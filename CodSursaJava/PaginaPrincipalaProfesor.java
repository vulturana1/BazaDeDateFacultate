import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class PaginaPrincipalaProfesor {
    Connection con;
    int x;
    int y;
    String nume;
    String prenume;
    String CNP;
    private static JFrame frame;
    JTextArea orar;

    PaginaPrincipalaProfesor(int x,int y,String nume,String prenume,Connection c,String CNP){
        this.CNP=CNP;
        frame=new JFrame("Pagina "+nume+" "+prenume);
        this.x=x-10;
        this.y=y+10;
        this.nume=nume;
        this.prenume=prenume;
        con=c;
        frame.setLocation(this.x,this.y);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(700,320);

        JLabel sal=new JLabel("Bine ai venit "+nume+" "+prenume);
        sal.setFont(new Font("", Font.BOLD, 20));
        JLabel data=new JLabel("data:");
        JLabel orar_mess=new JLabel("Orarul pentru astazi:");

        JButton info=new JButton("Vizualizare info");
        info.addActionListener(new Viz());
        //info.setPreferredSize(new Dimension(26,100));
        JButton curs=new JButton("Cursuri");
        curs.addActionListener(new CursProf());

        //curs.setPreferredSize(new Dimension(26,100));
        JButton decon=new JButton("Deconectare");
        decon.setForeground(Color.red);

        decon.addActionListener(new DeconectListener());

        orar=new UneditableJTextArea("ora:curs,activitate\n");
        try{
            CallableStatement a=null;
            String s="call vizualizare_activitati_zi_curenta_AS_profesor(?)";
            a=con.prepareCall(s);
            a.setString(1,CNP);
            ResultSet r=null;
            r=a.executeQuery();
            String st;
            String stFinal="";
            int i=1;
            while(r.next())
            {
                if(i==1)
                {
                    st=(r.getString(1));
                    stFinal="Profesorul: "+st+" ";
                    st=(r.getString(2));
                    stFinal=stFinal+st+" preda astazi materiile:\n";
                    i++;
                }
                st=(r.getString(3));
                stFinal=stFinal+st+", avand activitatea: ";
                st=(r.getString(4));
                stFinal=stFinal+st;
                st=(r.getString(5));
                stFinal+=" de la ora: "+st;
                st=(r.getString(6));
                stFinal+=" pana la ora: "+st;


            }
            if(stFinal=="")
                orar.setText("Nu sunt activitati curente");
            else
                orar.setText(stFinal);


        }catch(Exception e1){
            Eroare er=new Eroare(" ");
            e1.printStackTrace();
        }
        orar.setFont(new Font("Seqoe Script",Font.BOLD,12));

        JPanel sus=new JPanel();
        sus.add(sal);
        sus.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel eticheteZi=new JPanel();

        eticheteZi.add(Box.createHorizontalStrut(1));
        eticheteZi.add(data);
        eticheteZi.add(Box.createHorizontalStrut(110));
        eticheteZi.add(orar_mess);
        eticheteZi.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel butoane=new JPanel();
        JPanel mijloc=new JPanel();
        orar.setColumns(5);

        butoane.add(Box.createRigidArea(new Dimension(20,0)));
        butoane.add(info);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.add(curs);
        butoane.add(Box.createRigidArea(new Dimension(0,10)));
        butoane.setLayout(new BoxLayout(butoane,BoxLayout.Y_AXIS));

        mijloc.setLayout(new BorderLayout(10,0));

        mijloc.add(butoane,BorderLayout.LINE_START);
        butoane.add(Box.createHorizontalGlue());
        mijloc.add(orar,BorderLayout.CENTER);

        JPanel jos=new JPanel();
        jos.add(decon);
        jos.setLayout(new FlowLayout(FlowLayout.RIGHT));


        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
    //    System.out.println(formatter.format(date));
        data.setText("Data: "+formatter.format(date));

        JPanel p=new JPanel();

        p.add(sus);
        p.add(Box.createRigidArea(new Dimension(0,30)));
        p.add(eticheteZi);
        p.add(mijloc);

        p.add(jos);

        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));


        frame.setContentPane(p);
        frame.setVisible(true);
    }
    class CursProf implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int sem=1;
            try{
                JComboBox c1=new JComboBox();
                CallableStatement a=null;
                String s="call vizualizare_cursuri(?)";
                a=con.prepareCall(s);
                a.setString(1,CNP);
                ResultSet r=null;
                r=a.executeQuery();
                while(r.next())
                {
                    sem=0;
                    c1.addItem(r.getString(1));
                }
                if(sem==1)
                {
                    Eroare er1=new Eroare("Profesorul nu e titular la niciun curs");
                }

            }catch(Exception ex){
                Eroare er=new Eroare("Profesorul nu e titular la niciun curs");
                ex.printStackTrace();
            }
            if(sem==0)
            {
                frame.dispose();
                CursuriProfesor c=new CursuriProfesor(con,x,y,nume,prenume,CNP);
            }

        }
    }

    class DeconectListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Logare log=new Logare(con);
            frame.dispose();
        }
    }
    class Viz implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            VizualizareDatePersonale p=new VizualizareDatePersonale(x,y,con,nume,prenume,CNP);
        }
    }
}
