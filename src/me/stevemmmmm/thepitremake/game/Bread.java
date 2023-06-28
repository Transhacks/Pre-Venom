//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.game;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bread implements Listener {
    public Bread() {
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.BREAD) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 96, 2), true);
            EntityPlayer player = ((CraftPlayer)event.getPlayer()).getHandle();
            player.setAbsorptionHearts(Math.min(player.getAbsorptionHearts() + 4.0F, 4.0F));
        }

    }
}
