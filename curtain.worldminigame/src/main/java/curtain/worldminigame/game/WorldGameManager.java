package curtain.worldminigame.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import curtain.worldminigame.WorldMinigamePlugin;

public class WorldGameManager implements Listener
{

	// Automatically updates
	private static String worldName = "wmg";
	private static boolean active;
	public static HashMap<String, Location> evacuationLocations = new HashMap<String, Location>();
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			if(active)
			{
				if(event.getCause().equals(DamageCause.SUFFOCATION))
				{
					event.setCancelled(true);
				}
				else
				{
					event.setCancelled(false);
				}
			}
		}
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	
	public void startGame()
	{
		Bukkit.broadcastMessage("�a�lWorld Game started! Every 5 minutes, a new world will be generated. You will be teleported to your exact coords but inside of that newly created world. You will not take damage from suffocation. Good luck!");
		active = true;
		Bukkit.getOnlinePlayers().forEach((p) -> {
			evacuationLocations.put(p.getName(), p.getLocation());
		});
		new SwitchTask().runTaskTimer(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), 0L, 6000L);
	}
	
	public void stopGame()
	{
		Bukkit.broadcastMessage("�a�lWorld Game stopped!");
		active = false;
		cleanWorlds();
	}
	
	public void cleanWorlds()
	{
		
		//Evacuate players
		
		Bukkit.getOnlinePlayers().forEach((p) -> {
			if(p.getWorld().getName().equals("wmg") || p.getWorld().getName().equals("wmg2"))
			{
				p.sendMessage("�e�lYou are being evacuated back to your location when the game was started.");
				p.teleport(evacuationLocations.get(p.getName()));
			}
		});
		
		//Once done we will truncate the map
		evacuationLocations.clear();
		
		
		//Delete the worlds!
		//unload then delete
		
		
		Bukkit.unloadWorld("wmg", false);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
		{
			try
			{
				FileUtils.deleteDirectory(new File("wmg"));
			} catch (IOException e)
			{
				System.out.println("fuck");
				e.printStackTrace();
			}
		}, 60L);

		
		//then we shall do it again
		
		if(Bukkit.getWorld("wmg2") != null)
		{
			Bukkit.unloadWorld("wmg2", false);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
			{
				try
				{
					FileUtils.deleteDirectory(new File("wmg2"));
					
				} catch (IOException e)
				{
					System.out.println("fuck");
					e.printStackTrace();
				}
			}, 60L);
		}
		
	}
	
	public void createWorld()
	{
		if (active)
		{
			// check to see if the world exists
			if (Bukkit.getWorld(worldName) == null)
			{
				// create
				WorldCreator wc = new WorldCreator(worldName);

				wc.environment(World.Environment.NORMAL);
				wc.type(WorldType.NORMAL);

				wc.createWorld();
			
				//after create world then teleport people after a couple seconds to ensure the thing works
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
				{
					Bukkit.broadcastMessage("�e�lSwitching worlds!");
					Bukkit.getOnlinePlayers().forEach((p) -> 
					{
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
						Location pLoc = p.getLocation();
						
						Location newLoc = new Location(Bukkit.getWorld(worldName), pLoc.getX(), pLoc.getY(), pLoc.getZ());
						p.teleport(newLoc);
					});
				}, 5 * 20L);
				
			}
			else
			{
				//change names
				if(worldName.equals("wmg"))
				{
					final String newWorld = "wmg2";
					WorldCreator wc = new WorldCreator(newWorld);

					wc.environment(World.Environment.NORMAL);
					wc.type(WorldType.NORMAL);

					wc.createWorld();
				
				
					//after create world then teleport people after a couple seconds to ensure the thing works
					Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
					{
						Bukkit.broadcastMessage("�e�lSwitching worlds!");
						Bukkit.getOnlinePlayers().forEach((p) -> 
						{
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
							Location pLoc = p.getLocation();
							Location newLoc = new Location(Bukkit.getWorld(worldName), pLoc.getX(), pLoc.getY(), pLoc.getZ());
							p.teleport(newLoc);
						});
					}, 5 * 20L);
					
					worldName = "wmg2";
					
					//Delete old one!
					
					Bukkit.unloadWorld("wmg", false);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
					{
						try
						{
							FileUtils.deleteDirectory(new File("wmg"));
							
						} catch (IOException e)
						{
							System.out.println("fuck");
							e.printStackTrace();
						}
					}, 60L);
					
				}
				else if (worldName.equals("wmg2"))
				{
					final String newWorld = "wmg";
					WorldCreator wc = new WorldCreator(newWorld);

					wc.environment(World.Environment.NORMAL);
					wc.type(WorldType.NORMAL);

					wc.createWorld();
				
				
					//after create world then teleport people after a couple seconds to ensure the thing works
					Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
					{
						Bukkit.broadcastMessage("�e�lSwitching worlds!");
						Bukkit.getOnlinePlayers().forEach((p) -> 
						{
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
							Location pLoc = p.getLocation();
							Location newLoc = new Location(Bukkit.getWorld(worldName), pLoc.getX(), pLoc.getY(), pLoc.getZ());
							p.teleport(newLoc);
						});
					}, 5 * 20L);
					
					worldName = "wmg";
					
					
					Bukkit.unloadWorld("wmg", false);
					
					//Delete old one!
					Bukkit.getScheduler().scheduleSyncDelayedTask(WorldMinigamePlugin.getPlugin(WorldMinigamePlugin.class), () -> 
					{
						try
						{
							FileUtils.deleteDirectory(new File("wmg2"));
							
						} catch (IOException e)
						{
							System.out.println("fuck");
							e.printStackTrace();
						}
					}, 60L);
					
				}
			}
		}

	}

	public boolean deleteWorld(File path)
	{
		if (path.exists())
		{
			File files[] = path.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				if (files[i].isDirectory())
				{
					deleteWorld(files[i]);
				} else
				{
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

}
