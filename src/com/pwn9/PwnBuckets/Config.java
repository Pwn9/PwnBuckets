package com.pwn9.PwnBuckets;

public class Config 
{
	// Load up any values from the config file
	public static void LoadConfig()
	{
		
		// Get enabled worlds
		PwnBuckets.enabledWorlds = PwnBuckets.instance.getConfig().getStringList("enabled_worlds");
		
		// Get water source setting
		PwnBuckets.blockWaterBucket = PwnBuckets.instance.getConfig().getBoolean("block_water_bucket", false);
		
		// Get biome bypass settings
		PwnBuckets.bucketBypass = PwnBuckets.instance.getConfig().getStringList("bucket_allowed_biomes");
		
		// Get water source setting
		PwnBuckets.blockWaterDispenser = PwnBuckets.instance.getConfig().getBoolean("block_water_dispenser", false);
		
		// Get biome bypass settings
		PwnBuckets.dispenserBypass = PwnBuckets.instance.getConfig().getStringList("dispenser_allowed_biomes");

		// Get ice melt setting
		PwnBuckets.blockIceMelt = PwnBuckets.instance.getConfig().getBoolean("block_ice_melt", false);
		
		// Get ice melt bypass settings
		PwnBuckets.icemeltBypass = PwnBuckets.instance.getConfig().getStringList("ice_melt_allowed_biomes");
		
		// Get lava source setting
		PwnBuckets.blockLavaBucket = PwnBuckets.instance.getConfig().getBoolean("block_lava_bucket", false);
		
		// Get biome lava bypass settings
		PwnBuckets.lavaBucketBypass = PwnBuckets.instance.getConfig().getStringList("lava_bucket_allowed_biomes");
	
		// Get water source setting
		PwnBuckets.blockLavaDispenser = PwnBuckets.instance.getConfig().getBoolean("block_lava_dispenser", false);
		
		// Get biome bypass settings
		PwnBuckets.lavaDispenserBypass = PwnBuckets.instance.getConfig().getStringList("lava_dispenser_allowed_biomes");
		
		// Get ice melt setting
		PwnBuckets.blockCreativeSource = PwnBuckets.instance.getConfig().getBoolean("block_creative_source", false);
		
		// Get logfile setting
		PwnBuckets.logEnabled = PwnBuckets.instance.getConfig().getBoolean("debug_log", false);	
	}
	
}