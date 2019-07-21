package wailord2.autotpo.autotpo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Teleporter implements CommandExecutor {

    private Plugin plugin;
    private Player player;
    private Timer tpoTimer;
    private int atPlayer;

    public Teleporter(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        this.player = (Player)sender;

        if(command.getName().equalsIgnoreCase("tpostart")){
            if(args.length == 0){
                startFromFirstPlayer();
                return true;
            }
            return false;

        }
        else if(command.getName().equalsIgnoreCase("tpostop")){
            if(args.length == 0){
                if(tpoTimer != null){
                    player.sendMessage(ChatColor.GOLD + "Stopping automated tpo check.");
                    tpoTimer.cancel();
                    tpoTimer.purge();
                }
            }
            else{
                return false;
            }

        }
        return false;
    }

    public void startFromFirstPlayer(){
        Collection players = plugin.getServer().getOnlinePlayers();

        Iterator iterator = players.iterator();

        player.sendMessage(ChatColor.GOLD + "Starting automated tpo check.");

        if(iterator.hasNext()){
            tpoTimer = new Timer();
            tpoTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(iterator.hasNext()){
                        Player possibleNext = (Player)iterator.next();
                        if(possibleNext.getName().equalsIgnoreCase(player.getName())){
                            player.sendMessage(ChatColor.GOLD + "Skipping yourself");
                        }
                        else{
                            atPlayer++;
                            player.sendMessage(ChatColor.GOLD + "Teleporting to next player.");

                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Bukkit.dispatchCommand(player, "tpo " + possibleNext.getName());
                                }
                            });
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.GOLD + "Finished automated tpo check.");
                    }
                }
            } , 0, Integer.valueOf(String.valueOf(plugin.getConfig().get("interval"))));
        }
        else{
            player.sendMessage(ChatColor.GOLD + "No players found to check.");
        }
    }
}
