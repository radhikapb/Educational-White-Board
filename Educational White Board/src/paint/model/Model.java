package paint.model;

import java.util.LinkedHashSet;
import paint.view.View;


/**
 * ����� - ������, ���������� � ����� ���������� ������������ ���� View
 * @author Vit
 *
 */
public class Model 
{
	/**
	 * ������ ������������
	 */
	private LinkedHashSet<View> _observers = new LinkedHashSet<>(0);
	/**
	 * �������� ����������� 
	 * @param observer ����� �����������
	 */
	public void attach(View observer)
	{
		_observers.add(observer);
	}
	/**
	 * ��������� ����������� 
	 * @param observer �����������
	 */
	public void detach(View observer)
	{
		_observers.remove(observer);
	}
	/**
	 * �������� ������������ �� ����������
	 */
	public void update()
	{
		for(View observer : _observers)
			observer.refresh();
	}
}
