package wailord2.rebelsnick.rebelsnick;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsNick extends JavaPlugin {

    public static FileConfiguration config;

    @Override
    public void onEnable(){
        getLogger().info("RebelsNick - RebelsNick is now enabled");
        NickChanger nickChanger = new NickChanger();
        config = getConfig();
        getCommand("nick").setExecutor(nickChanger);
        getCommand("nickcolor").setExecutor(nickChanger);
    }

    @Override
    public void onDisable(){
        getLogger().info("RebelsNick - RebelsNick is now disabled");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

    }
}
