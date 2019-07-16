package listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wailord2.rebelsnick.rebelsnick.RebelsNick;

import static org.bukkit.Bukkit.getLogger;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event){
        getLogger().info("found player name: "  + RebelsNick.config.get(event.getPlayer().getUniqueId().toString()));
        Player player = event.getPlayer();
        player.setDisplayName(RebelsNick.config.get(player.getUniqueId().toString()).toString());
    }
}
