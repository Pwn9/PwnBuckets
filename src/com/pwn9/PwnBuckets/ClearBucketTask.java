package com.pwn9.PwnBuckets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearBucketTask implements Runnable 
{

	private Player player;
	private Boolean mainHand;

	public ClearBucketTask(Player p, Boolean mainHand) 
	{
		this.player = p;
		this.mainHand = mainHand;
	}
	
	@Override
	public void run()
	{

		ItemStack emptyBucket = new ItemStack(Material.BUCKET, 1);
		ItemStack air = new ItemStack(Material.AIR);
		
		
		if (mainHand) {
			player.getInventory().setItemInMainHand(air);
			player.getInventory().setItemInMainHand(emptyBucket);
		}
		else {
			player.getInventory().setItemInOffHand(air);
			player.getInventory().setItemInOffHand(emptyBucket);
		}
		
	}	
	
}
