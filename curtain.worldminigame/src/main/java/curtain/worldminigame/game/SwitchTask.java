package curtain.worldminigame.game;

import org.bukkit.scheduler.BukkitRunnable;

import curtain.worldminigame.WorldMinigamePlugin;

public class SwitchTask extends BukkitRunnable
{

	public void run()
	{
	
		if(WorldMinigamePlugin.getManager().isActive())
		{
			WorldMinigamePlugin.getManager().createWorld();
		}
		else
		{
			cancel();
		}

		
	}

	
	
}
