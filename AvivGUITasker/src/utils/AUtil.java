package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.process.IProcess;
import view.View.process_type;

public class AUtil 
{
	public static long getTotalTimeByUnit(long start, long current, TimeUnit unit)
	{
		if(current==0)
			current=System.currentTimeMillis();
		current=current-start;
		long time=unit.convert(current, TimeUnit.MILLISECONDS);
		if(unit.equals(unit.HOURS))
			time=time%24;
		else if(unit.equals(unit.MINUTES) || unit.equals(unit.SECONDS))
			time=time%60;
		return time; 
	}
	public static String getTotalTimeDaysHoursMinSec(long start, long current)
	{ 
		if(start == 0 ) return "null";
		return String.format("%02d:%02d:%02d:%02d", 
				getTotalTimeByUnit(start, current, TimeUnit.DAYS),
				getTotalTimeByUnit(start, current, TimeUnit.HOURS)
			   ,getTotalTimeByUnit(start, current, TimeUnit.MINUTES),
				getTotalTimeByUnit(start, current, TimeUnit.SECONDS));
	}
	public static String extractProcessTitle(String string, Collection<IProcess> list) 
	{
		for(IProcess p: list)
		{
			if(p.getProcessName().contains(string))
				return p.getProcessName();
		}
		return null;
	}
	public static String decryptProcessIDToTitle(String pID)
	{
		Scanner s= new Scanner(pID);
		s.useDelimiter("%");
		String title="";
		while(s.hasNext())
		{
			title+=s.next()+" ";
		}
		return title;
	}
	public static String encryptProcessTitleToID(String pTitle)
	{
		Scanner scanner= new Scanner(pTitle);
		scanner.useDelimiter(" ");
		String id="";
		while(scanner.hasNext())
		{
			id+=scanner.next()+"%";
		}
		return id;
	}

	public static String ArrayListToString(ArrayList<String> list)
	{
		if(list == null)
			return null;
		
		String line="";
		
		for(String s: list)
		{
			line+=s;
		}
		return line;
	}
	public static boolean isNumber(String str){
		  int i=0, len=str.length();
		  boolean a=false,b=false,c=false, d=false;
		  if(i<len && (str.charAt(i)=='+' || str.charAt(i)=='-')) i++;
		  while( i<len && isDigit(str.charAt(i)) ){ i++; a=true; }
		  if(i<len && (str.charAt(i)=='.')) i++;
		  while( i<len && isDigit(str.charAt(i)) ){ i++; b=true; }
		  if(i<len && (str.charAt(i)=='e' || str.charAt(i)=='E') && (a || b)){ i++; c=true; }
		  if(i<len && (str.charAt(i)=='+' || str.charAt(i)=='-') && c) i++;
		  while( i<len && isDigit(str.charAt(i)) ){ i++; d=true;}
		  return i==len && (a||b) && (!c || (c && d));
		}
		static boolean isDigit(char c){
		  return c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9';
		}
		public static List<IProcess> MapToList(HashMap<String, IProcess> map)
		{
			ArrayList<IProcess> list = new ArrayList<>();
			Collection<IProcess> collection = map.values();
			for(IProcess c : collection)
			{
				list.add(c);
			}
			return list;
			
		}
		public static process_type ProcessStringToEnum(String process)
		{
			if(process.equals("current"))
			{
				return process_type.CURRENT;
			}
			else if(process.equals("queue"))
			{
				return process_type.QUEUED;
			}
			else if(process.equals("finished"))
			{
				return process_type.FINISHED;
			}
			else
			{
				return null;
			}
		}

}
