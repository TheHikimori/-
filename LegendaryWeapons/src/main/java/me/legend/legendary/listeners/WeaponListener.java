package me.legend.legendary.listeners;

import me.legend.legendary.LegendaryWeapons;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeaponListener implements Listener {

    private final LegendaryWeapons plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public WeaponListener(LegendaryWeapons plugin) {
        this.plugin = plugin;
    }

    private void actionBar(Player p, String msg) {
        Component c = LegacyComponentSerializer.legacyAmpersand().deserialize(msg);
        p.sendActionBar(c);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null || !e.getItem().hasItemMeta()) return;

        Player p = e.getPlayer();
        String name = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName());

        plugin.getConfig().getConfigurationSection("weapons").getKeys(false).forEach(id -> {
            String cfgName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("weapons." + id + ".name")));

            if (!cfgName.equals(name)) return;

            int cd = plugin.getConfig().getInt("weapons." + id + ".cooldown");
            long now = System.currentTimeMillis();
            long last = cooldowns.getOrDefault(p.getUniqueId(), 0L);

            if (last + cd * 1000L > now) {
                long left = (last + cd * 1000L - now) / 1000;
                actionBar(p, plugin.getConfig().getString("messages.cooldown")
                        .replace("%time%", String.valueOf(left)));
                return;
            }

            if (id.equals("sword")) {
                Block b = p.getTargetBlockExact(40);
                if (b != null) p.teleport(b.getLocation().add(0,1,0));
            }

            cooldowns.put(p.getUniqueId(), now);
            actionBar(p, plugin.getConfig().getString("messages.ready"));
        });
    }

    @EventHandler
    public void onArrow(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow a)) return;
        if (!(a.getShooter() instanceof Player p)) return;
        a.getWorld().createExplosion(a.getLocation(), 3f, false, false);
    }
}
