import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class VizualizareDatePersonale {
    private static JFrame frame;
    private int x, y;
    private Connection con;
    private String nume, prenume;
    private JTextArea ar;
    private String CNP;
    VizualizareDatePersonale(int x, int y, Connection con, String nume, String prenume,String CNP) {
        this.CNP=CNP;
        this.frame = new JFrame("Vizualizare date personale profesor");
        this.nume = nume;
        this.prenume = prenume;
        this.x = x;
        this.y = y;
        this.con = con;
        this.frame.setLocation(x, y);
        this.frame.setSize(350, 300);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        JTextArea a1=new JTextArea("Informatiile personale ale profesorului "+this.nume+" "+this.prenume+" :");
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
            PaginaPrincipalaProfesor c=new PaginaPrincipalaProfesor(x,y,nume,prenume,con,CNP);
        }
    }
    class Viz implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                CallableStatement a=null;
                String s="call viz_inf_as_profesor(?)";
                a=con.prepareCall(s);
                a.setString(1,CNP);
                ResultSet r=null;
                r=a.executeQuery();
                String st;
                String stFinal="";
                int i=1;
                while(r.next())
                {


                    stFinal="profesorul: ";
                    st=(r.getString(3));
                    stFinal+=st+ " ";
                    st=(r.getString(4));
                    stFinal+=st+"\n";
                    st=(r.getString(2));
                    stFinal+="CNP: "+st+"\n";
                    st=(r.getString(5));
                    stFinal+="Adresa: " +st+"\n";
                    st=(r.getString(6));
                    stFinal+="IBAN: "+st+"\n";
                    st=(r.getString(7));
                    stFinal+="numar telefon: "+st+"\n";
                    st=(r.getString(8));
                    stFinal+="numar contract: "+st+"\n";
                    st=(r.getString(9));
                    stFinal+="email: "+st+"\n";

                }
                ar.setText(stFinal);


            }catch(Exception e1){
                Eroare er=new Eroare(" ");
                e1.printStackTrace();
            }

        }
    }
}