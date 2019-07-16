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
            if(strings.length == 1){
                if(strings[0].equalsIgnoreCase("clear")){
                    player.setDisplayName(player.getName());
                    player.sendMessage(ChatColor.GOLD + "Your nickname has been reset.");
                    RebelsNick.config.set(player.getUniqueId().toString(), strings[0]);
                    return true;
                }
            }


            if(player.hasPermission("rebelsnick.changenick")){
                if(strings.length == 1) {
                    if(strings[0].equalsIgnoreCase("clear")){
                        player.setDisplayName(player.getName());
                        player.sendMessage(ChatColor.GOLD + "Your nickname has been reset.");
                        RebelsNick.config.set(player.getUniqueId().toString(), strings[0]);
                        return true;
                    }
                    else{
                        player.setDisplayName(strings[0]);
                        player.sendMessage(ChatColor.GOLD + "Changed your nickname to: " + strings[0] + ".");
                        RebelsNick.config.set(player.getUniqueId().toString(), strings[0]);
                        return true;
                    }
                }
                else if(strings.length == 2){
                    if(player.hasPermission("rebelsnick.changeothernick")){
                        if(strings[0].equalsIgnoreCase("clear")){
                            Player playerToChange = RebelsNick.getPlugin(RebelsNick.class).getServer().getPlayer(strings[1]);
                            try {
                                playerToChange.setDisplayName(playerToChange.getName());
                                player.sendMessage(ChatColor.GOLD + "Reset nickname for player " + playerToChange.getName() + ".");
                                playerToChange.sendMessage(ChatColor.GOLD + "Your nickname has been reset.");
                                RebelsNick.config.set(playerToChange.getUniqueId().toString(), playerToChange.getName());
                                return true;
                            }
                            catch(Exception e){
                                player.sendMessage(ChatColor.GOLD + "Could not find  player named " + strings[1] + ".");
                                return true;
                            }
                        }
                        Player playerToChange = RebelsNick.getPlugin(RebelsNick.class).getServer().getPlayer(strings[1]);
                        try {
                            playerToChange.setDisplayName(strings[0]);
                        }
                        catch(Exception e){
                            player.sendMessage(ChatColor.GOLD + "Could not find  player named " + strings[1] + ".");
                            return true;
                        }
                        player.sendMessage(ChatColor.GOLD + "Changed " + playerToChange.getName() + "'s name to " + strings[0] + ".");
                        playerToChange.sendMessage(ChatColor.GOLD + "Changed your nickname to: " + strings[0] + ".");
                        RebelsNick.config.set(playerToChange.getUniqueId().toString(), strings[0]);
                        return true;
                    }
                    else{
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission for that command.");
                    }
                }
                else if(strings.length == 3){
                    if(player.hasPermission("rebelsnick.changeothernick")){
                        if(strings[2].equalsIgnoreCase("-s")){
                            if(strings[0].equalsIgnoreCase("clear")){
                                Player playerToChange = RebelsNick.getPlugin(RebelsNick.class).getServer().getPlayer(strings[1]);
                                try {
                                    playerToChange.setDisplayName(playerToChange.getName());
                                    player.sendMessage(ChatColor.GOLD + "Reset nickname for player " + playerToChange.getName() + ".");
                                    RebelsNick.config.set(playerToChange.getUniqueId().toString(), playerToChange.getName());
                                    return true;
                                }
                                catch(Exception e){
                                    player.sendMessage(ChatColor.GOLD + "Could not find  player named " + strings[1] + ".");
                                    return true;
                                }
                            }
                            Player playerToChange = RebelsNick.getPlugin(RebelsNick.class).getServer().getPlayer(strings[1]);
                            try {
                                playerToChange.setDisplayName(strings[0]);
                            }
                            catch(Exception e){
                                player.sendMessage(ChatColor.GOLD + "Could not find player named " + strings[1] + ".");
                                return true;
                            }
                            player.sendMessage(ChatColor.GOLD + "Changed " + playerToChange.getName() + "'s name to " + strings[0] + ".");
                            RebelsNick.config.set(playerToChange.getUniqueId().toString(), strings[0]);
                        }
                        else{
                            player.sendMessage(ChatColor.GOLD + "Improper use of silencing argument, use -s to silence the command.");
                        }

                    }
                    else{
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission for that command.");
                    }
                }
                else{
                    return false;
                }

            }
            else{
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission to change your nickname.");
                return false;
            }
        }

        else if(command.getName().equalsIgnoreCase("nickcolor")){
            String color;
            if(strings.length > 0){
                color = strings[0];
            }else{
                return false;
            }
            if(strings.length == 2){
                switch(color){
                    case "black":
                        changeOtherColor(player, strings[1],  "BLACK");
                        break;
                    case "dark_blue":
                        changeOtherColor(player, strings[1],  "DARK_BLUE");
                        break;
                    case "dark_green":
                        changeOtherColor(player, strings[1], "DARK_GREEN");
                        break;
                    case "dark_aqua":
                        changeOtherColor(player, strings[1], "DARK_AQUA");
                        break;
                    case "dark_red":
                        changeOtherColor(player, strings[1],  "DARK_RED");
                        break;
                    case "dark_purple":
                        changeOtherColor(player, strings[1],  "DARK_PURPLE");
                        break;
                    case "gold":
                        changeOtherColor(player, strings[1],"GOLD");
                        break;
                    case "gray":
                        changeOtherColor(player, strings[1],"GRAY");
                        break;
                    case "dark_gray":
                        changeOtherColor(player, strings[1],  "DARK_GRAY");
                        break;
                    case "blue":
                        changeOtherColor(player, strings[1],  "BLUE");
                        break;
                    case "green":
                        changeOtherColor(player, strings[1],  "GREEN");
                        break;
                    case "aqua":
                        changeOtherColor(player, strings[1],  "AQUA");
                        break;
                    case "red":
                        changeOtherColor(player, strings[1], "RED");
                        break;
                    case "light_purple":
                        changeOtherColor(player, strings[1],  "LIGHT_PURPLE");
                        break;
                    case "yellow":
                        changeOtherColor(player, strings[1], "YELLOW");
                        break;
                    case "white":
                        changeOtherColor(player, strings[1],  "WHITE");
                        break;
                }
            }

            if(strings.length == 1) {
                switch (color) {
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
            }
            return false;

        }

        return false;
    }


    public void changeNickColor(Player player, String permission, String color){
        if(player.hasPermission(permission)){
            String playername = ChatColor.stripColor(player.getDisplayName());
            player.setDisplayName(ChatColor.valueOf(color) + playername + ChatColor.WHITE);
            player.sendMessage(ChatColor.GOLD + "Changed your nickname color to " + color + ".");
            RebelsNick.config.set(player.getUniqueId().toString(), ChatColor.valueOf(color) + playername + ChatColor.WHITE );
        }
        else{
            player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission to change your nickname.");
        }
    }

    public void changeOtherColor(Player player, String playerToChange, String color){
        if(player.hasPermission("rebelsnick.changeothernick")){

            Player changePlayer = RebelsNick.getPlugin(RebelsNick.class).getServer().getPlayer(playerToChange);

            String playername = ChatColor.stripColor(changePlayer.getDisplayName());
            changePlayer.setDisplayName(ChatColor.valueOf(color) + playername + ChatColor.WHITE);
            player.sendMessage(ChatColor.GOLD + "Changed the nickname color of " + changePlayer.getName() + " to " + color + ".");
            changePlayer.sendMessage(ChatColor.GOLD + "Changed your nickname color to " + color + ".");
            RebelsNick.config.set(changePlayer.getUniqueId().toString(), ChatColor.valueOf(color) + playername + ChatColor.WHITE );
        }
        else{
            player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission to change your nickname.");
        }
    }
}
