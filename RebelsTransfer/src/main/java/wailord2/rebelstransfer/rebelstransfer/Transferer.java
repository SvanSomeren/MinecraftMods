package wailord2.rebelstransfer.rebelstransfer;

import commandSenders.APICallSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.Plugin;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Transferer implements CommandExecutor, Observer {

    private FileConfiguration config;
    private Plugin plugin;
    private Plugin griefPrevention;
    private Plugin placeholderAPI;
    private int baseBlocks = 250;
    private Player player;
    private Player playerToPay;
    private String[] args;
    private String identifier;

    public Transferer(FileConfiguration config, Plugin plugin){
        this.config = config;
        this.plugin = plugin;
        griefPrevention = plugin.getServer().getPluginManager().getPlugin("GriefPrevention");
        placeholderAPI = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        player = (Player) sender;

        if (command.getName().equalsIgnoreCase("cbpay")) {

            if(!player.hasPermission("rebelstransfer.cbpay"))
            {
                player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You do not have permission for that command.");
                return true;

            }

            if (args.length == 2) {

                if(player.getName().equalsIgnoreCase(args[0])){
                    player.sendMessage(ChatColor.GOLD + "You cannot pay yourself.");
                    return true;
                }

                if(args[1].contains("+"))
                {
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }


                try{
                    Integer.valueOf(args[1]);
                }
                catch (Exception e){
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }
                if (Integer.valueOf(args[1]) > 0) {
                    try {
                        playerToPay = plugin.getServer().getPlayer(args[0]);
                        if(playerToPay == null){
                            player.sendMessage(ChatColor.GOLD + "That player is not online.");
                            return true;
                        }

                        identifier = "pay";

                        this.args = args;
                        ServerOperator operator = new ServerOperator() {
                            @Override
                            public boolean isOp() {
                                return true;
                            }

                            @Override
                            public void setOp(boolean value) {

                            }
                        };

                        APICallSender apiCallSender = new APICallSender(operator,player);
                        apiCallSender.setOp(true);
                        apiCallSender.addAttachment(placeholderAPI, "placeholderapi.*", true);
                        apiCallSender.addObserver(this);

                        Bukkit.dispatchCommand(apiCallSender, "papi parse " + player.getName() + " %griefprevention_remainingclaims%");

                    } catch (Exception e) {
                        player.sendMessage(e.getMessage());
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

                if(args[0].contains("+"))
                {
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }

                try{
                    Integer.valueOf(args[0]);
                }
                catch (Exception e){
                    player.sendMessage(ChatColor.GOLD + "You cannot use special characters in the command.");
                    return true;
                }
                if(Integer.valueOf(args[0]) > 0){
                    try {
                        identifier = "withdraw";

                        this.args = args;
                        ServerOperator operator = new ServerOperator() {
                            @Override
                            public boolean isOp() {
                                return true;
                            }

                            @Override
                            public void setOp(boolean value) {

                            }
                        };

                        APICallSender apiCallSender = new APICallSender(operator,player);
                        apiCallSender.setOp(true);
                        apiCallSender.addAttachment(placeholderAPI, "placeholderapi.*", true);
                        apiCallSender.addObserver(this);

                        Bukkit.dispatchCommand(apiCallSender, "papi parse " + player.getName() + " %griefprevention_remainingclaims%");



                    }
                    catch(Exception e){
                        plugin.getLogger().info(e.getMessage());
                        return false;
                    }
                }
                else{
                    player.sendMessage(ChatColor.GOLD + "You can't withdraw a negative amount.");
                }

            }
            else{
                return false;
            }

        }
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {


        if(identifier.contains("pay")){
            String blockstospend = ((APICallSender)o).getSomeVariable();

            int blocksToSpend = 0;

            try{
                String newblocks = blockstospend.replaceAll("[^0-9]","");
                blocksToSpend = Integer.parseInt(newblocks);
            }
            catch (Exception e){
                player.sendMessage("RebelsTransfer - format error.");
            }

            if (blocksToSpend - Integer.valueOf(args[1]) >= 0) {
                int newAvailableBlocks = blocksToSpend - Integer.valueOf(args[1]);

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + args[1]);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + playerToPay.getName() + " " + args[1]);

                player.sendMessage(ChatColor.GOLD + "You paid " + args[1] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
                playerToPay.sendMessage(ChatColor.GOLD + "You received " + args[1] + " claimblocks from " + player.getName() + ".");
            }
            else {
                player.sendMessage(ChatColor.GOLD + "You only have " + blocksToSpend + " claimblocks available to trade.");
            }
        }
        else if(identifier.contains("withdraw")){
            String blockstospend = ((APICallSender)o).getSomeVariable();

            int blocksToSpend = 0;

            try{
                String newblocks = blockstospend.replaceAll("[^0-9]","");
                blocksToSpend = Integer.parseInt(newblocks);
            }
            catch (Exception e){
                player.sendMessage("format failure");
            }

            if (blocksToSpend - Integer.valueOf(args[0]) >= 0) {
                int newAvailableBlocks = blocksToSpend - Integer.valueOf(args[0]);
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " paper{display:{Name:\"\\\"Claimblock Voucher\\\"\", Lore: [\"\\\"" + args[0] + " claimblocks\\\"\", \"\\\"Right click to redeem\\\"\"]}} 1");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + player.getName() + " paper 1 name:&4Claimblock_Voucher lore:&f" + args[0] + "_Claimblocks|&cRight_click_to_redeem");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " -" + args[0]);

                player.sendMessage(ChatColor.GOLD + "You withdrew " + args[0] + " claimblocks. " + "You have " + newAvailableBlocks + " left to trade.");
            }
            else {
                player.sendMessage(ChatColor.GOLD + "You only have " + blocksToSpend + " claimblocks available to trade.");
            }
        }
        else{
            player.sendMessage("RebelsTransfer - general error.");
        }





    }
}
