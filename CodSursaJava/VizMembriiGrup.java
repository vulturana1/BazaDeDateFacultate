import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;

public class VizMembriiGrup extends JDialog {
	private JTable infoTable;
	private String[][] data;
	private String[] columnNames;

	public VizMembriiGrup(String[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;

		infoTable = new JTable(data, columnNames);

		add(new JScrollPane(infoTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
}