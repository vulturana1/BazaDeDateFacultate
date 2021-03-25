import javax.swing.*;

public class UneditableJTextArea extends JTextArea{
	UneditableJTextArea(String s){
		super(s);
		setEditable(false);
	}
}
