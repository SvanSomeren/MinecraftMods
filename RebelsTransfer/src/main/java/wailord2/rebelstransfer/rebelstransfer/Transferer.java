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
    private int baseBlocks = 250;

    public Transferer(FileConfiguration config, Plugin plugin){
        this.config = config;
        this.plugin = plugin;
        griefPrevention = plugin.getServer().getPluginManager().getPlugin("GriefPrevention");

    }

    public Transferer(){

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("cbpay")) {
            if(!player.hasPermission("rebelstransfer.cbpay"))
            {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission for that command.");
                return true;

            }
            if(player.getName().equalsIgnoreCase(args[0])){
                player.sendMessage(ChatColor.GOLD + "You cannot pay yourself.");
                return true;
            }
            if (args.length == 2) {

                try{
                    Integer.valueOf(args[1]);
                }
                catch (Exception e){
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }
                if (Integer.valueOf(args[1]) > 0) {
                    try {
                        Player playerToPay = plugin.getServer().getPlayer(args[0]);
                        if(playerToPay == null){
                            player.sendMessage(ChatColor.GOLD + "That player is not online.");
                            return true;
                        }

                        List<String> playerBlocks = new ArrayList<>(Files.readAllLines(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()), StandardCharsets.UTF_8));

                        int playBlocks = Integer.valueOf(playerBlocks.get(1));
                        int bonusBlocks = Integer.valueOf(playerBlocks.get(2));
                        int totalBlocks = playBlocks + bonusBlocks - baseBlocks;

                        if (bonusBlocks - Integer.valueOf(args[1]) >= 0) {
                            int newAvailableBlocks = totalBlocks - Integer.valueOf(args[1]);

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + args[1]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + playerToPay.getName() + " " + args[1]);

                            player.sendMessage(ChatColor.GOLD + "You paid " + args[1] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                            playerToPay.sendMessage(ChatColor.GOLD + "You received " + args[1] + " claimblocks from " + player.getName() + ".");
                            return true;
                        }
                        else if(totalBlocks - Integer.valueOf(args[1]) >= 0){
                            int newAvailableBlocks = totalBlocks - Integer.valueOf(args[1]);

                            int blockToPay = Integer.valueOf(args[1]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + bonusBlocks);

                            blockToPay = blockToPay - bonusBlocks;
                            int newBlocksFromPlay = playBlocks - blockToPay;
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setaccruedclaimblocks " + player.getName() + " " + newBlocksFromPlay);

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + playerToPay.getName() + " " + args[1]);
                            player.sendMessage(ChatColor.GOLD + "You paid " + args[1] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                            playerToPay.sendMessage(ChatColor.GOLD + "You received " + args[1] + " claimblocks from " + player.getName() + ".");
                        }
                        else {
                            player.sendMessage(ChatColor.GOLD + "You only have " + totalBlocks + " claimblocks available to trade.");
                            return true;
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
        }
        else if(command.getName().equalsIgnoreCase("cbwithdraw")){
            if(!player.hasPermission("rebelstransfer.cbwithdraw"))
            {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission for that command.");
                return true;
            }

            if(args.length == 1){

                try{
                    Integer.valueOf(args[0]);
                }
                catch (Exception e){
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }
                if(Integer.valueOf(args[0]) > 0){
                    try {
                        List<String> playerBlocks = new ArrayList<>(Files.readAllLines(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()), StandardCharsets.UTF_8));

                        int playBlocks = Integer.valueOf(playerBlocks.get(1));
                        int bonusBlocks = Integer.valueOf(playerBlocks.get(2));
                        int totalBlocks = playBlocks + bonusBlocks - baseBlocks;

                        if (bonusBlocks - Integer.valueOf(args[0]) >= 0) {
                            int newAvailableBlocks = totalBlocks - Integer.valueOf(args[0]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " paper{display:{Name:\"\\\"Claimblock Voucher\\\"\", Lore: [\"\\\"" + args[0] + " blocks\\\"\"]}} 1");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + args[0]);

                            player.sendMessage(ChatColor.GOLD + "You withdrew " + args[0] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                            return true;
                        }
                        else if (totalBlocks - Integer.valueOf(args[0]) >= 0) {
                            int newAvailableBlocks = totalBlocks - Integer.valueOf(args[0]);


                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " paper{display:{Name:\"\\\"Claimblock Voucher\\\"\", Lore: [\"\\\"" + args[0] + " blocks\\\"\"]}} 1");

                            int blockToWithdraw = Integer.valueOf(args[0]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + bonusBlocks);

                            blockToWithdraw = blockToWithdraw - bonusBlocks;
                            int newBlocksFromPlay = playBlocks - blockToWithdraw;
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setaccruedclaimblocks " + player.getName() + " " + newBlocksFromPlay);

                            player.sendMessage(ChatColor.GOLD + "You withdrew " + args[0] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                        }
                        else {
                            player.sendMessage(ChatColor.GOLD + "You only have " + totalBlocks + " claimblocks available to trade.");
                            return true;
                        }
                    }
                    catch(Exception e){
                        plugin.getLogger().info(e.getMessage());
                        return false;
                    }
                }
                else{
                    player.sendMessage(ChatColor.GOLD + "You cant withdraw a negative amount.");
                }

            }
            else{
                return false;
            }

        }
        return true;
    }
}
