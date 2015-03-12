package com.pwn9.PwnBuckets;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class EvaporateLavaTask implements Runnable 
{
	private Block block;
	
	public EvaporateLavaTask(Block block) 
	{
		this.block = block;
	}
	
	@Override
	public void run()
	{
		if (isLava(this.block))
		{
			this.block.setType(Material.AIR);			
		}
		if (isLava(this.block.getRelative(BlockFace.NORTH)))
		{
			this.block.getRelative(BlockFace.NORTH).setType(Material.AIR);
		}
		if (isLava(this.block.getRelative(BlockFace.SOUTH)))
		{
			this.block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
		}
		if (isLava(this.block.getRelative(BlockFace.WEST)))
		{
			this.block.getRelative(BlockFace.WEST).setType(Material.AIR);
		}
		if (isLava(this.block.getRelative(BlockFace.EAST)))
		{
			this.block.getRelative(BlockFace.EAST).setType(Material.AIR);
		}
	}	
	
	public boolean isLava(Block block)
	{
		Block b = block;
		
		if (b.getType() == Material.STATIONARY_LAVA || b.getType() == Material.LAVA) 
		{
			return true;
		}
		else 
		{
			return false;
		}		
	}	
}
