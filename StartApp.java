import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StartApp {
	//validating String inputs
	public static String validationOfString(String str)
	{
		if(str==null)
		{
			throw new IllegalArgumentException("Null cannot be category name, enter proper name");
		}
		if(str.trim().equals(""))
		{
			throw new IllegalArgumentException("Invalid category name");
		}
		else
		{
			return str;
		}
		
	}
	//validating date input
	public static Date dateValidation(String date)
	{
		if(date==null)
		{
			throw new IllegalArgumentException("date cannot be null");
		}
		if(date.trim().equals(""))
		{
			throw new IllegalArgumentException("date cannot be empty");
		}
		else
		{
			try
			{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(date);
			return d;
			}
			catch (ParseException e)
			{
			    System.out.println(e.getMessage());
			}
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		
		String category;
		int ch1=0;
		int ch2=0;
		String taskName;
		String description;
		Date createdDate=null;
		Date endDate=null;
		int priority;
		String status=null;
		String tags;
		TaskManagerModel tmm = new TaskManagerModel();
		StartApp tsa = new StartApp();
		while(ch1!=5)
		{
			System.out.println("---MAIN MENU---");
	     	System.out.println("Enter 1 to Create Category");
	    	System.out.println("Enter 2 to Load Category");
		    System.out.println("Enter 3 to search");
	    	System.out.println("Enter 4 to List ");
	    	System.out.println("Enter 5 to Exit");
	    	System.out.println("Enter a choice");
	    	while(!(sc1.hasNextInt()))
	    	{
	    		System.out.println("Enter int only");
	    		sc1.next();
	    	}
	        ch1 = sc1.nextInt();
	        switch(ch1)
	        {
	            case 1 :
	            	System.out.println("Enter the the category name to be created");
	            	//validate the input
	            	category = validationOfString(sc2.nextLine());
	            	boolean created;
	                boolean exist = tmm.checkCategoryExists(category);
	                if(!exist)
	                {
	                	System.out.println("Category entered does not exist");
	                }
	            	try
	            	{
	            	created=tmm.createCategory(category);
	            	if(created)
	            	{
	            		System.out.println("new category created");
	            	}
	            	else
	            	{
	            		System.out.println("category not created");
	            	}
	            	}
	            	catch (FileAlreadyExistsException e)
	            	{
	            		System.out.println(e.getMessage());
	            	}
	            	System.out.println("---Inner Menu---"+"...you can do below operations on ->"+category+"<- category now");
	            	tsa.switchCases(category); //Invoking Inner menu
	            	System.out.println("");
	            	
	            	break;
	                //Continuing with Main Menu
	          case 2 : 
	        	   System.out.println("Enter category");
	        	   category = validationOfString(sc2.nextLine());
	        	   exist = tmm.checkCategoryExists(category);
	        	   if(!exist)
	        	   {
	        		   throw new IllegalArgumentException("category not exists");
	        	   }
	        	   else
	        	   {
	        		    System.out.println("Category Loaded... you can do below operation");
	        		    tsa.switchCases(category); //Invoking Inner menu
	        	   }
	        	   break;
	        	   //continue with Main Menu
	          case 3 :
	        	  System.out.println("Enter category name to search");
	        	  category = validationOfString(sc2.nextLine());
	        	   exist = tmm.checkCategoryExists(category);
	        	   if(!exist)
	        	   {
	        		   throw new IllegalArgumentException("category not exists");
	        	   }
	        	   else
	        	   {
	        		   System.out.println("Enter the whole task String by seperating words with : this");
        	        	taskName = validationOfString(sc2.nextLine());
        	        	try
        	        	{
        	        	    List<TaskBean> list = tmm.search(category, taskName);
        	        	    if(list!=null)
        	        	    {	            	   
        	        	    	for(TaskBean t : list)
        	        	    	{
        	        	    		System.out.println(t);
        	        	    		System.out.println("");
        	        	    	}
        	        	    }
        	        	    else
        	        	    {
        	        	    	System.out.println("The String does not exist in file");
        	        	    }
        	        	}
        	        	catch (FileNotFoundException e)
        	        	{
        	        		System.out.println(e.getLocalizedMessage());
        	        	}
        	        	catch (IOException e)
        	        	{
        	        		System.out.println(e.getMessage());
        	        	}
        	        	catch (ParseException e)
        	        	{
        	        		System.out.println(e.getMessage());
        	        	}
        	        	catch (Exception e)
        	        	{
        	        		System.out.println(e.getMessage());
        	        	}
	        		    
	        	   }
	        	   break;
	          case 4 :
	        	  System.out.println("Enter 1 to list the Task by category name");
	        	  System.out.println("Enter 2 to list the all tasks");
	        	  String s=""; 
	        	  while(!sc1.hasNextInt())
	        	  {
	        		  System.out.println("Enter only 1 or 2");
	        		  s=sc1.next();
	        	  }
	        	  int x = sc1.nextInt();
	        	  if(x!=1 && x!=2)
	        	  {
	        		  throw new IllegalArgumentException("Enter only 1 or 2");
	        	  }
	        	  if(x==1)
	        	  {
	        		  System.out.println("Enter the category name");
	        		  category = validationOfString(sc2.nextLine());
	        		  int ch3 =0;
        	        	while(ch3!=5)
        	        	{
        	        	System.out.println("Enter 1 to list the task based on alphabhetical listing of name");
        	        	System.out.println("Enter 2 to list the task based on Due date");
        	        	System.out.println("Enter 3 to list the task based on Create date");
        	        	System.out.println("Enter 4 to list the task based on longest time");
        	        	System.out.println("Enter 5 to come back");
        	        	System.out.println("Enter your choice");
        	        	while(!sc1.hasNextInt())
        	        	{
        	        	  System.out.println("Enter int only");
        	        	  sc1.next();
        	        	}
        	        	ch3 = sc1.nextInt();
        	        	switch(ch3)
        	        	{
        	        	    case 1 :
        	        	         try
        	        	         {
        	        	         	List<TaskBean> list = tmm.alphbhetList(category);
        	        		        for(TaskBean tb: list)
        	        		        {
        	        		        	System.out.println(tb);
        	        		        }
        	        	         }
        	        	         catch (FileNotFoundException e)
        	        	         {
        	        	        	System.out.println(e.getMessage());
        	        	         }
        	        	         catch (IOException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (ParseException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (Exception e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         break;
        	        	    case 2 :
        	        	    	try
        	        	         {
        	        	         	List<TaskBean> list = tmm.dueDateList(category);
        	        	         	for(TaskBean tb: list)
        	        		        {
        	        		        	System.out.println(tb);
        	        		        }
        	        	         }
        	        	         catch (FileNotFoundException e)
        	        	         {
        	        	        	System.out.println(e.getMessage());
        	        	         }
        	        	         catch (IOException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (ParseException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (Exception e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         break;
        	        	    case 3 :
        	        	    	 try
        	        	         {
        	        	         	List<TaskBean> list = tmm.createDateList(category);
        	        	         	for(TaskBean tb: list)
        	        		        {
        	        		        	System.out.println(tb);
        	        		        }
        	        	         }
        	        	         catch (FileNotFoundException e)
        	        	         {
        	        	        	System.out.println(e.getMessage());
        	        	         }
        	        	         catch (IOException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (ParseException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (Exception e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         break;
        	        	   case 4 :
        	        		   try
        	        	         {
        	        	         	List<TaskBean> list = tmm.longTimeDateList(category);
        	        	         	for(TaskBean tb: list)
        	        		        {
        	        		        	System.out.println(tb);
        	        		        }
        	        	         }
        	        	         catch (FileNotFoundException e)
        	        	         {
        	        	        	System.out.println(e.getMessage());
        	        	         }
        	        	         catch (IOException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (ParseException e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         catch (Exception e)
        	        	         {
        	        		        System.out.println(e.getMessage());
        	        	         }
        	        	         break;
        	        	}
        	        	}
	        	  }
	        	  if(x==2)
	        	  {
	        		    int ch3 =0;
        	        	while(ch3!=5)
        	        	{
        	        	System.out.println("Enter 1 to list the task based on alphabhetical listing of name");
        	        	System.out.println("Enter 2 to list the task based on Due date");
        	        	System.out.println("Enter 3 to list the task based on Create date");
        	        	System.out.println("Enter 4 to list the task based on longest time");
        	        	System.out.println("Enter 5 to come back");
        	        	System.out.println("Enter your choice");
        	        	while(!sc1.hasNextInt())
        	        	{
        	        	  System.out.println("Enter int only");
        	        	  sc1.next();
        	        	}
        	        	ch3 = sc1.nextInt();
        	        	switch(ch3)
        	        	{
        	        	    case 1 :
        	        	        try
        	        	        {
        	        	    	   List<TaskBean> list = tmm.listAllTaskBasedOnAlpahbet();
        	        	    	   for(TaskBean tb: list)
       	        		          {
       	        		        	System.out.println(tb);
       	        		          }
        	        	        }
        	        	        catch (FileNotFoundException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (IOException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (ParseException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (Exception e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        break;
        	        	    case 2 :
        	        	    	try
        	        	        {
        	        	    	   List<TaskBean> list = tmm.listAllTaskBasedOnDueDate();
        	        	    	   for(TaskBean tb: list)
       	        		           {
       	        		        	System.out.println(tb);
       	        		           }
        	        	        }
        	        	        catch (FileNotFoundException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (IOException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (ParseException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (Exception e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        break;
        	        	    case 3 :
        	        	    	try
        	        	        {
        	        	    	   List<TaskBean> list = tmm.listAllTaskBasedOnCreatedDate();
        	        	    	   for(TaskBean tb: list)
       	        		           {
       	        		        	System.out.println(tb);
       	        		           }
        	        	        }
        	        	        catch (FileNotFoundException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (IOException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (ParseException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (Exception e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	    	break;
        	        	    case 4 :
        	        	    	try
        	        	        {
        	        	    	   List<TaskBean> list = tmm.listAllTaskBasedOnLongestDate();
        	        	    	   for(TaskBean tb: list)
       	        		          {
       	        		        	System.out.println(tb);
       	        		          }
        	        	        }
        	        	        catch (FileNotFoundException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (IOException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (ParseException e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        catch (Exception e)
        	        	        {
        	        	        	System.out.println(e.getMessage());
        	        	        }
        	        	        break;
        	        	}
	        	  }   
	        }
		}
	}
	}
	
	//Inner Menu
    public void switchCases(String category)
    {
    	Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
	
		int ch1=0;
		int ch2=0;
		String taskName;
		String description;
		Date createdDate=null;
		Date endDate=null;
		int priority;
		String status=null;
		String tags;
		TaskManagerModel tmm = new TaskManagerModel();
    	
    	while(ch2!=6)
        {
    	    System.out.println("Enter 1 to Add the Task to the Category");
    	    System.out.println("Enter 2 to Edit a Task");
    	    System.out.println("Enter 3 to remove Task");
    	    System.out.println("Enter 4 to List the task");
    	    System.out.println("Enter 5 to Search the task");
    	    System.out.println("Enter 6 to Come back");
    	    System.out.println("Enter a number given in menu");
    	    while((!sc1.hasNextInt()))
    	    {
    	    	System.out.println("Enter only int");
    	    	sc1.next();
    	    }
    	    ch2=sc1.nextInt();
    	    switch(ch2)
    	    {
    	        case 1 :
    	        	System.out.println("Enter name of the task");
    	        	taskName = validationOfString(sc2.nextLine());
    	        	System.out.println("Enter description");
    	        	description =validationOfString(sc2.nextLine());
    	        	System.out.println("Enter created date in date/month/year format");
    	        	Date d = dateValidation(sc2.nextLine());
    	        	if(d==null)
    	        	{
    	        		System.out.println("created date entered is invalid");
    	        	}
    	        	else
    	        	{
    	        		createdDate = d;
    	        	}
    	        	System.out.println("Enter end date in date/month/year format");
    	        	d = dateValidation(sc2.nextLine());
    	        	if(d==null)
    	        	{
    	        		System.out.println("end date entered is invalid");
    	        	}
    	        	else
    	        	{
    	        		endDate = d;
    	        	}
    	        	System.out.println("Enter priority for the task ranges from 1-10.. (1 being lowest and 10 being heighest");
    	        	while((!sc1.hasNextInt()))
            	    {
            	    	System.out.println("Enter only int");
            	    	sc1.next();
            	    }
    	        	int x = sc1.nextInt();
    	        	if(x<1 && x>10)
    	        	{
    	        		throw new IllegalArgumentException("priority should be >1 and <11");            	      
    	        	}
    	        	else
    	        	{
    	        		priority = x;
    	        	}
    	        	System.out.println("Enter the satatus of the task");
    	        	System.out.println("enter 1-> status is NEWTASKs");
    	        	System.out.println("enter 2-> status is IN PROCESS");
    	        	System.out.println("enter 3-> status is COMPLETED");
    	        	System.out.println("Enter your task status->");
  	        	    while(!sc1.hasNextInt())
  	        	    {
  	        		  System.out.println("Enter only 1 or 2 or 3");
  	        		  sc1.next();
  	        	    }
  	        	    int num =Integer.parseInt(sc1.next());
    	        	if(num<1 || num>3)
    	        	{
    	        		throw new IllegalArgumentException("Enter number btween 1 & 3");
    	        	}
    	        	if(num==1)
    	        	{
    	        		status = Constants.NEW;
    	        	}
    	        	if(num==2)
    	        	{
    	        		status = Constants.INPROCESS;
    	        	}
    	        	if(num==3)
    	        	{
    	        		status = Constants.COMPLETE;
    	        	}
    	        	System.out.println("Enter tag for the task");
    	        	tags = validationOfString(sc2.nextLine());
    	        	TaskBean tb = new TaskBean(taskName,description,createdDate,endDate,priority,status,tags);
    	        	try
    	        	{
    	        	    String str=tmm.addTask(tb, category);
    	        	    System.out.println("Added task :"+str);
    	        	}
    	        	catch (FileAlreadyExistsException e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        		e.printStackTrace();
    	        	}
    	        	catch (IOException e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        		e.printStackTrace();
    	        	}
    	        	catch (Exception e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        		e.printStackTrace();
    	        	}
    	        	break;
    	    
    	        case 2 :
    	            System.out.println("Enter the name of the old task to be edit");
    	            String oldTaskName =validationOfString(sc2.nextLine());
    	        	System.out.println("Enter new name of task");
    	        	taskName = validationOfString(sc2.nextLine());
    	        	System.out.println("Enter description");
    	        	description =validationOfString(sc2.nextLine());
    	        	System.out.println("Enter created date in date/month/year format");
    	        	d = dateValidation(sc2.nextLine());
    	        	if(d==null)
    	        	{
    	        		System.out.println("created date entered is invalid");
    	        	}
    	        	else
    	        	{
    	        		createdDate = d;
    	        	}
    	        	System.out.println("Enter end date in date/month/year format");
    	        	d = dateValidation(sc2.nextLine());
    	        	if(d==null)
    	        	{
    	        		System.out.println("end date entered is invalid");
    	        	}
    	        	else
    	        	{
    	        		endDate = d;
    	        	}
    	        	System.out.println("Enter priority for the task ranges from 1-10.. (1 being lowest and 10 being heighest");
    	        	while((!sc1.hasNextInt()))
            	    {
            	    	System.out.println("Enter only int");
            	    	sc1.next();
            	    }
    	        	x = sc1.nextInt();
    	        	if(x<1 && x>10)
    	        	{
    	        		throw new IllegalArgumentException("priority should be >1 and <11");            	      
    	        	}
    	        	else
    	        	{
    	        		priority = x;
    	        	}
    	        	System.out.println("Enter the satatus of the task");
    	        	System.out.println("enter 1-> status is NEWTASKs");
    	        	System.out.println("enter 2-> status is IN PROCESS");
    	        	System.out.println("enter 3-> status is COMPLETED");
    	        	System.out.println("Enter your task status->"); 
    	        	String st="";
  	        	    while(!sc1.hasNextInt() && st!="2" && st!="1" && st!="3")
  	        	    {
  	        		  System.out.println("Enter only 1 or 2 or 3");
  	        		  st=sc1.next();
  	        	    }
  	        	    num =Integer.parseInt(sc1.next());
    	        	if(num<1 || num>3)
    	        	{
    	        		throw new IllegalArgumentException("Enter number btween 1 & 3");
    	        	}
    	        	if(num==1)
    	        	{
    	        		status = Constants.NEW;
    	        	}
    	        	if(num==2)
    	        	{
    	        		status = Constants.INPROCESS;
    	        	}
    	        	if(num==3)
    	        	{
    	        		status = Constants.COMPLETE;
    	        	}
    	        	System.out.println("Enter tag for the task");
    	        	tags = validationOfString(sc2.nextLine());
    	        	try
    	        	{
                    TaskBean oldTask = tmm.getTaskBean(category, oldTaskName);
                    TaskBean newTask = new TaskBean(taskName,description,createdDate,endDate,priority,status,tags);
                    boolean b=tmm.update(category, oldTask, newTask);
                    if(b==true)
                    {
                    	System.out.println("updated :"+newTask);
                    }
    	        	}
    	        	catch (FileNotFoundException e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        		e.printStackTrace();
    	        	}
    	        	catch (IOException e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        	}
    	        	catch (ParseException e)
    	        	{
    	        		System.out.println("here"+e.getMessage());
    	        		e.printStackTrace();
    	        	}   	
    	        	break;
    	        	
    	        case  3 :
    	        	System.out.println("enter the task name to be removed");
    	        	taskName = sc2.nextLine();
    	        	try
    	        	{
    	        		String result=tmm.removeTask(category, taskName);
    	        		if(result==Constants.SUCCESS)
    	        		{
    	        			System.out.println("removed successfully");
    	        		}
    	        	}
    	        	catch (Exception e)
    	        	{
    	        		System.out.println(e.getMessage());
    	        		e.printStackTrace();
    	        	}
    	        	break;
    	        	
    	        case 4 :
    	        	int ch3 =0;
    	        	while(ch3!=5)
    	        	{
    	        	System.out.println("Enter 1 to list the task based on alphabhetical listing of name");
    	        	System.out.println("Enter 2 to list the task based on Due date");
    	        	System.out.println("Enter 3 to list the task based on Create date");
    	        	System.out.println("Enter 4 to list the task based on longest time");
    	        	System.out.println("Enter 5 to come back");
    	        	System.out.println("Enter your choice");
    	        	while(!sc1.hasNextInt())
    	        	{
    	        	  System.out.println("Enter int only");
    	        	  sc1.next();
    	        	}
    	        	ch3 = sc1.nextInt();
    	        	switch(ch3)
    	        	{
    	        	    case 1 :
    	        	         try
    	        	         {
    	        	         	List<TaskBean> list = tmm.alphbhetList(category);
    	        		        System.out.println(list);
    	        	         }
    	        	         catch (FileNotFoundException e)
    	        	         {
    	        	        	System.out.println(e.getMessage());
    	        	         }
    	        	         catch (IOException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (ParseException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (Exception e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         break;
    	        	    case 2 :
    	        	    	try
    	        	         {
    	        	         	List<TaskBean> list = tmm.dueDateList(category);
    	        		        System.out.println(list);
    	        	         }
    	        	         catch (FileNotFoundException e)
    	        	         {
    	        	        	System.out.println(e.getMessage());
    	        	         }
    	        	         catch (IOException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (ParseException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (Exception e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         break;
    	        	    case 3 :
    	        	    	 try
    	        	         {
    	        	         	List<TaskBean> list = tmm.createDateList(category);
    	        		        System.out.println(list);
    	        	         }
    	        	         catch (FileNotFoundException e)
    	        	         {
    	        	        	System.out.println(e.getMessage());
    	        	         }
    	        	         catch (IOException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (ParseException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (Exception e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         break;
    	        	   case 4 :
    	        		   try
    	        	         {
    	        	         	List<TaskBean> list = tmm.longTimeDateList(category);
    	        		        System.out.println(list);
    	        	         }
    	        	         catch (FileNotFoundException e)
    	        	         {
    	        	        	System.out.println(e.getMessage());
    	        	         }
    	        	         catch (IOException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (ParseException e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         catch (Exception e)
    	        	         {
    	        		        System.out.println(e.getMessage());
    	        	         }
    	        	         break;	
    	        	         
    	        	}
    	        	
    	        	}
    	       case 5 :
                	System.out.println("Enter the whole task String, please seperate words with : this ");
                    taskName = validationOfString(sc2.nextLine());
                    try
    	            {
    	        	    List<TaskBean> list = tmm.search(category, taskName);
    	        	     if(list!=null)
    	        	     {	            	   
                             for(TaskBean t : list)
    	        	    	 {
    	        	    	   	System.out.println(t);
    	        	    	 }
    	        	      }
    	        	      else
    	        	      {
    	        	          System.out.println("The String does not exist in file");
    	        	      }
    	            }
    	            catch (FileNotFoundException e)
    	            {
    	        	   System.out.println(e.getLocalizedMessage());
    	        	}
    	            catch (IOException e)
    	        	{
    	        	   System.out.println(e.getMessage());
    	        	}
    	        	catch (ParseException e)
    	        	{
    	        	   System.out.println(e.getMessage());
    	            }
    	            catch (Exception e)
    	            {
    	               System.out.println(e.getMessage());
    	            }
    	            break;
    	    }
        }
    }
	
}

	            	        	
	            	        		            		