package wailord2.rebelstransfer.rebelstransfer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsTransfer extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("RebelsTransfer - RebelsTransfer is now enabled");
        FileConfiguration blockConfig = Bukkit.getPluginManager().getPlugin("griefprevention").getConfig();
        Transferer transferer = new Transferer();
        getCommand("nick").setExecutor(transferer);
        getCommand("nickcolor").setExecutor(transferer);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("RebelsNick - RebelsNick is now disabled");
    }
}
