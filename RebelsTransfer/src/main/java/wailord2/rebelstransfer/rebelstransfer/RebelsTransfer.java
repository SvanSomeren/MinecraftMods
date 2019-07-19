package wailord2.rebelstransfer.rebelstransfer;

import listeners.RightClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsTransfer extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("RebelsTransfer - RebelsTransfer is now enabled");
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
        Transferer transferer = new Transferer(getConfig(), this);
        getCommand("cbpay").setExecutor(transferer);
        getCommand("cbwithdraw").setExecutor(transferer);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("RebelsTransfer - RebelsTransfer is now disabled");
    }
}
