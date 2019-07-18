package wailord2.rebelstransfer.rebelstransfer;

import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsTransfer extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("RebelsTransfer - RebelsTransfer is now enabled");
        Transferer transferer = new Transferer(getConfig(), this);
        getCommand("cbpay").setExecutor(transferer);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("RebelsTransfer - RebelsTransfer is now disabled");
    }
}
