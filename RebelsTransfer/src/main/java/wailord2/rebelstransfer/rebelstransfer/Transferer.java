package wailord2.rebelstransfer.rebelstransfer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Transferer implements CommandExecutor {

    private FileConfiguration config;
    private Plugin plugin;
    private Plugin griefPrevention;

    public Transferer(FileConfiguration config, Plugin plugin){
        this.config = config;
        this.plugin = plugin;
        griefPrevention = plugin.getServer().getPluginManager().getPlugin("GriefPrevention");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("cbpay")) {
            if (args.length == 2) {
                if (Integer.valueOf(args[1]) > 0) {
                    try {
                        Player playerToPay = plugin.getServer().getPlayer(args[0]);
                        if(playerToPay == null){
                            player.sendMessage(ChatColor.GOLD + "That player is not online.");
                            return true;
                        }

                        List<String> playerBlocks = new ArrayList<>(Files.readAllLines(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()), StandardCharsets.UTF_8));
                        int availableBlocks = Integer.valueOf(playerBlocks.get(2));
                        if (availableBlocks - Integer.valueOf(args[1]) > 0) {
                            int newAvailableBlocks = availableBlocks - Integer.valueOf(args[1]);

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + args[1]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + playerToPay.getName() + " " + args[1]);

                            player.sendMessage(ChatColor.GOLD + "You paid " + args[1] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                            playerToPay.sendMessage(ChatColor.GOLD + "You received " + args[1] + " claimblocks from " + player.getName() + ".");
                            return true;
                        } else {
                            player.sendMessage(ChatColor.GOLD + "You only have " + availableBlocks + " claimblocks available to trade.");
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
                else{
                    player.sendMessage(ChatColor.GOLD + "You can't pay a negative amount.");
                    return true;
                }
            } else {
                return false;
            }

            return false;
        }
        return true;
    }
}
