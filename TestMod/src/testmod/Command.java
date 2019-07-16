package testmod;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l" + player.getName() + "  &b&lsent the command!"));
            return true;
        }
        else{
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lThe console sent the command!"));
            return true;
        }
    }
}
