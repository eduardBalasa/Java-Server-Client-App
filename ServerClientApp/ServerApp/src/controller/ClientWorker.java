package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Car;
import model.CarsList;

public class ClientWorker implements Runnable {
	private Socket socket;
	private DBConnection conn;

	public ClientWorker(Socket socket, DBConnection conn) {
		this.socket = socket;
		this.conn = conn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {

			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			InputStreamReader dis = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(dis);
			while (true) {
				StringBuffer sf = new StringBuffer();
				String s;
				while (true) {
					s = br.readLine();
					sf.append(s);
					if (s.endsWith("}")) {
						break;
					}
				}

				List<Object> r = parseJSON(sf.toString());

				String message = (String) r.get(0);
				System.out.println("Clientul spune:" + sf);

				JSONObject json = new JSONObject();

				if (message.equalsIgnoreCase("data")) {
					String data = Calendar.getInstance().getTime().toString();
					json.put("action", message);
					json.put("content", data);
					pw.println(json.toString());
				} else if (message.equalsIgnoreCase(".")) {
					pw.println("am inchis");
					pw.flush();
					break;
				} else if (message.equalsIgnoreCase("update")) {
					conn.update((Car) r.get(1));
					json.put("action", message);
					json.put("content", "ok");
					pw.println(json.toString());
				} else if (message.equalsIgnoreCase("insert")) {
					conn.insert((Car) r.get(1));
					json.put("action", message);
					json.put("content", "ok");
					pw.println(json.toString());
				} else if (message.equalsIgnoreCase("retrieve")) {

					json.put("action", message);
					CarsList cars = conn.retrieve();
					ArrayList<Car> listMasini = cars.toArray(cars);

					JSONArray array = new JSONArray();
					for (Car car : listMasini) {
						JSONObject item = new JSONObject();
						item.put("id", car.getId());
						item.put("producer", car.getProducer());
						item.put("model", car.getModel());
						item.put("price", car.getPrice());
						item.put("year", car.getYear());
						array.add(item);
					}
					json.put("content", array);
					pw.println(json.toString());
				} else if (message.contains("delete")) {
					conn.delete((Car) r.get(1));
					json.put("action", message);
					json.put("content", "ok");
					pw.print(json.toString());
				} else if (message.contains("clear")) {
					conn.clear();
					json.put("action", message);
					json.put("content", "ok");
					pw.print(json.toString());
				} else {
					json.put("action", message);
					json.put("content", "ok");
					pw.print(json.toString());
				}
				pw.flush();
			}
		} catch (Exception ex) {

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<Object> parseJSON(String sf) throws ParseException {
		List<Object> carsList = new ArrayList<Object>();

		JSONParser parser = new JSONParser();
		Object object = parser.parse(sf);

		JSONObject jsonObject = (JSONObject) object;

		JSONObject jsonAction = (JSONObject) jsonObject;
		String action = (String) jsonAction.get("action");
		carsList.add(action);

		JSONObject jsonContent = (JSONObject) jsonObject;
		JSONArray content = (JSONArray) jsonContent.get("content");
		if (content != null) {
			for (Object cat : content) {
				JSONObject tmpObj = (JSONObject) cat;
				Integer id = ((Long) tmpObj.get("id")).intValue();
				String producer = (String) tmpObj.get("producer");
				String model = (String) tmpObj.get("model");
				Integer price = ((Long) tmpObj.get("price")).intValue();
				Integer year = ((Long) tmpObj.get("year")).intValue();
				Car newCar = new Car(id, producer, model, price, year);
				carsList.add(newCar);
			}
		}
		return carsList;
	}
}
