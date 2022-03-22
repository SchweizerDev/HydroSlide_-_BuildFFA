package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(BuildFFA.getInstance().getAllowBuild().contains(e.getPlayer())) return;
		if(e.getClickedBlock() != null) {
			switch(e.getClickedBlock().getType()) {
			case LEVER:
			case STONE_BUTTON:
			case DISPENSER:
			case FURNACE:
			case BURNING_FURNACE:
			case BREWING_STAND:
			case BEACON:
			case ANVIL:
			case HOPPER:
			case SOIL:
			case FIRE:
			case GOLD_PLATE:
			case IRON_PLATE:
			case WOOD_PLATE:
			case STONE_PLATE:
			case DROPPER:
			case TRAP_DOOR:
			case WOODEN_DOOR:
			case ITEM_FRAME:
			case BED:
			case BED_BLOCK:
			case FENCE_GATE:
			case NOTE_BLOCK:
			case ENCHANTMENT_TABLE:
			case COMMAND:
			case WORKBENCH:
			case TRAPPED_CHEST:
			case CHEST:
			case ENDER_CHEST:
			case IRON_DOOR:
			case IRON_DOOR_BLOCK:
				e.setCancelled(true);
				return;
			default:
				return;
			}
		}
	}
}
