package application.jtable.management;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] columnNames;
	private List<String[]> data;

	public TableModel(List<String[]> pData, String[] pColumnNames) {
		this.columnNames = pColumnNames;
		// this.data = new ArrayList<String[]>();
		this.data = pData;
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;// length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}
}
