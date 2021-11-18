import java.util.Date;

public class TaskBean implements Comparable {
	private String name;
	private String desc;
	private Date createdDate;
	private Date endDate;
	private int priority;
	private String status;
	private String tags;

	public TaskBean()
	{
		
	}

	public TaskBean(String name, String desc, Date createdDate, Date endDate, int priority, String status,
			String tags) {
		super();
		this.name = name;
		this.desc = desc;
		this.createdDate = createdDate;
		this.endDate = endDate;
		this.priority = priority;
		this.status = status;
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	@Override
	public String toString() {
		return "name=" + name 
		   +"   desc=" + desc 
	       +"   createdDate=" + createdDate
   	       +"   endDate=" + endDate
	       +"   priority=" + priority 
	       +"   status=" + status 
		   +"   tags=" + tags;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof TaskBean)
		{
			TaskBean t = (TaskBean)o;
			if(this.name.equals(t.name) && this.desc.equals(t.desc) && this.createdDate.equals(t.createdDate) && this.endDate.equals(t.endDate) && (this.priority==t.priority) && this.status.equals(t.status) && this.tags.equals(t.tags))
			{
				return true;
			}
		}
		return false;
	}
	public int hashCode()
	{
		return (""+name+desc+createdDate+endDate+priority+status+tags).hashCode();
	}
	
	public int compareTo(Object o)
	{
		if(o instanceof TaskBean)
		{
			TaskBean t = (TaskBean)o;
			return this.name.compareTo(t.name);
		}
		else
		{
			throw new IllegalArgumentException("Only TaskBean is accepted");
		}
	}
}



/*
name!
- desc!
- cr_dt!
- end_dt!
- priority!
- status!
- tags!*/