package project1;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.awt.*;


public class Table extends JFrame
{
	JTable table;
	
	public Table(Object[][] data, String[] columnNames) {

		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		
		// TODO Auto-generated constructor stub
	}

	}