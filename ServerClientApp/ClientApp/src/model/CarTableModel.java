package model;

import javax.swing.table.DefaultTableModel;

public class CarTableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final int NR_COL = 5;
	final int COL_EDIT = 3;
	private CarsList m_list;

	public CarTableModel(CarsList list) {
		m_list = list;
	}
	
	public CarTableModel() {
	
	}

	public CarsList getCars() {
		return m_list;
	}
	
	public void setCars(CarsList list) {
		m_list = list;
	}

	@Override
	public int getRowCount() {
		if (m_list != null) {
			return m_list.GetSize();
		}

		return 0;
	}

	@Override
	public int getColumnCount() {
		return NR_COL;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return Car.GetAttributeName(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0 || columnIndex == 4 || columnIndex == 5) {
			return Integer.class;
		}

		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return COL_EDIT == columnIndex;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= m_list.GetSize()) {
			return null;
		}
		Car car = m_list.Get(rowIndex);

		return car.GetAttribute(columnIndex);
	}
}
