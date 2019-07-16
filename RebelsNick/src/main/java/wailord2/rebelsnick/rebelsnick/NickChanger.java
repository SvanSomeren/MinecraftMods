package wailord2.rebelsnick.rebelsnick;

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
            if(player.hasPermission("rebelsnick.changenick")){

                if(strings.length > 0) {
                    player.setDisplayName(strings[0]);
                    player.sendMessage(ChatColor.GOLD + "Changed your nickname to: " + strings[0]);
                    return true;
                }
                else{
                    return false;
                }

            }
            else{
                return false;
            }
        }

        else if(command.getName().equalsIgnoreCase("nickcolor")){
            String color;
            if(strings.length > 0){
                color = strings[0];
            }
            else{
                return false;
            }

            switch(color){
                case "black":
                    changeNickColor(player, "rebelsnick.black", "BLACK");
                    break;
                case "dark_blue":
                    changeNickColor(player, "rebelsnick.dark_blue", "DARK_BLUE");
                    break;
                case "dark_green":
                    changeNickColor(player, "rebelsnick.dark_green", "DARK_GREEN");
                    break;
                case "dark_aqua":
                    changeNickColor(player, "rebelsnick.dark_aqua", "DARK_AQUA");
                    break;
                case "dark_red":
                    changeNickColor(player, "rebelsnick.dark_red", "DARK_RED");
                    break;
                case "dark_purple":
                    changeNickColor(player, "rebelsnick.dark_purple", "DARK_PURPLE");
                    break;
                case "gold":
                    changeNickColor(player, "rebelsnick.gold", "GOLD");
                    break;
                case "gray":
                    changeNickColor(player, "rebelsnick.gray", "GRAY");
                    break;
                case "dark_gray":
                    changeNickColor(player, "rebelsnick.dark_gray", "DARK_GRAY");
                    break;
                case "blue":
                    changeNickColor(player, "rebelsnick.blue", "BLUE");
                    break;
                case "green":
                    changeNickColor(player, "rebelsnick.green", "GREEN");
                    break;
                case "aqua":
                    changeNickColor(player, "rebelsnick.aqua", "AQUA");
                    break;
                case "red":
                    changeNickColor(player, "rebelsnick.red", "RED");
                    break;
                case "light_purple":
                    changeNickColor(player, "rebelsnick.light_purple", "LIGHT_PURPLE");
                    break;
                case "yellow":
                    changeNickColor(player, "rebelsnick.yellow", "YELLOW");
                    break;
                case "white":
                    changeNickColor(player, "rebelsnick.white", "WHITE");
                    break;
            }
            return false;

        }

        return false;
    }


    public void changeNickColor(Player player, String permission, String color){
        if(player.hasPermission(permission)){
            String playername = ChatColor.stripColor(player.getDisplayName());
            player.setDisplayName(ChatColor.valueOf(color) + playername + ChatColor.WHITE);
            player.sendMessage(ChatColor.GOLD + "Changed your nickname color to " + color);
        }
        else{
            player.sendMessage(ChatColor.GOLD + "You do not have permission to change your name to " + color);
        }
    }
}
