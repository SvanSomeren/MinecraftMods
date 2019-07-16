package wailord2.rebelsnick.rebelsnick;

import listeners.PlayerJoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsNick extends JavaPlugin{

    public static FileConfiguration config;

    @Override
    public void onEnable(){
        getLogger().info("RebelsNick - RebelsNick is now enabled");
        NickChanger nickChanger = new NickChanger();
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        config = getConfig();
        getCommand("nick").setExecutor(nickChanger);
        getCommand("nickcolor").setExecutor(nickChanger);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("RebelsNick - RebelsNick is now disabled");
    }

}
