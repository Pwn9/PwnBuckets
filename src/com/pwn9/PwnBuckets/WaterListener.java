package com.pwn9.PwnBuckets;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

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
		
		// let creative mode dump water and lava all they want with buckets if they have permission
		if((player.getGameMode() == GameMode.CREATIVE) && ((!PwnBuckets.blockCreativeSource) || (player.hasPermission("pwnbuckets.creativebucket")))) {
			return;
		}
		
		// if the plugin isn't enabled for this world, return
		if (!PwnBuckets.isEnabledIn(world.getName())) {
			return;
		}
		
		String biome = String.valueOf(event.getPlayer().getLocation().getBlock().getBiome());
		
		Material bucket = event.getBucket();
		Material result = event.getItemStack().getType();
		Material mainBucket = player.getInventory().getItemInMainHand().getType();
		Material offBucket = player.getInventory().getItemInOffHand().getType();
				
    	if (PwnBuckets.logEnabled) 
    	{	
    		PwnBuckets.logToFile("Bucket empty event for: " + bucket.toString() + " Main Hand: " + mainBucket.toString() + " Off Hand: " + offBucket.toString() + " Result: " + result.toString());
    	}
    	
		// if its water
		if ((bucket.toString().contains("WATER") || bucket.toString().contains("FISH") || bucket.toString().contains("SALMON") || bucket.toString().contains("COD")) && (!player.hasPermission("pwnbuckets.waterbucket"))) 
		{

			// if the biome has a bypass allow dumping water
			if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.bucketBypass)) 
			{
				return;
			}
	    	
			if(PwnBuckets.blockWaterBucket)
			{	
    			Block block = event.getBlockClicked().getRelative(event.getBlockFace());
	    		
    			block.getBlockData(); 
    			// If the block is already water then don't do anything, otherwise set it water then set it air for water effect.
    			if (!isWater(block)) 
    			{
	    			block.setType(Material.WATER);
	    			EvaporateWaterTask task = new EvaporateWaterTask(block);
	    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 30L);
    			}	 
	    		
    			// no need to cancel the event anymore, let it finish and evaporate the water
	    		
    	    	if (PwnBuckets.logEnabled) 
    	    	{	
    	    		PwnBuckets.logToFile("Blocked water source from bucket");
    	    	}
    	    	
    	    	return;
    		}	 
		}
		
		// if its lava
		if (bucket.toString().contains("LAVA") && (!player.hasPermission("pwnbuckets.lavabucket"))) 
		{
			
			// if the biome has a bypass allow dumping lava
			if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.lavaBucketBypass)) 
			{
				return;
			}
		
			if(PwnBuckets.blockLavaBucket)
			{	
				//event.setItemStack(emptyBucket);
    			Block block = event.getBlockClicked().getRelative(event.getBlockFace());
	    			    			
    			// If the block is already lava then don't do anything, otherwise set it lava then set it air for lava effect.
    			if (!isLava(block)) 
    			{
	    			block.setType(Material.LAVA);
	    			EvaporateLavaTask task = new EvaporateLavaTask(block);
	    			plugin.getServer().getScheduler().runTaskLater(plugin, task, 120L);
    			}	 

    			// no need to cancel the event anymore, let it finish and evaporate the lava
    			
    		  	if (PwnBuckets.logEnabled) 
    	    	{	
    	    		PwnBuckets.logToFile("Blocked lava source from bucket");
    	    	}
    		  	
    		  	return;
    		}
		}			 
	}
	
	//when a dispenser dispenses...
	@EventHandler(ignoreCancelled = true)
	public void onBlockDispense(BlockDispenseEvent event)
	{
		World world = event.getBlock().getWorld();
		
		String biome = String.valueOf(event.getBlock().getBiome());
		
		// if the plugin isn't enabled for this world, return
		if (!PwnBuckets.isEnabledIn(world.getName())) {
			return;
		}
		
	    Material bucket = event.getItem().getType();
	    
	    
    	if (PwnBuckets.logEnabled) 
    	{	
    		PwnBuckets.logToFile("Dispenser dispense event for: " + bucket.toString());
    	}
    	
		//only care about water buckets including fish buckets
		if (bucket.toString().contains("WATER") || bucket.toString().contains("FISH") || bucket.toString().contains("SALMON") || bucket.toString().contains("COD"))
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
				BlockFace face = ((Directional) dispenser.getBlockData()).getFacing();
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
		else if(bucket.toString().contains("LAVA"))
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
				BlockFace face = ((Directional) dispenser.getBlockData()).getFacing();
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
	
	// when ice melts?
	@EventHandler(ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) 
	{

		World world = event.getBlock().getWorld();
		
		String biome = String.valueOf(event.getBlock().getBiome());
		
		// if the biome has a bypass, allow ice to melt
		if (PwnBuckets.containsCaseInsensitive(biome, PwnBuckets.icemeltBypass)) {
			return;
		}
		
		// if the plugin isn't enabled for this world, return
		if (!PwnBuckets.isEnabledIn(world.getName())) {
			return;
		}		
		
		if(PwnBuckets.blockIceMelt)
    	{			
	    	if (PwnBuckets.logEnabled) 
	    	{	
	    		PwnBuckets.logToFile("Block fade event result: " + event.getNewState().getType().toString());
	    	}
	    	
			//only care about water
			if (event.getNewState().getType() == Material.WATER)
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
	
	// to set a source water not flowing water
	public boolean isWater(Block block)
	{	
		if ((block.getType() == Material.WATER) && (block.getBlockData() instanceof Levelled)) 
		{
		    Levelled levelledBlock = (Levelled) block.getBlockData();
		    int level = levelledBlock.getLevel();
		    //source block
		    if (level == 0) 
		    {
    	    	if (PwnBuckets.logEnabled) 
    	    	{	
    	    		PwnBuckets.logToFile("Placing bucket on an existing source block.");
    	    	}		    	
		    	return true;
		    }
		}		
		return false;	
	}		

	// to get a source lava not flowing lava
	public boolean isLava(Block block)
	{	
		if ((block.getType() == Material.LAVA) && (block.getBlockData() instanceof Levelled)) 
		{
		    Levelled levelledBlock = (Levelled) block.getBlockData();
		    int level = levelledBlock.getLevel();
		    //source block
		    if (level == 0) 
		    {
    	    	if (PwnBuckets.logEnabled) 
    	    	{	
    	    		PwnBuckets.logToFile("Placing bucket on an existing source block.");
    	    	}				    	
		    	return true;
		    }
		}		
		return false;	
	}	
}
	
	