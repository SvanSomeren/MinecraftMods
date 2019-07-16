package wailord2.rebelsnick.rebelsnick;

import org.bukkit.plugin.java.JavaPlugin;

public final class RebelsNick extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("RebelsNick - RebelsNick is now enabled");
        NickChanger nickChanger = new NickChanger();
        getCommand("nick").setExecutor(nickChanger);
        getCommand("nickcolor").setExecutor(nickChanger);
    }

    @Override
    public void onDisable(){
        getLogger().info("RebelsNick - RebelsNick is now disabled");
    }

}
