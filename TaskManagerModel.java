import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskManagerModel {
	
	public boolean createCategory(String cat) throws FileAlreadyExistsException
	{
		String path = Constants.PATH+"\\"+cat+".txt";
		File f = new File(path);
		if(f.exists())
		{
			throw new FileAlreadyExistsException("Category already exists, Please Give any other name");
		}
		else
		{
			try
			{
			f.createNewFile();
			return true;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	private String addTaskHere(File file, String addDetails) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
		bw.write(addDetails);
		bw.newLine();
		bw.close();
		return addDetails;
	}
	
	public boolean checkCategoryExists(String catName)
	{
		String path = Constants.PATH+"\\"+catName+".txt";
		File file = new File(path);
		if(file.exists())
		{
			return true;
		}
		return false;
	}
	
	private boolean writeToFile(List<TaskBean> list, String catName) throws FileNotFoundException, IOException
	{
		String path = ""+Constants.PATH+"\\"+catName+".txt";
		File f = new File(path);
		if(f.exists())
		{
		    BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    for(TaskBean tb : list)
		    {
		    	String addDetails = ""+tb.getName()+":"+tb.getDesc()+":"+sdf.format(tb.getCreatedDate())+":"+sdf.format(tb.getEndDate())+":"+tb.getPriority()+":"+tb.getStatus()+":"+tb.getTags();
		    	bw.write(addDetails);
			    bw.newLine();
		    }
		  bw.close();
		  return true;
		}
		else
		{
			throw new FileNotFoundException("The category is not exist");
		}
		
	}
	
	private List<String> readFromFile(File f) throws FileNotFoundException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		List<String> strList = new ArrayList<String>();
		while((line=br.readLine())!=null)
		{
			strList.add(line);
		}
		return strList;
	}
	
	public String addTask(TaskBean tb,String catName) throws IOException, FileNotFoundException, TaskAlreadyExistsException
	{
		String path = Constants.PATH+"\\"+catName+".txt";
		File file = new File(path);
		System.out.println(file.toString());
		String name= tb.getName();
		String result;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String addDetails = ""+tb.getName()+":"+tb.getDesc()+":"+sdf.format(tb.getCreatedDate())+":"+sdf.format(tb.getEndDate())+":"+tb.getPriority()+":"+tb.getStatus()+":"+tb.getTags();
		if(!(file.exists()))
		{
			throw new FileNotFoundException("category not exist");	
		}
		if(file.length()==0)
		{
		    result = addTaskHere(file,addDetails);
			return result;
		}
		else  //if file.length()>0
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line=br.readLine())!=null)
			{
				if(line.startsWith(name))
				{
					throw new TaskAlreadyExistsException("This task is already exists");
				}
			}
			result = addTaskHere(file,addDetails);
			br.close();
			return(result);
		}
	}
	
	public List<TaskBean> ListTasks(String catName) throws FileNotFoundException, IOException, ParseException
	{
		String path = ""+Constants.PATH+"\\"+catName+".txt";
		BufferedReader br = new BufferedReader(new FileReader(path));
	    String line="";
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date d=null;
	    List<TaskBean> listTask = new ArrayList<TaskBean>();
	    String[] str=null;
	    TaskBean t;
	    while((line=br.readLine())!=null)
	    {
	    	/*if(line.trim().equals(""))
	    	{
	    		continue;
	    	}*/
	    	str=line.split(":");
	        //System.out.println(line);
	    	t= new TaskBean();
	    	//System.out.println("length : "+str.length);
	    	for(int i=0;i<str.length;i++)
	    	{
	    		if(i==0)
	    		{
	    			t.setName(str[0]);
	    			
	    		}
	    		if(i==1)
	    		{
	    			t.setDesc(str[1]);
	    		}
	    		if(i==2)
	    		{
	    			String s =str[2];
	    			d=sdf.parse(s);
	    			t.setCreatedDate(d);
	    		}
	    		if(i==3)
	    		{
	    			String s = str[3];
	    			d=sdf.parse(s);
	    			t.setEndDate(d);
	    		}
	    		if(i==4)
	    		{
	    			int m = Integer.parseInt(str[4]);
	    			t.setPriority(m);
	    		}
	    		if(i==5)
	    		{
	    			t.setStatus(str[5]);
	    		}
	    		if(i==6)
	    		{
	    			t.setTags(str[6]);
	    		}
	    	}
	    	listTask.add(t);
	    }
	    br.close();
	    //System.out.println(listTask);
	    return listTask;
	}
	
	public TaskBean getTaskBean(String catName,String taskName) throws FileNotFoundException, IOException, ParseException
	{
	    List<TaskBean> list= new ArrayList<TaskBean>();
	    list = ListTasks(catName);
	    for(TaskBean t : list)
	    {
	    	if(t.getName().equals(taskName))
	    	{
	    		return t;
	    	}
	    }
	    throw new FileNotFoundException("the task does not exist in File");
	    
	}
	public boolean update(String catName,TaskBean old, TaskBean newer) throws FileNotFoundException, IOException, ParseException
	{
		List<TaskBean> list;
		list = ListTasks(catName);	
		boolean b;
		if(list.contains(old))
		{
			int x = list.indexOf(old);
			list.remove(x);
			list.add(x, newer);
			b = writeToFile(list,catName);
			if(b==true)
			   return true;
			throw new FileNotFoundException("Category not exist");
		}
		else
		{
			throw new FileNotFoundException("task not exist");
		}
	}
	public String removeTask(String catName, String taskName) throws FileNotFoundException, IOException, ParseException
	{
		List<TaskBean> list = ListTasks(catName);
		boolean b = false;
		for(TaskBean tb : list)
		{
			if(tb.getName().equals(taskName))
			{
				list.remove(tb);
				writeToFile(list,catName);
                b = true;
			    break;
			}
		}
		if(b==true)
		{
			return Constants.SUCCESS;
		}
		else
		{
			throw new FileNotFoundException("this task is not exist to remove");
		}
	}
	
	public List<TaskBean> alphbhetList(String catName) throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanAlphbhetComparator tba = new TaskBeanAlphbhetComparator();
		List<TaskBean> list= ListTasks(catName);
		Collections.sort(list, tba);
		return list;
	}
	public List<TaskBean> dueDateList(String catName) throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanDueDateComparator tbd = new TaskBeanDueDateComparator();
		List<TaskBean> list = ListTasks(catName);
		Collections.sort(list, tbd);
		return list;
	}
	public List<TaskBean> createDateList(String catName) throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanCreateDateComparator tbc = new TaskBeanCreateDateComparator();
		List<TaskBean> list = ListTasks(catName);
		Collections.sort(list, tbc);
		return list;
	}
	public List<TaskBean> longTimeDateList(String catName) throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanLongestTimeComparator tlt = new TaskBeanLongestTimeComparator();
		List<TaskBean> list = ListTasks(catName);
		Collections.sort(list, tlt);
		return list;
	}
	
	public List<TaskBean> listAllTaskBasedOnAlpahbet() throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanAlphbhetComparator tbac = new TaskBeanAlphbhetComparator();
		String path = Constants.PATH;
		File f = new File(path);
		List<TaskBean> totalList = new ArrayList<TaskBean>();
		File[] file = f.listFiles();
		StringBuilder sb;
		String s;
		for(File fi : file)
		{
			s=fi.getName();
			s=s.substring(0, (s.length()-4));
			List<TaskBean> list = ListTasks(s);
			totalList.addAll(list);
		}
		Collections.sort(totalList, tbac);
		return totalList;
	}
	
	public List<TaskBean> listAllTaskBasedOnDueDate() throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanDueDateComparator tbdd = new TaskBeanDueDateComparator();
		String path = Constants.PATH;
		File f = new File(path);
		List<TaskBean> totalList = new ArrayList<TaskBean>();
		File[] file = f.listFiles();
		StringBuilder sb;
		String s;
		for(File fi : file)
		{
			s=fi.getName();
			s=s.substring(0, (s.length()-4));
			List<TaskBean> list = ListTasks(s);
			totalList.addAll(list);
		}
		Collections.sort(totalList, tbdd);
		return totalList;
	}
	
	public List<TaskBean> listAllTaskBasedOnCreatedDate() throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanCreateDateComparator tbcc = new TaskBeanCreateDateComparator();
		String path = Constants.PATH;
		File f = new File(path);
		List<TaskBean> totalList = new ArrayList<TaskBean>();
		File[] file = f.listFiles();
		StringBuilder sb;
		String s;
		for(File fi : file)
		{
			s=fi.getName();
			s=s.substring(0, (s.length()-4));
			List<TaskBean> list = ListTasks(s);
			totalList.addAll(list);
		}
		Collections.sort(totalList, tbcc);
		return totalList;
	}
	
	public List<TaskBean> listAllTaskBasedOnLongestDate() throws FileNotFoundException, IOException, ParseException
	{
		TaskBeanLongestTimeComparator tblt = new TaskBeanLongestTimeComparator();
		String path = Constants.PATH;
		File f = new File(path);
		List<TaskBean> totalList = new ArrayList<TaskBean>();
		File[] file = f.listFiles();
		StringBuilder sb;
		String s;
		for(File fi : file)
		{
			s=fi.getName();
			s=s.substring(0, (s.length()-4));
			List<TaskBean> list = ListTasks(s);
			totalList.addAll(list);
		}
		Collections.sort(totalList, tblt);
		return totalList;
	}
	
	public List<TaskBean> search(String catName,String str) throws FileNotFoundException, IOException, ParseException
	{
		String path = Constants.PATH+"\\"+catName+".txt";
		File file = new File(path);
		boolean b=false;
		List<String> list = readFromFile(file);
		for(String s : list)
		{
			if(str.equals(s))
			{
				b = true;
				break;
			}
		}
		if(b==true)
		{
			List<TaskBean> beanList = ListTasks(catName);
			return beanList;
		}
		else
		{
			throw new FileNotFoundException("Entered task string does not exist");
		}
		
	}
	/*public static void main(String[] args)
	{
		TaskManagerModel tmm = new TaskManagerModel();
		try
		{
		/*System.out.println("here");
		List<TaskBean> l = tmm.search("Reading");
		System.out.println(l);
		
		TaskBean oldtb = tmm.getTaskBean("Reading", "python");
		//System.out.println("old task: "+oldtb);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date cd = sdf.parse("12/10/2021");
		Date ed = sdf.parse("13/08/2021");
		TaskBean newtb = new TaskBean("Java","corePHP",cd,ed,8,Constants.NEW,"learn Php");
		Boolean b=tmm.update("Reading", oldtb, newtb);
		System.out.println("b :"+b);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}*/
}


