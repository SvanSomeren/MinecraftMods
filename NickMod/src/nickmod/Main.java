package nickmod;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("NickPlugin - NickPlugin is now enabled");
        NickChanger nickChanger = new NickChanger();
        getCommand("nick").setExecutor(nickChanger);
        getCommand("nickcolor").setExecutor(nickChanger);
    }

    @Override
    public void onDisable(){
        getLogger().info("NickPlugin - NickPlugin is now disabled");
    }
}
