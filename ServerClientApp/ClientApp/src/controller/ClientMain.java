package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Car;
import model.CarTableModel;
import model.CarsList;

@SuppressWarnings("serial")
public class ClientMain extends JFrame {

	private JTextArea areaMesaje;
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	public JTable m_carsTable;

	private CarsList arrayList;

	private JTextField txtMessage;

	private JScrollPane m_scrollPane;
	private JScrollPane m_areaScollPane;

	private JButton m_btnDelete;
	private JButton m_btnUpdate;
	private JButton m_btnInsert;
	private JButton m_btnRetrive;
	private JButton m_btnClear;

	private CarTableModel m_model;

	public ClientMain() {
		super("Client");

		connect();

		JPanel panel = new JPanel();
		panel.setLayout(null);

		m_model = new CarTableModel();

		m_carsTable = new JTable();
		m_carsTable.setModel(m_model);
		m_scrollPane = new JScrollPane(m_carsTable);
		m_scrollPane.setBounds(10, 100, 400, 300);
		panel.add(m_scrollPane);

		m_btnDelete = new JButton("Delete");
		m_btnDelete.addActionListener(new ActionController(this));
		m_btnDelete.setBounds(10, 10, 80, 60);
		panel.add(m_btnDelete);

		m_btnUpdate = new JButton("Update");
		m_btnUpdate.addActionListener(new ActionController(this));
		m_btnUpdate.setBounds(95, 10, 80, 60);
		panel.add(m_btnUpdate);

		m_btnInsert = new JButton("Insert");
		m_btnInsert.addActionListener(new ActionController(this));
		m_btnInsert.setBounds(180, 10, 80, 60);
		panel.add(m_btnInsert);

		m_btnRetrive = new JButton("Retrieve");
		m_btnRetrive.addActionListener(new ActionController(this));
		m_btnRetrive.setBounds(265, 10, 90, 60);
		panel.add(m_btnRetrive);

		m_btnClear = new JButton("Clear");
		m_btnClear.addActionListener(new ActionController(this));
		m_btnClear.setBounds(360, 10, 80, 60);
		panel.add(m_btnClear);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionController(this));
		btnSend.setBounds(840, 10, 100, 30);
		panel.add(btnSend);
		JLabel lblMesaj = new JLabel();
		lblMesaj.setText("Mesaj:");
		lblMesaj.setBounds(500, 10, 100, 30);
		panel.add(lblMesaj);

		JTextField txtMesaj = new JTextField();
		txtMesaj.setBounds(620, 10, 200, 30);
		panel.add(txtMesaj);


			

		areaMesaje = new JTextArea();
		m_areaScollPane = new JScrollPane(areaMesaje);
		m_areaScollPane.setBounds(500, 50, 440, 200);
		panel.add(m_areaScollPane);

		add(panel);
		pack();
		setSize(1000, 600);
		setVisible(true);
	}

	public void connect() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();

			socket = new Socket(address, 9091);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			InputStreamReader dis = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(dis);
			arrayList = new CarsList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void sendMessage(String action, Car car) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", action);
		if (action.equalsIgnoreCase("update") || action.equalsIgnoreCase("insert")
				|| action.equalsIgnoreCase("delete")) {
			JSONArray content = new JSONArray();
			JSONObject item = new JSONObject();
			item.put("id", car.getId());
			item.put("producer", car.getProducer());
			item.put("model", car.getModel());
			item.put("price", car.getPrice());
			item.put("year", car.getYear());
			content.add(item);
			jsonObject.put("content", content);
		}
		String message = jsonObject.toString();
		if (writer != null) {
			try {
				writer.write(message);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			areaMesaje.append("Client: " + message + "\n");
		}
	}

	public void readMessage() {
		if (reader != null) {
			try {
				String message = reader.readLine();
				areaMesaje.append("Server: " + message + "\n");
				if (message.contains("retrieve")) {
					try {
						arrayList = parseJSON(message);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setScrollPane(arrayList);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public CarsList parseJSON(String string) throws FileNotFoundException, ParseException {
		CarsList carsList = new CarsList();
		JSONParser parser = new JSONParser();

		Object object = parser.parse(string);

		JSONObject jsonObject = (JSONObject) object;

		JSONObject jsonContent = (JSONObject) jsonObject;
		JSONArray content = (JSONArray) jsonContent.get("content");

		for (Object cat : content) {
			JSONObject tmpObj = (JSONObject) cat;
			Integer id = ((Long) tmpObj.get("id")).intValue();
			String producer = (String) tmpObj.get("producer");
			String model = (String) tmpObj.get("model");
			Integer price = ((Long) tmpObj.get("price")).intValue();
			Integer year = ((Long) tmpObj.get("year")).intValue();
			carsList.Add(new Car(id, producer, model, price, year));
		}
		return carsList;
	}

	public void setScrollPane(CarsList list) {
		m_model.setCars(list);
		m_carsTable.setModel(m_model);
		m_scrollPane.setViewportView(m_carsTable);
	}

	public JTextField getTxtMessage() {
		return txtMessage;
	}

	public JTable getTabel() {
		return m_carsTable;
	}

	public CarTableModel getModel() {
		return m_model;
	}

	public void setTabel(JTable m_carsTable) {
		this.m_carsTable = m_carsTable;
	}

	public void setModel(CarTableModel m_editCarModel) {
		this.m_model = m_editCarModel;
	}

	public static void main(String[] args) {
		new ClientMain();

	}

	/*
	 * clientul: Cerere: - impacheteaza cererile catre server in XML/ (optional
	 * JSON): - pentru fiecare tip de cerere adauga date sumplimentare: - de exemplu
	 * pentru update adauga obiectul data care se doreste a fi actualizat Raspuns: -
	 * parseaza raspunsul serverului: - de exemplu pentru operatia select primeste o
	 * lista de obiecte care trebuie stocate in modelul de date Cerere si raspuns: -
	 * actualizeaza interfata
	 * 
	 * Cererile: - update <comand><action>update</action><content> <elev id="value">
	 * <nume> value </nume> <prenume> value </prenume> <varsta> value </varsta>
	 * </elev> </content> </comand> - insert
	 * <comand><action>insert</action><content> <elev id="value"> <nume> value
	 * </nume> <prenume> value </prenume> <varsta> value </varsta> </elev>
	 * </content> </comand> - delete <comand><action>delete</action><content> <elev
	 * id="value"> <nume> value </nume> <prenume> value </prenume> <varsta> value
	 * </varsta> </elev> </content> </comand> - select
	 * <comand><action>select</action></comand> -clear
	 * <comand><action>clear</action></comand>
	 * 
	 */
}
