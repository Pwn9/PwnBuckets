package com.pwn9.PwnBuckets;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;

public class EvaporateWaterTask implements Runnable 
{
	private Block block;
	
	public EvaporateWaterTask(Block block) 
	{
		this.block = block;
	}
	
	@Override
	public void run()
	{
		Block b = this.block;
		Block n = block.getRelative(BlockFace.NORTH);
		Block s = block.getRelative(BlockFace.SOUTH);
		Block e = block.getRelative(BlockFace.EAST);
		Block w = block.getRelative(BlockFace.WEST);
		Block d = block.getRelative(BlockFace.DOWN);
		Block u = block.getRelative(BlockFace.UP);
		
		if (isWater(b))
		{
			b.setType(Material.AIR);			
		}
		if (isWater(n))
		{
			n.setType(Material.AIR);
		}
		if (isWater(s))
		{
			s.setType(Material.AIR);
		}
		if (isWater(w))
		{
			w.setType(Material.AIR);
		}
		if (isWater(e))
		{
			e.setType(Material.AIR);
		}
		
		// check if the block is waterlogged - this check should probably be done first, and separately, perhaps with a different routine?
		if (isWaterLog(b))
		{
			Material m = b.getType();
			PwnBuckets.logToFile("Removing waterlog status from block: " + m.toString());
			//b.setType(Material.AIR);
			b.setType(m);
		}
		if (isWaterLog(d))
		{
			Material m = d.getType();
			PwnBuckets.logToFile("Removing waterlog status from block: " + m.toString());
			//d.setType(Material.AIR);
			d.setType(m);
		}
		if (isWaterLog(u))
		{
			Material m = u.getType();
			PwnBuckets.logToFile("Removing waterlog status from block: " + m.toString());
			//u.setType(Material.AIR);
			u.setType(m);
		}			
	}	
	
	// https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/data/Levelled.html 
	public boolean isWater(Block block)
	{
		if ((block.getType() == Material.WATER) && (block.getBlockData() instanceof Levelled)) 
		{
		    Levelled levelledBlock = (Levelled) block.getBlockData();
		    int level = levelledBlock.getLevel();
		    //source block
		    // can a waterlogged block be a 0 level - source block?
		    if (level == 0) 
		    {	    	
		    	return true;
		    }
		}		
		return false;		
	}
	
	// determine if the location is a waterlogged block
	// https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/data/Waterlogged.html
	public boolean isWaterLog(Block block)
	{
		if (block.getBlockData() instanceof Waterlogged)
		{
			Waterlogged wetBlock = (Waterlogged) block.getBlockData();
			if (wetBlock.isWaterlogged())
			{
				// don't think this makes a difference
				//wetBlock.setWaterlogged(false);
				return true;
			}
		}
		return false;
	}
	
}
