package ro.coderdojo.throwtnt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public final class EventsListener implements Listener {

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("Salut " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + "! Felicitări pentru primul mod de Minecraft!");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		World world = event.getPlayer().getWorld();
		TNTPrimed newTNTBlock = world.spawn(
				event.getPlayer().getLocation(), TNTPrimed.class);
		Vector velocityVector = event.getPlayer().getLocation().getDirection();
		System.out.println("Vectorul viteza este: " + velocityVector);
		velocityVector.multiply(2.5);
		System.out.println("Vectorul viteza multiplicat este: " + velocityVector);
		newTNTBlock.setIsIncendiary(true);
		newTNTBlock.setVelocity(velocityVector); //setez viteza si directia blocului nou creat
	}

	@EventHandler
	public void OnExplosion(EntityExplodeEvent event) {
		for (Block block : event.blockList()) {
			FallingBlock newBlock = block.
					getWorld()
					.spawnFallingBlock(block.getLocation(), block.getState().getData());
			newBlock.setVelocity(new Vector(0.1, 2, 0.2));
			block.setType(Material.AIR);
		}
	}
}
