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

	public boolean Add(Car car) {
		if (car == null) {
			return false;
		}

		return m_carsList.add(car);
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
	
	public void removeCarAt(int index) 
	{
		
		m_carsList.remove(index);
		
	}

	public Object[] toArray() {

		return m_carsList.toArray();
	}
}
