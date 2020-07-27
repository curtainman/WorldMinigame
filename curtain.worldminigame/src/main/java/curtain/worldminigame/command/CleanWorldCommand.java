package curtain.worldminigame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import curtain.worldminigame.WorldMinigamePlugin;

public class CleanWorldCommand implements CommandExecutor
{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(player.hasPermission("wmg.admin"))
			{
				if(WorldMinigamePlugin.getManager().isActive())
				{
					player.sendMessage("§cYou cannot do this while a game is active");
					return false;
				}
				else
				{
					WorldMinigamePlugin.getManager().cleanWorlds();
					player.sendMessage("§aCleaned worlds");
					return true;
				}
			}
			else
			{
				player.sendMessage("§cInvalid Permission!");
				return false;
			}
		}
		return false;
	}

	
	
}
