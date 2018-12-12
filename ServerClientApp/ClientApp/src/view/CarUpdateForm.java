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

public class CarUpdateForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientMain m_interface;
	private Integer m_index;
	private JTextField m_txtProducer;
	private JTextField m_txtModel;
	private JTextField m_txtPrice;
	private JTextField m_txtYear;
	private Car m_car;

	public CarUpdateForm(ClientMain iinterface, int selectedIndex) {

		m_interface = iinterface;
		m_index = selectedIndex;

		Integer id = (Integer) m_interface.getModel().getValueAt(m_index, 0);
		String producer = (String) m_interface.getModel().getValueAt(m_index, 1);
		String model = (String) m_interface.getModel().getValueAt(m_index, 2);
		Integer price = (Integer) m_interface.getModel().getValueAt(m_index, 3);
		Integer year = (Integer) m_interface.getModel().getValueAt(m_index, 4);

		m_car = new Car(id, producer, model, price, year);
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

		lbl = new JLabel("" + id);
		lbl.setBounds(110, 40, 85, 20);
		panel.add(lbl);

		m_txtProducer = new JTextField(producer);
		m_txtProducer.setBounds(110, 70, 150, 20);
		panel.add(m_txtProducer);

		m_txtModel = new JTextField(model);
		m_txtModel.setBounds(110, 100, 150, 20);
		panel.add(m_txtModel);

		m_txtPrice = new JTextField(price.toString());
		m_txtPrice.setBounds(110, 130, 150, 20);
		panel.add(m_txtPrice);

		m_txtYear = new JTextField(year.toString());
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

		btnCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"Escape");
		btnCancel.getActionMap().put("Escape", absActionCancel);
		btnCancel.addActionListener(absActionCancel);
		btnCancel.setBounds(280, 80, 90, 50);
		btnCancel.setMnemonic(KeyEvent.VK_ESCAPE);
		panel.add(btnCancel);

		AbstractAction absActionSave = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				btnCancel.requestFocusInWindow();
			}
		};

		JButton btnSave = new JButton("Update");
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		btnSave.getActionMap().put("Enter", absActionSave);
		btnSave.addActionListener(absActionSave);
		btnSave.setBounds(280, 150, 90, 50);
		btnSave.setMnemonic(KeyEvent.VK_ENTER);
		panel.add(btnSave);

		add(panel);
		setSize(450, 300);
		setVisible(true);
	}

	private void update() {

		m_car.setProducer(m_txtProducer.getText());
		m_car.setModel(m_txtModel.getText());
		m_car.setPrice(Integer.parseInt(m_txtPrice.getText()));
		m_car.setYear(Integer.parseInt(m_txtYear.getText()));
		m_interface.sendMessage("update", m_car);

		m_interface.getModel().getCars().getCarAt(m_index).update(m_car);

		CarTableModel model = (CarTableModel) m_interface.getModel();
		m_interface.getTabel().setModel(model);
		model.fireTableDataChanged();
		dispose();

	}

}
