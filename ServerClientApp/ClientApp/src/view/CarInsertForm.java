package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import controller.ClientMain;
import model.Car;
import model.CarTableModel;

public class CarInsertForm extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientMain m_interface;
	private JTextField m_txtProducer;
	private JTextField m_txtModel;
	private JTextField m_txtPrice;
	private JTextField m_txtYear;
	private JTextField m_txtId;
	private Car m_car;
	
	public CarInsertForm(ClientMain iinterface) {
		
		m_interface = iinterface;
		m_car = new Car();
		JPanel panel = new JPanel();

		panel.setLayout(null);

		JLabel lbl = new JLabel("Id");
		lbl.setBounds(10, 40, 85, 20);
		panel.add(lbl);
		lbl = new JLabel("Producer");
		lbl.setBounds(10, 70, 85, 20);
		panel.add(lbl);
		lbl = new JLabel("Model");
		lbl.setBounds(10, 100, 85, 20);
		panel.add(lbl);
		lbl = new JLabel("Price");
		lbl.setBounds(10, 130, 85, 20);
		panel.add(lbl);
		lbl = new JLabel("Year");
		lbl.setBounds(10, 160, 85, 20);
		panel.add(lbl);
		
		m_txtId = new JTextField();
		m_txtId.setBounds(110, 40, 150, 20);
		panel.add(m_txtId);
		
		m_txtProducer = new JTextField();
		m_txtProducer.setBounds(110, 70, 150, 20);
		panel.add(m_txtProducer);

		m_txtModel = new JTextField();
		m_txtModel.setBounds(110, 100, 150, 20);
		panel.add(m_txtModel);

		m_txtPrice = new JTextField();
		m_txtPrice.setBounds(110, 130, 150, 20);
		panel.add(m_txtPrice);

		m_txtYear = new JTextField();
		m_txtYear.setBounds(110, 160, 150, 20);
		panel.add(m_txtYear);

		JButton btnCancel = new JButton("Cancel");
		AbstractAction absActionCancel = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				btnCancel.requestFocusInWindow();
			}
		};
		
		btnCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		btnCancel.getActionMap().put("Escape", absActionCancel);
		btnCancel.addActionListener(absActionCancel);
		btnCancel.setBounds(280, 80, 90, 50);
		btnCancel.setMnemonic(KeyEvent.VK_ESCAPE);
		panel.add(btnCancel);
		
		AbstractAction absActionSave = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				btnCancel.requestFocusInWindow();
			}
		};

		JButton btnSave = new JButton("Insert");
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		btnSave.getActionMap().put("Enter", absActionSave);
		btnSave.addActionListener(absActionSave);
		btnSave.setBounds(280, 150, 90, 50);
		btnSave.setMnemonic(KeyEvent.VK_ENTER);
		panel.add(btnSave);

		add(panel);
		setSize(450,300);
		setVisible(true);
	}
	
	private void insert() { 
				
				m_car = new Car(Integer.parseInt(m_txtId.getText()), m_txtProducer.getText(), m_txtModel.getText(), 
						Integer.parseInt(m_txtPrice.getText()), Integer.parseInt(m_txtYear.getText()));
				m_interface.getModel().getCars().Add(m_car);
				m_interface.sendMessage("insert", m_car);
				CarTableModel model = (CarTableModel) m_interface.getModel();
				m_interface.m_carsTable.setModel(model);
				model.fireTableDataChanged();
				dispose();
			
	}
	
}
