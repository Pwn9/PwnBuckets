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

import com.pwn9.PwnBuckets.MetricsLite;
import com.pwn9.PwnBuckets.PwnBuckets;
import com.pwn9.PwnBuckets.WaterListener;
import com.pwn9.PwnBuckets.PwnBuckets;

public class PwnBuckets extends JavaPlugin 
{
	
	public static File dataFolder;
	public static Boolean logEnabled;
	public static Boolean blockWaterBucket;
	public static Boolean blockWaterDispenser;
	public static Boolean blockIceMelt;	
	public static List<String> enabledWorlds;
	public static List<String> bucketBypass;
	public static List<String> dispenserBypass;
	public static List<String> icemeltBypass;

	public void onEnable() 
	{
		this.saveDefaultConfig();
		
		// Start Metrics
		try 
		{
		    MetricsLite metricslite = new MetricsLite(this);
		    metricslite.start();
		} 
		catch (IOException e) 
		{
		    // Failed to submit the stats :-(
		}
				
		// Setup listeners
		new WaterListener(this);
		
		// Get data folder
		PwnBuckets.dataFolder = getDataFolder();
		
		// Get enabled worlds
		PwnBuckets.enabledWorlds = getConfig().getStringList("enabled_worlds");
		
		// Get water source setting
		PwnBuckets.blockWaterBucket = getConfig().getBoolean("block_water_bucket", false);
		
		// Get biome bypass settings
		PwnBuckets.bucketBypass = getConfig().getStringList("bucket_allowed_biomes");
		
		// Get water source setting
		PwnBuckets.blockWaterDispenser = getConfig().getBoolean("block_water_dispenser", false);
		
		// Get biome bypass settings
		PwnBuckets.dispenserBypass = getConfig().getStringList("dispenser_allowed_biomes");

		// Get ice melt setting
		PwnBuckets.blockIceMelt = getConfig().getBoolean("block_ice_melt", false);
		
		// Get ice melt bypass settings
		PwnBuckets.icemeltBypass = getConfig().getStringList("ice_melt_allowed_biomes");
		
		// Get logfile setting
		PwnBuckets.logEnabled = getConfig().getBoolean("debug_log", false);
		
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

