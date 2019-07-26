package wailord2.rebelsanarchist.rebelsanarchist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsAnarchist extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("RebelsTransfer - RebelsTransfer is now enabled");
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        getLogger().info("RebelsTransfer - RebelsTransfer is now disabled");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockClick(PlayerInteractEvent event){

            try {
                Player player = event.getPlayer();

                ItemStack stack = player.getInventory().getItemInMainHand();
                String lore = stack.getItemMeta().getLore().toString();
                lore = lore.toLowerCase();

                if(lore.contains("anarchist")){
                    if(player.hasPermission("rebelsanarchist.tools")){

                    }
                    else{
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You have to be Anarchist to use that item.");
                        event.setCancelled(true);

                    }
                }

            }
            catch (Exception e){

            }

    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHitMob(EntityDamageByEntityEvent event){
        try {
            Player player = (Player)event.getDamager();

            ItemStack stack = player.getInventory().getItemInMainHand();
            String lore = stack.getItemMeta().getLore().toString();
            lore = lore.toLowerCase();
            if(lore.contains("anarchist")){
                if(player.hasPermission("rebelsanarchist.tools")){

                }
                else{
                    player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "MC-Rebels" + ChatColor.DARK_RED + "]" + ChatColor.RED + " You have to be Anarchist to use that item.");
                    event.setCancelled(true);

                }
            }

        }
        catch (Exception e){

        }

    }

}
