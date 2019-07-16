package testmod;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("Testplugin - This is wailord2's plugin :)");
        getCommand("wailord").setExecutor(new Command());
    }

    @Override
    public void onDisable(){
        getLogger().info("Testplugin - Disabling plugin :(");
    }

}
