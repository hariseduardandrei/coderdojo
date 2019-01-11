package ro.coderdojo.pets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreature;
import org.bukkit.event.player.PlayerInteractEvent;

public class Main extends JavaPlugin implements Listener {

	public static HashMap<String, List<Entity>> pets = new HashMap<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		int random = new Random().nextInt(8);
		switch (random) {
			case 0:
				createPet(player, EntityType.SHEEP);
				break;
			case 1:
				createPet(player, EntityType.COW);
				break;
			case 2:
				createPet(player, EntityType.CHICKEN);
				break;
			case 3:
				createPet(player, EntityType.DONKEY);
				break;
			case 4:
				createPet(player, EntityType.HORSE);
				break;
			case 5:
				createPet(player, EntityType.LLAMA);
				break;
			case 6:
				createPet(player, EntityType.PIG);
				break;
			case 7:
				createPet(player, EntityType.RABBIT);
				break;
		}
		
	}

	public void createPet(Player player, EntityType type) {
		Entity entity = (Entity) player.getWorld().spawnEntity(player.getLocation(), type);
		entity.setCustomName(player.getName());
		entity.setCustomNameVisible(true);
		if(pets.get(player.getName()) == null) {
			pets.put(player.getName(), new ArrayList<>());
		}
		pets.get(player.getName()).add(entity);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();
		if (pets.containsKey(player.getName())) {
			for(Entity pet : pets.get(player.getName())) {
				followPlayer((Creature) pet, player, 1.6);
			}
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
		int random = new Random().nextInt(6);
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

	@EventHandler
	public void onDmg(EntityDamageByEntityEvent event) {
		Entity entity = (Entity) event.getEntity();
		if (pets.containsValue(entity)) {
			event.setCancelled(true);
		}
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

}
