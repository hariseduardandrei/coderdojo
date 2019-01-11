package ro.coderdojo.pets;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreature;

public class Main extends JavaPlugin implements Listener {

	public static HashMap<String, Entity> pets = new HashMap<String, Entity>();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		createPet(player, EntityType.SHEEP);
	}

	public void createPet(Player player, EntityType type) {
		Entity entity = player.getWorld().spawnEntity(player.getLocation(), type);
		entity.setCustomName(player.getName());
		entity.setCustomNameVisible(true);
		pets.put(player.getName(), entity);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (pets.containsKey(player.getName())) {
			followPlayer((Creature) pets.get(player.getName()), player, 1.1);
		}
	}

	public void followPlayer(Creature creature, Player player, double speed) {
		Location location = player.getLocation();
		addRandomMovement(location);
		if (location.distanceSquared(creature.getLocation()) > 100) {
			if (!player.isOnGround()) {
				return;
			}
			creature.teleport(location);
		} else {
			((CraftCreature) creature).getHandle().getNavigation().a(location.getX(), location.getY(), location.getZ(), speed);
		}
	}

	public void addRandomMovement(Location location) {
		Random rnd = new Random();
		int random = rnd.nextInt(6);
		switch (random) {
			case 0:
				location.add(1.5, 0, 1.5);
				break;
			case 1:
				location.add(0, 0, 1.5);
				break;
			case 2:
				location.add(1.5, 0, 0);
				break;
			case 3:
				location.subtract(1.5, 0, 1.5);
				break;
			case 4:
				location.subtract(0, 0, 1.5);
				break;
			case 5:
				location.subtract(1.5, 0, 0);
				break;
		}
	}

//	@EventHandler
//	public void onDmg(EntityDamageByEntityEvent event) {
//		Entity entity = (Entity) event.getEntity();
//		if (pets.containsValue(entity)) {
//			event.setCancelled(true);
//		}
//	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

}
