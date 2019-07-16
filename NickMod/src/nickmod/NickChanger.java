package nickmod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickChanger implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player;
        if(commandSender instanceof Player){
            player = (Player)commandSender;
        }
        else{
            return false;
        }

            if(command.getName().equalsIgnoreCase("nick")){

                if(strings.length > 0) {
                    player.setDisplayName(strings[0]);
                    player.sendMessage(ChatColor.GOLD + "Changed your nickname to: " + strings[0]);

                    return true;
                }
                else{
                    return false;
                }

            }

            else if(command.getName().equalsIgnoreCase("nickcolor")){

                if(strings[0].equalsIgnoreCase("red")){
                    if(player.hasPermission("nickplugin.red")){
                        player.setDisplayName(ChatColor.RED + player.getDisplayName() + ChatColor.WHITE);
                        player.sendMessage(ChatColor.GOLD + "Changed your nickname color to red");
                        return true;
                    }
                    player.sendMessage(ChatColor.GOLD + "You do not have permission to change your name to red");
                    return false;
                }

            }

        return false;
    }
}
