package wailord2.rebelstransfer.rebelstransfer;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
        Player player = (Player)sender;

        if(command.getName().equalsIgnoreCase("cbpay")){
            if(args.length == 2){
                if(Integer.valueOf(args[1]) > 0){
                    try{
                        Player playerToPay = plugin.getServer().getPlayer(args[0]);

                        List<String> playerBlocks = new ArrayList<>(Files.readAllLines(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()), StandardCharsets.UTF_8));
                        //playertopayblocks
                        int availableBlocks = Integer.valueOf(playerBlocks.get(2));
                        if(availableBlocks - Integer.valueOf(args[1]) > 0){
                            int newAvailableBlocks = availableBlocks - Integer.valueOf(args[1]);
                            playerBlocks.set(2, String.valueOf(newAvailableBlocks));
                            PlayerData data 
                            player.sendMessage(ChatColor.GOLD + "You have " + newAvailableBlocks + " left to trade.");
                        }
                        else{
                            player.sendMessage(ChatColor.GOLD + "You only have " + availableBlocks + " available to trade.");
                        }
                        Files.write(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()), playerBlocks, StandardCharsets.UTF_8);





//                        try (Stream<String> lines = Files.lines(Paths.get("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId()))) {
//                            int availableBlocks = Integer.valueOf(lines.skip(1).findFirst().get());
//                            if(availableBlocks - Integer.valueOf(args[1]) > 0){
//                                int newAvailableBlocks = availableBlocks - Integer.valueOf(args[1]);
//
//                               }
//                            else{
//                                player.sendMessage("You only have " + availableBlocks + " available to trade.");
//                            }
//                        }

                    }catch (Exception e){
                        player.sendMessage(e.getMessage());
                        return false;
                    }
                }
            }
            else{
                player.sendMessage(ChatColor.GOLD + "Pay a minimum of 1 block.");
            }

            return false;
        }
        if(command.getName().equalsIgnoreCase("simon")){
            try{
                player.sendMessage("Got plugin griefprevention");
                File file = new File("plugins/GriefPreventionData/PlayerData/" + player.getUniqueId());
                player.sendMessage(file.getName());


            }catch (Exception e){
                player.sendMessage(e.getMessage());
            }
        }
        return false;
    }
}
