package paint.model;

/**
 * 
 * @author Vit
 *
 */
public abstract class Tool extends Model 
{
	/**
	 * ���������� ����������, ��������� ��� ���������� �������� ���������
	 */
	public abstract void activate();
	/**
	 * ������������ ����������
	 */
	public abstract void deactivate();
}
