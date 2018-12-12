package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import controller.ClientMain;
import model.Car;
import model.CarTableModel;
import model.CarsList;

public class CarDeleteForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String options[] = { "id", "producer", "model", "price", "year" };
	public JTextField m_txt;
	private ClientMain m_interface;
	private JComboBox<String> comboBox;
	CarTableModel model;

	public CarDeleteForm(ClientMain iinterface) {

		m_interface = iinterface;
		JPanel panel = new JPanel(null);
		comboBox = new JComboBox<String>(options);
		comboBox.setBounds(160, 60, 200, 30);
		panel.add(comboBox);

		m_txt = new JTextField(20);
		m_txt.setBounds(160, 90, 200, 30);
		panel.add(m_txt);

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
		btnCancel.setBounds(100, 150, 90, 50);
		btnCancel.setMnemonic(KeyEvent.VK_ESCAPE);
		panel.add(btnCancel);

		AbstractAction absActionSave = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				delete();
				btnCancel.requestFocusInWindow();
			}

		};

		JButton btnSave = new JButton("Delete");
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		btnSave.getActionMap().put("Enter", absActionSave);
		btnSave.addActionListener(absActionSave);
		btnSave.setBounds(250, 150, 90, 50);
		btnSave.setMnemonic(KeyEvent.VK_ENTER);
		panel.add(btnSave);

		add(panel);
		setSize(450, 300);
		setVisible(true);
	}

	private void delete() {
		String value = m_txt.getText();
		String option = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
		CarsList cars = m_interface.getModel().getCars();
		for (int i = cars.GetSize() - 1; i >= 0; i--) {
			Car car = cars.getCarAt(i);
			String resultString = "";
			if (option.equalsIgnoreCase("id")) {
				resultString = Integer.toString(car.getId());
			} else if (option.equalsIgnoreCase("producer")) {
				resultString = car.getProducer();
			} else if (option.equalsIgnoreCase("model")) {
				resultString = car.getModel();
			} else if (option.equalsIgnoreCase("price")) {
				resultString = Integer.toString(car.getPrice());
			} else {
				resultString = Integer.toString(car.getYear());
			}
			if (resultString.equalsIgnoreCase(value)) {
				cars.removeCarAt(i);
				m_interface.sendMessage("delete", car);
			}
		}
		m_interface.setScrollPane(cars);
		dispose();
	}

}
