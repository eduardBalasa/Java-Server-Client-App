package model;

public class Car {
	private int m_id;
	private int m_year;
	private int m_price;
	private String m_producer;
	private String m_model;

	public Car() {
		super();
		m_id = 0;
		m_year = 0;
		m_price = 0;
		m_producer = "";
		m_model = "";
	}

	public Car(int id, String producer, String model, int price, int year) {
		super();
		m_id = id;
		m_producer = producer;
		m_model = model;
		m_price = price;
		m_year = year;
	}


	public void setPrice(int price) {
		this.m_price = price;
	}

	public void setId(int id) {
		m_id = id;
	}

	public void setProducer(String producer) {
		this.m_producer = producer;
	}

	public void setModel(String model) {
		this.m_model = model;
	}

	public void setYear(int year) {
		this.m_year = year;
	}

	@Override
	public String toString() {

		return "id:" + m_id + "\nproducer:" + m_producer + "\nmodel:" + m_model + "\nprice:" + m_price + "\nyear"
				+ m_year;
	}

	public Object GetAttribute(int index) {
		switch (index) {
		case 0:
			return m_id;
		case 1:
			return m_producer;
		case 2:
			return m_model;
		case 3:
			return m_price;
		case 4:
			return m_year;

		}
		return null;
	}

	static String GetAttributeName(int index) {
		switch (index) {
		case 0:
			return "Id";
		case 1:
			return "Producer";
		case 2:
			return "Model";
		case 3:
			return "Year";
		case 4:
			return "Price";
		}
		return "";
	}

	public int getId() {
		return m_id;
	}

	public String getProducer() {
		return m_producer;
	}

	public String getModel() {
		return m_model;
	}

	public int getPrice() {
		return m_price;
	}

	public int getYear() {
		return m_year;
	}

	public void update(Car car) {

		m_id = car.getId();
		m_producer = car.getProducer();
		m_model = car.getModel();
		m_price = car.getPrice();
		m_year = car.getYear();

	}
}
