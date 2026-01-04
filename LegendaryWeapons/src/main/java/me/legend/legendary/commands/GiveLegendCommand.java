package me.legend.legendary.commands;

import me.legend.legendary.items.LegendaryItemFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveLegendCommand implements CommandExecutor {

    private final me.legend.legendary.LegendaryWeapons plugin;

    public GiveLegendCommand(me.legend.legendary.LegendaryWeapons plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        if (args.length == 0) return false;

        var item = LegendaryItemFactory.create(args[0]);
        if (item == null) {
            p.sendMessage("§cТакого оружия нет в конфиге");
            return true;
        }

        p.getInventory().addItem(item);
        p.sendMessage("§aТы получил легендарное оружие!");
        return true;
    }
}
