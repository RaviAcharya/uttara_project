import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TaskBeanLongestTimeComparator implements Comparator{
	
	public int compare(Object o1, Object o2)
	{
	    if((o1 instanceof TaskBean) && (o2 instanceof TaskBean))
		{
			TaskBean tb1 = (TaskBean)o1;
			TaskBean tb2 = (TaskBean)o2;
			int dif1 =(int) ((tb1.getEndDate().getTime()-tb1.getCreatedDate().getTime())/(1000*60*60*24));
			int dif2 =(int) ((tb2.getEndDate().getTime()-tb2.getCreatedDate().getTime())/(1000*60*60*24));
			return (int)(dif2-dif1);
		}
		else
		{
			throw new IllegalArgumentException("Only TaskBean is accepted");
		}
   }
}
