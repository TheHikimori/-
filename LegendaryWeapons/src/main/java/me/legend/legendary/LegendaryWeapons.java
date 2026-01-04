package me.legend.legendary;

import me.legend.legendary.commands.GiveLegendCommand;
import me.legend.legendary.listeners.WeaponListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LegendaryWeapons extends JavaPlugin {

    private static LegendaryWeapons instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("givelegend").setExecutor(new GiveLegendCommand(this));
        getServer().getPluginManager().registerEvents(new WeaponListener(this), this);
        getLogger().info("LegendaryWeapons loaded!");
    }

    public static LegendaryWeapons get() {
        return instance;
    }
}
