//  
//  =====GPL=============================================================
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; version 2 dated June, 1991.
// 
//  This program is distributed in the hope that it will be useful, 
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
// 
//  You should have received a copy of the GNU General Public License
//  along with this program;  if not, write to the Free Software
//  Foundation, Inc., 675 Mass Ave., Cambridge, MA 02139, USA.
//  =====================================================================
//
//
// Copyright 2011-2015 Michael Sheppard (crackedEgg)
//
package com.reptiles.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	public static Configuration config;

	static private int komodoSpawnProb;
	static private int griseusSpawnProb;
	static private int laceSpawnProb;
	static private int perentieSpawnProb;
	static private int savannaSpawnProb;
	static private int crocSpawnProb;
	static private int largeCrocSpawnProb;
	static private int desertTortoiseSpawnProb;
	static private int littleTurtleSpawnProb;
	static private int iguanaSpawnProb;
	static private int tortoiseSpawnProb;
	static private int gatorSpawnProb;
	static private int chameleonSpawnProb;
	static private int crocMonitorSpawnProb;
	static private int minSpawn;
	static private int maxSpawn;
	static private int talkInterval;
	static private double talkvolume;
	static private boolean despawn;
	static private boolean randomScale;
	static private boolean followOwner;

	private static final String comments = Reptiles.name + " Config\n Michael Sheppard (crackedEgg)\n"
			+ " For Minecraft Version " + Reptiles.mcversion;
	private static final String randomScaleComment = "Set to false to disable random scaling of monitors, default is true.";
	private static final String despawnComment = "Set to false to not despawn. default is true.";
	private static final String followOwnerComment = "Set to false to have tamed monitors not follow owner, default is true.";
	private static final String spawnProbComment = "Spawn Probability\nSet to zero to disable spawning of this entity";
	private static final String minSpawnComment = "Minimum number of reptiles to spawn at one time";
	private static final String maxSpawnComment = "Maximum number of reptiles to spawn at one time";
	private static final String talkIntervalComment = "time interval between ambient sounds";
	private static final String talkVolumeComment = "volume of reptile sounds";

	public static void startConfig(FMLPreInitializationEvent event)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load(); // only need to load config once during pre initializeSoundEvents
		updateConfigInfo();
	}

	public static void updateConfigInfo()
	{
		try {
			config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, comments);

			komodoSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "komodoSpawnProb", 10, spawnProbComment).getInt();
			griseusSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "griseusSpawnProb", 12, spawnProbComment).getInt();
			laceSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "laceSpawnProb", 12, spawnProbComment).getInt();
			perentieSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "perentieSpawnProb", 12, spawnProbComment).getInt();
			savannaSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "savannaSpawnProb", 12, spawnProbComment).getInt();
			crocSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "crocSpawnProb", 5, spawnProbComment).getInt();
			largeCrocSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "largeCrocSpawnProb", 4, spawnProbComment).getInt();
			desertTortoiseSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "desertTortoiseSpawnProb", 12, spawnProbComment).getInt();
			littleTurtleSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "littleTurtleSpawnProb", 10, spawnProbComment).getInt();
			iguanaSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "iguanaSpawnProb", 12, spawnProbComment).getInt();
			tortoiseSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "tortoiseSpawnProb", 12, spawnProbComment).getInt();
			gatorSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "gatorSpawnProb", 5, spawnProbComment).getInt();
			chameleonSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "chameleonSpawnProb", 12, spawnProbComment).getInt();
			crocMonitorSpawnProb = config.get(Configuration.CATEGORY_GENERAL, "crocMonitorSpawnProb", 12, spawnProbComment).getInt();
			minSpawn = config.get(Configuration.CATEGORY_GENERAL, "minSpawn", 1, minSpawnComment).getInt();
			maxSpawn = config.get(Configuration.CATEGORY_GENERAL, "maxSpawn", 4, maxSpawnComment).getInt();
			talkInterval = config.get(Configuration.CATEGORY_GENERAL, "talkInterval", 320, talkIntervalComment).getInt();
			talkvolume = config.get(Configuration.CATEGORY_GENERAL, "talkVolume", 0.3, talkVolumeComment).getDouble();


			randomScale = config.get(Configuration.CATEGORY_GENERAL, "randomScale", true, randomScaleComment).getBoolean(true);
			despawn = config.get(Configuration.CATEGORY_GENERAL, "despawn", true, despawnComment).getBoolean(true);
			followOwner = config.get(Configuration.CATEGORY_GENERAL, "followOwner", true, followOwnerComment).getBoolean(true);
		} catch (Exception e) {
			Reptiles.proxy.info("failed to load or read the config file");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	public static boolean useRandomScaling()
	{
		return randomScale;
	}

	public static boolean shouldDespawn()
	{
		return despawn;
	}

	public static boolean getFollowOwner()
	{
		return followOwner;
	}

	public static int getKomodoSpawnProb()
	{
		return komodoSpawnProb;
	}

	public static int getGriseusSpawnProb()
	{
		return griseusSpawnProb;
	}

	public static int getLaceSpawnProb()
	{
		return laceSpawnProb;
	}

	public static int getPerentieSpawnProb()
	{
		return perentieSpawnProb;
	}

	public static int getSavannaSpawnProb()
	{
		return savannaSpawnProb;
	}

	public static int getCrocSpawnProb()
	{
		return crocSpawnProb;
	}

	public static int getLargeCrocSpawnProb()
	{
		return largeCrocSpawnProb;
	}

	public static int getDesertTortoiseSpawnProb()
	{
		return desertTortoiseSpawnProb;
	}

	public static int getLittleTurtleSpawnProb()
	{
		return littleTurtleSpawnProb;
	}

	public static int getIguanaSpawnProb()
	{
		return iguanaSpawnProb;
	}

	public static int getTortoiseSpawnProb()
	{
		return tortoiseSpawnProb;
	}

	public static int getGatorSpawnProb()
	{
		return gatorSpawnProb;
	}

	public static int getChameleonSpawnProb()
	{
		return chameleonSpawnProb;
	}

	public static int getCrocMonitorSpawnProb()
	{
		return crocMonitorSpawnProb;
	}

	public static int getMinSpawn() { return minSpawn; }

	public static int getMaxSpawn() { return maxSpawn; }

	public static int getTalkInterval() {
		return talkInterval;
	}

	public static float getTalkVolume() {
		return (float)talkvolume;
	}

}
