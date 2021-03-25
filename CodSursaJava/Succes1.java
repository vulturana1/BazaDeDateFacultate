

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;


public class Succes1 {
    private static JFrame frame;
    int x;
    int y;
    Succes1(String s){
        frame=new JFrame("Operatie efectuata");
        frame.setSize(400,130);
        frame.setLocation(600,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel err=new JLabel("SUCCES");
        JLabel motiv=new JLabel(s);

        err.setFont(new Font("", Font.BOLD, 20));
        motiv.setFont(new Font("", Font.BOLD, 12));
        err.setForeground(Color.green);
        motiv.setForeground(Color.green);


        JButton ok=new JButton("OK");

        ok.addActionListener(new CloseListener());

        JPanel p=new JPanel();
        err.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(err);
        p.add(Box.createVerticalStrut(10));
        p.add(motiv);
        motiv.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(Box.createVerticalStrut(5));
        p.add(ok);
        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        frame.setContentPane(p);
        frame.setVisible(true);
    }

    class CloseListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }
}