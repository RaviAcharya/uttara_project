import java.util.Comparator;

public class TaskBeanDueDateComparator implements Comparator{
	
	public int compare(Object o1, Object o2)
	{
		if((o1 instanceof TaskBean) && (o2 instanceof TaskBean))
		{
			TaskBean tb1 = (TaskBean)o1;
			TaskBean tb2 = (TaskBean)o2;
			return (tb1.getEndDate().compareTo(tb2.getEndDate()));
		}
		else
		{
			throw new IllegalArgumentException("Only TaskBean is accepted");
		}
	}

}
