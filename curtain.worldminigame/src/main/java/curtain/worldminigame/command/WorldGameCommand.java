package curtain.worldminigame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import curtain.worldminigame.WorldMinigamePlugin;

public class WorldGameCommand implements CommandExecutor
{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(player.hasPermission("wmg.admin"))
			{
				switch(args.length)
				{
				case(0):
					player.sendMessage("§7/wmg (start, stop) §eStarts the world minigame.");
					return false;
				case(1):
					if(args[0].equals("start") || args[0].equals("stop"))
					{
						if(args[0].equals("start"))
						{
							WorldMinigamePlugin.getManager().startGame();
							return true;
						}
						if(args[0].equals("stop"))
						{
							WorldMinigamePlugin.getManager().stopGame();
							return true;
						}
					}
					else
					{
						player.sendMessage("§cInvalid Argument");
						return false;
					}
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
