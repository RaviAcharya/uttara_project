import java.util.Comparator;

public class TaskBeanAlphbhetComparator implements Comparator{
	
	public int compare(Object o1, Object o2)
	{
		if((o1 instanceof TaskBean) && (o2 instanceof TaskBean))
		{
			TaskBean tb1 = (TaskBean)o1;
			TaskBean tb2 = (TaskBean)o2;
			return (tb1.getName().compareTo(tb2.getName()));
		}
		else
		{
			throw new IllegalArgumentException("Only TaskBean is accepted");
		}
	}

}
