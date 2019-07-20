package listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
        try {
            Player player = event.getPlayer();

            ItemStack stack = player.getInventory().getItemInMainHand();
            String lore = stack.getItemMeta().getLore().toString();
            int redeemAmount = 0;
            if(lore.contains("Claimblocks")){
                if(lore.contains(" ")){
                    redeemAmount = Integer.valueOf(lore.replaceAll("[^0-9]",""));
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "acb " + player.getName() + " " + redeemAmount);
                player.sendMessage(ChatColor.GOLD + "Succesfully redeemed " + redeemAmount + " claimblocks.");

                stack.setAmount(stack.getAmount()-1);
            }

        }
        catch (Exception e){

        }
    }
}
