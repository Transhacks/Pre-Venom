//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.managers.enchants;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BowManager implements Listener {
    private static BowManager instance;
    private final HashMap<Arrow, PlayerInventory> data = new HashMap();

    public BowManager() {
    }

    public static BowManager getInstance() {
        if (instance == null) {
            instance = new BowManager();
        }

        return instance;
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.getProjectile() instanceof Arrow && ((Arrow)event.getProjectile()).getShooter() instanceof Player) {
            this.data.put((Arrow)event.getProjectile(), ((Player)((Arrow)event.getProjectile()).getShooter()).getInventory());
        }

    }

    public void registerArrow(Arrow arrow, Player player) {
        this.data.put(arrow, player.getInventory());
    }

    public ItemStack getBowFromArrow(Arrow arrow) {
        Iterator var2 = this.data.keySet().iterator();

        Arrow arr;
        do {
            if (!var2.hasNext()) {
                return new ItemStack(Material.BOW);
            }

            arr = (Arrow)var2.next();
        } while(!arr.equals(arrow));

        return ((PlayerInventory)this.data.get(arr)).getItemInHand();
    }

    public ItemStack getLeggingsFromArrow(Arrow arrow) {
        return ((PlayerInventory)this.data.get(arrow)).getLeggings();
    }
}
