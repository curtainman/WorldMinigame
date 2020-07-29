package curtain.worldminigame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import curtain.worldminigame.command.CleanWorldCommand;
import curtain.worldminigame.command.WorldGameCommand;
import curtain.worldminigame.game.WorldGameManager;

public class WorldMinigamePlugin extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(new WorldGameManager(), this);
		this.getCommand("worldgame").setExecutor(new WorldGameCommand());
		this.getCommand("wmgforceclean").setExecutor(new CleanWorldCommand());
	}
	
	@Override
	public void onDisable()
	{
		//you know maybe we SHOULD do this before everything goes kaboom 
		getManager().cleanWorlds();
	}
	
	public static WorldGameManager getManager()
	{
		return new WorldGameManager();
	}
	
}
