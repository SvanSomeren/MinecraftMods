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
    private Map<Player, Timer> playerTimers;

    public Teleporter(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{

            this.player = (Player)sender;
        }catch (Exception e){
            plugin.getLogger().info("Only players can use this command.");
        }

        if(command.getName().equalsIgnoreCase("tpostart")){
            if(args.length == 0){
                Timer timer = new Timer();
                playerTimers.put(player, timer);
                startFromPlayer(timer, 1);
                return true;
            }
            else if(args.length == 1){
                Timer timer = new Timer();
                playerTimers.put(player, timer);
                startFromPlayer(timer, Integer.valueOf(args[0]));
            }
            return false;

        }
        else if(command.getName().equalsIgnoreCase("tpostop")){
            if(args.length == 0) {
                try {
                    if (playerTimers.get(player) != null) {
                        player.sendMessage(ChatColor.GOLD + "Stopping automated tpo check.");
                        Timer timerToStop = playerTimers.get(player);
                        timerToStop.cancel();
                        timerToStop.purge();
                    }
                }
                catch (Exception e){
                    player.sendMessage(ChatColor.GOLD + "You do not have a timer running at the moment.");
                }
            }

            else{
                return false;
            }

        }
        return false;
    }

    public void startFromPlayer(Timer timer, int firstPlayer){
        Collection players = plugin.getServer().getOnlinePlayers();

        Iterator iterator = players.iterator();

        player.sendMessage(ChatColor.GOLD + "Starting automated tpo check.");

        if(iterator.hasNext()){

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (iterator.hasNext()) {
                            Player possibleNext = (Player) iterator.next();
                            if (possibleNext.getName().equalsIgnoreCase(player.getName())) {
                                player.sendMessage(ChatColor.GOLD + "Skipping yourself");
                            } else {
                                player.sendMessage(ChatColor.GOLD + "Teleporting to next player.");

                                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        Bukkit.dispatchCommand(player, "tpo " + possibleNext.getName());
                                    }
                                });
                            }
                        } else {
                            player.sendMessage(ChatColor.GOLD + "Finished automated tpo check.");
                        }
                    }
                    catch (Exception e){

                    }
                }
            } , 0, Integer.valueOf(String.valueOf(plugin.getConfig().get("interval"))));
        }
        else{
            player.sendMessage(ChatColor.GOLD + "No players found to check.");
        }
    }
}
