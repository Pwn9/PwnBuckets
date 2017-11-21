package com.pwn9.PwnBuckets;

public class Config 
{
	// Load up any values from the config file
	public static void LoadConfig()
	{
		
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
		
		// Get lava source setting
		PwnBuckets.blockLavaBucket = getConfig().getBoolean("block_lava_bucket", false);
		
		// Get biome lava bypass settings
		PwnBuckets.lavaBucketBypass = getConfig().getStringList("lava_bucket_allowed_biomes");
	
		// Get water source setting
		PwnBuckets.blockLavaDispenser = getConfig().getBoolean("block_lava_dispenser", false);
		
		// Get biome bypass settings
		PwnBuckets.lavaDispenserBypass = getConfig().getStringList("lava_dispenser_allowed_biomes");
		
		// Get ice melt setting
		PwnBuckets.blockCreativeSource = getConfig().getBoolean("block_creative_source", false);
		
		// Get logfile setting
		PwnBuckets.logEnabled = getConfig().getBoolean("debug_log", false);	
	}
	
}