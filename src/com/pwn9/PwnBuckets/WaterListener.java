package com.pwn9.PwnBuckets;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.material.MaterialData;

public class WaterListener implements Listener 
{

	private final PwnBuckets plugin;
	
	public WaterListener(PwnBuckets plugin) 
	{
	    plugin.getServer().getPluginManager().registerEvents(this, plugin);    
	    this.plugin = plugin;
	}
	
	// water buckets
	@EventHandler(ignoreCancelled = true)
	public void onPlayerEmptyBucket(PlayerBucketEmptyEvent event)
	{
		World world = event.getPlayer().getWorld();
		
		Player player = event.getPlayer();
		
		// let creative mode dump water and lava all they want with buckets
		if((player.getGameMode() == GameMode.CREATIVE) && (!PwnBuckets.blockCreativeSource)) {
			return;
		}
		
		String biome = String.valueOf(event.getPlayer().getLocation().getBlock().getBiome());
		
		Material bucket = event.getBucket();
		
		// An empty bucket
		ItemStack emptyBucket = new ItemStack(Material.BUCKET, 1);
		
		if (bucket.toString().contains("WATER")) {
			
			// if the biome has a bypass allow dumping water
			if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.bucketBypass)) 
			{
				return;
			}
		
			if (PwnBuckets.isEnabledIn(world.getName())) 
			{
				if(PwnBuckets.blockWaterBucket)
				{	
					event.setItemStack(emptyBucket);
	    			// dual wield now: player.getItemInHand().setType(Material.BUCKET);
					
	    			Block block = event.getBlockClicked().getRelative(event.getBlockFace());
	    			
	    			// If the block is already water then don't do anything, otherwise set it water then set it air for water effect.
	    			if (!isWater(block)) 
	    			{
		    			block.setType(Material.WATER);
		    			EvaporateWaterTask task = new EvaporateWaterTask(block);
		    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 30L);
	    			}	 
	    			
		    		//event.setCancelled(true);
		    		
		    		event.setItemStack(emptyBucket);
		    		
	    	    	if (PwnBuckets.logEnabled) 
	    	    	{	
	    	    		PwnBuckets.logToFile("Blocked water source from bucket");
	    	    	}
	    		}
			}		 
		}
		
		if (bucket.toString().contains("LAVA")) {
			
			// if the biome has a bypass allow dumping lava
			if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.lavaBucketBypass)) 
			{
				return;
			}
		
			if (PwnBuckets.isEnabledIn(world.getName())) 
			{
				if(PwnBuckets.blockLavaBucket)
				{				
					event.setItemStack(emptyBucket);
					
	    			Block block = event.getBlockClicked().getRelative(event.getBlockFace());
	    			
	    			// If the block is already lava then don't do anything, otherwise set it lava then set it air for lava effect.
	    			if (!isLava(block)) 
	    			{
		    			block.setType(Material.LAVA);
		    			EvaporateLavaTask task = new EvaporateLavaTask(block);
		    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 120L);
	    			}	 
	    			
		    		//event.setCancelled(true);
	    			
	    			event.setItemStack(emptyBucket);
	    			
	    	    	if (PwnBuckets.logEnabled) 
	    	    	{	
	    	    		PwnBuckets.logToFile("Blocked lava source from bucket");
	    	    	}
	    		}
			}			 
		}
	}
	
	//when a dispenser dispenses...
	@EventHandler(ignoreCancelled = true)
	public void onBlockDispense(BlockDispenseEvent event)
	{
		World world = event.getBlock().getWorld();
		
		String biome = String.valueOf(event.getBlock().getBiome());
		
		if (PwnBuckets.isEnabledIn(world.getName())) 
		{
			//only care about water
			if(event.getItem().getType() == Material.WATER_BUCKET)
			{			
				// if the biome has a bypass allow dumping water
				if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.dispenserBypass)) 
				{
					return;
				}
	
				if(PwnBuckets.blockWaterDispenser)
				{				
					Block dispenser = event.getBlock();
					// Get direction dispenser is facing 
					MaterialData mat = dispenser.getState().getData(); 
					Dispenser disp_mat = (Dispenser) mat; 
					BlockFace face = disp_mat.getFacing(); 			
					Block block = dispenser.getRelative(face);			
					
	    			EvaporateWaterTask task = new EvaporateWaterTask(block);
	    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 20L);
	    			
	    	    	if (PwnBuckets.logEnabled) 
	    	    	{	
	    	    		PwnBuckets.logToFile("Blocked water source from dispenser");
	    	    	}
	    		}
			}
			
			//only care about lava 
			else if(event.getItem().getType() == Material.LAVA_BUCKET)
			{			
				// if the biome has a bypass allow dumping lava
				if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.lavaDispenserBypass)) 
				{
					return;
				}
	
				if(PwnBuckets.blockLavaDispenser)
				{				
					Block dispenser = event.getBlock();
					// Get direction dispenser is facing 
					MaterialData mat = dispenser.getState().getData(); 
					Dispenser disp_mat = (Dispenser) mat; 
					BlockFace face = disp_mat.getFacing(); 			
					Block block = dispenser.getRelative(face);			
					
	    			EvaporateLavaTask task = new EvaporateLavaTask(block);
	    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 120L);
	    			
	    	    	if (PwnBuckets.logEnabled) 
	    	    	{	
	    	    		PwnBuckets.logToFile("Blocked lava source from dispenser");
	    	    	}
	    		}
			}			
			
		}
	}
	
	// when ice melts?
	@EventHandler(ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) {

		World world = event.getBlock().getWorld();
		
		String biome = String.valueOf(event.getBlock().getBiome());
		
		// if the biome has a bypass, allow ice to melt
		if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.icemeltBypass)) 
		{
			return;
		}
		
		if (PwnBuckets.isEnabledIn(world.getName())) 
		{
			if(PwnBuckets.blockIceMelt)
	    	{				
				//only care about water
				if(event.getNewState().getType() == Material.STATIONARY_WATER)
				{					

	    			Block block = event.getBlock();
	    			
	    			block.setType(Material.WATER);
	    			EvaporateWaterTask task = new EvaporateWaterTask(block);
	    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 30L);
	    			
		    		event.setCancelled(true);
	    			
	    	    	if (PwnBuckets.logEnabled) 
	    	    	{	
	    	    		PwnBuckets.logToFile("Blocked water source from ice melt");
	    	    	}
				}
	    	}
		}		
		
	}
	
	public boolean isWater(Block block)
	{	
		if(block.getType() == Material.STATIONARY_WATER || block.getType() == Material.WATER) 
		{
			return true;
		}
		return false;	
	}		

	public boolean isLava(Block block)
	{	
		if(block.getType() == Material.STATIONARY_LAVA|| block.getType() == Material.LAVA) 
		{
			return true;
		}
		return false;	
	}	
}
	
	