package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.CarsList;
import view.CarDeleteForm;
import view.CarInsertForm;
import view.CarUpdateForm;

public class ActionController implements ActionListener {

	private ClientMain m_interface;

	public ActionController(ClientMain iinterface) {

		this.m_interface = iinterface;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();
		if (button.getText() == "Delete") {

			m_interface.getTabel().getSelectedRow();
			new CarDeleteForm(m_interface);

		} else if (button.getText() == "Insert") {

			new CarInsertForm(m_interface);

		} else if (button.getText() == "Update") {
			int selectedIndex = m_interface.getTabel().getSelectedRow();
			new CarUpdateForm(m_interface, selectedIndex);

		}else if(button.getText().equalsIgnoreCase("Send")) 
		{
			m_interface.sendMessage(m_interface.getTxtMessage().getText(), null);
			m_interface.readMessage();
		} else if (button.getText() == "Retrieve") {

			m_interface.sendMessage("retrieve", null);
			m_interface.readMessage();
		} else if (button.getText().equalsIgnoreCase("clear")) {
			m_interface.sendMessage("clear", null);
			m_interface.readMessage();

			CarsList cars = m_interface.getModel().getCars();
			for (int i = cars.GetSize() - 1; i >= 0; i--) {
				cars.removeCarAt(i);
			}
			m_interface.setScrollPane(cars);

		}
	}

}
