package wailord2.autotpo.autotpo;

import org.bukkit.plugin.java.JavaPlugin;

public final class AutoTPO extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("AutoTPO - AutoTPO is now enabled");
        Teleporter teleporter = new Teleporter(this);
        saveConfig();
        getCommand("tpostart").setExecutor(teleporter);
        getCommand("tpopause").setExecutor(teleporter);
        getCommand("tpostop").setExecutor(teleporter);

    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("AutoTPO - AutoTPO is now disabled");
    }
}
