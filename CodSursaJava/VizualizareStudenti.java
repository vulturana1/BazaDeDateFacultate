import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class VizualizareStudenti {
    private static JFrame frame;
    private int x, y;
    private Connection con;
    private String nume, prenume;
    private JTextArea ar;
    private String CNP;
    String materie;
    VizualizareStudenti(int x, int y, Connection con, String nume, String prenume,String materie,String CNP) {
        this.CNP=CNP;
        this.frame = new JFrame("Vizualizare studenti");
        this.materie=materie;
        this.nume = nume;
        this.prenume = prenume;
        this.x = x;
        this.y = y;
        this.con = con;
        this.frame.setLocation(x, y);
        this.frame.setSize(350, 300);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        JTextArea a1=new JTextArea("Studentii profesorului "+this.nume+" "+this.prenume+" :");
        ar=a1;
        JButton OK=new JButton("OK");
        OK.addActionListener(new Viz());
        JButton back=new JButton("Inapoi");
        back.addActionListener(new Back());

        JPanel p=new JPanel();
        p.add(a1);
        JPanel jos=new JPanel();
        jos.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jos.add(OK);
        jos.add(back);
        p.add(jos);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        this.frame.setContentPane(p);
        this.frame.setVisible(true);


    }
    class Back implements ActionListener {

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
                    if(i==1)
                    {
                        stFinal="Profesorul " +nume+" "+ prenume+" de la materia "+materie+" are studentii: \n";
                        i++;
                    }
                    st=(r.getString(1));
                    stFinal=stFinal+st;
                    st=(r.getString(2));
                    stFinal=stFinal+" "+st+"\n";
                }
                if(stFinal=="")
                    ar.setText("Nu sunt studenti");
                else
                    ar.setText(stFinal);


            }catch(Exception e1){
                Eroare er=new Eroare(" ");
                e1.printStackTrace();
            }

        }
    }
}