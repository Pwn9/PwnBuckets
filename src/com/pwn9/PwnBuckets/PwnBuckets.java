package com.pwn9.PwnBuckets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class PwnBuckets extends JavaPlugin 
{
	
	// For convenience, a reference to the instance of this plugin
	public static PwnBuckets instance;
	
	public static File dataFolder;
	public static Boolean logEnabled;
	public static Boolean blockWaterBucket;
	public static Boolean blockLavaBucket;
	public static Boolean blockWaterDispenser;
	public static Boolean blockLavaDispenser;
	public static Boolean blockIceMelt;
	public static Boolean blockIceBreak;	
	public static Boolean blockCreativeSource;	
	public static List<String> enabledWorlds;
	public static List<String> bucketBypass;
	public static List<String> lavaBucketBypass;
	public static List<String> dispenserBypass;
	public static List<String> lavaDispenserBypass;
	public static List<String> iceMeltBypass;
	public static List<String> iceBreakBypass;

	public void onEnable() 
	{
		instance = this;
		
		this.saveDefaultConfig();
		
		// Start Metrics
	    Metrics metrics = new Metrics(this, 3257);
	
		// Setup listeners
		new WaterListener(this);
		
		// Get data folder
		PwnBuckets.dataFolder = getDataFolder();
				
		// Load Configurable Values
		Config.LoadConfig();
		
    	if (PwnBuckets.logEnabled) 
    	{	
    		PwnBuckets.logToFile("PwnBuckets Enabled");
    	}	
    	
	}
		
	public void onDisable() 
	{
    	if (PwnBuckets.logEnabled) 
    	{	
    		PwnBuckets.logToFile("PwnBuckets Disabled");
    	}	
	}
	
	
	public static boolean isEnabledIn(String world) 
	{
		return enabledWorlds.contains(world);
	}	

	public static boolean containsCaseInsensitive(String s, List<String> l)
	{
		for (String string : l)
		{
			if (string.equalsIgnoreCase(s))
			{
				return true;
			}
		}
		return false;
	}	
	
	public static void logToFile(String message) 
	{   
	    	try 
	    	{
			    
			    if(!dataFolder.exists()) 
			    {
			    	dataFolder.mkdir();
			    }
			     
			    File saveTo = new File(dataFolder, "pwnbuckets.log");
			    if (!saveTo.exists())  
			    {
			    	saveTo.createNewFile();
			    }
			    
			    FileWriter fw = new FileWriter(saveTo, true);
			    PrintWriter pw = new PrintWriter(fw);
			    pw.println(getDate() +" "+ message);
			    pw.flush();
			    pw.close();
		    } 
		    catch (IOException e) 
		    {
		    	e.printStackTrace();
		    }
	}
	
	public static String getDate() 
	{
		  String s;
		  Format formatter;
		  Date date = new Date(); 
		  formatter = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]");
		  s = formatter.format(date);
		  return s;
	}

}

