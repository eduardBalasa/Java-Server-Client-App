package model;


import java.util.ArrayList;
import java.util.List;

public class CarsList {
	private List<Car> m_carsList;

	public CarsList() {
		m_carsList = new ArrayList<Car>();
	}

	public int GetSize() {
		return m_carsList.size();
	}

	public Car Get(int index) {
		if (index < 0 || index >= m_carsList.size()) {
			return null;
		}

		return m_carsList.get(index);
	}

	public boolean Add(Car masina) {
		if (masina == null) {
			return false;
		}

		return m_carsList.add(masina);
	}

	public int getID(int index) {
		return m_carsList.get(index).getId();
	}
	
	public Car getCarAt(int index) 
	{
		if(index < 0 || index >= m_carsList.size())
		{
			return null;
		}
		return m_carsList.get(index);
	}

	public Object[] toArray() {

		return m_carsList.toArray();
	}
	
	public ArrayList<Car> toArray(CarsList carList)
	{
		ArrayList<Car> arrayList = new ArrayList<Car>();
		for(int i = 0; i < carList.GetSize(); i++)
		{
			arrayList.add(carList.getCarAt(i));
		}
		return arrayList;
		
	}
}
