package me.legend.legendary.items;

import me.legend.legendary.LegendaryWeapons;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class LegendaryItemFactory {

    public static ItemStack create(String id) {
        ConfigurationSection sec = LegendaryWeapons.get().getConfig().getConfigurationSection("weapons." + id);
        if (sec == null) return null;

        Material mat = Material.valueOf(sec.getString("material"));
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(color(sec.getString("name")));
        meta.setLore(color(sec.getStringList("lore")));
        meta.setUnbreakable(true);

        item.setItemMeta(meta);
        return item;
    }

    private static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static List<String> color(List<String> l) {
        return l.stream().map(LegendaryItemFactory::color).collect(Collectors.toList());
    }
}
