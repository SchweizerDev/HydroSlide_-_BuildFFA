package ch.luca.hydroslide.buildffa.item;

import ch.luca.hydroslide.buildffa.util.ItemBuilder;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemManager {

	@Getter
	private ItemStack sword, angel, stick, sandstone, helmet, chestplate, leggings, boots;
	
	@Getter
	private ItemStack[] defaultItems;
	
	@Getter
	private Inventory changeInventory;

	public ItemManager() {
		this.sword = new ItemBuilder(Material.GOLD_SWORD).name("§4Schwert").enchantment(Enchantment.DAMAGE_ALL, 2).setUnbreakable(true).build();
		this.angel = new ItemBuilder(Material.FISHING_ROD).name("§5Angel").setUnbreakable(true).build();
		this.stick = new ItemBuilder(Material.STICK).name("§3Knock Stick").enchantment(Enchantment.KNOCKBACK, 2).setUnbreakable(true).build();
		this.sandstone = new ItemBuilder(Material.SANDSTONE, 64, 0).name("§eBlöcke").build();
		
		this.helmet = new ItemBuilder(Material.LEATHER_HELMET).name("§cHelm").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build();
		this.chestplate = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("§cBrustplatte").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build();
		this.leggings = new ItemBuilder(Material.LEATHER_LEGGINGS).name("§cHose").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build();
		this.boots = new ItemBuilder(Material.LEATHER_BOOTS).name("§cSchuhe").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build();
		
		this.defaultItems = new ItemStack[36];
		this.defaultItems[0] = this.sword;
		this.defaultItems[1] = this.angel;
		this.defaultItems[2] = this.stick;
		this.defaultItems[6] = this.sandstone;
		this.defaultItems[7] = this.sandstone;
		this.defaultItems[8] = this.sandstone;
		
		ItemStack black = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 15).name("§e").build();
		ItemStack yellow = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 4).name("§e").build();
		
		this.changeInventory = Bukkit.createInventory(null, 6*9, "§eInventar Sortierung");
		for(int i = 0; i < this.changeInventory.getSize(); i++) {
			this.changeInventory.setItem(i, black);
		}
		this.changeInventory.setItem(4, yellow);
		this.changeInventory.setItem(13, yellow);
		this.changeInventory.setItem(22, yellow);
		this.changeInventory.setItem(29, yellow);
		this.changeInventory.setItem(30, yellow);
		this.changeInventory.setItem(31, yellow);
		this.changeInventory.setItem(32, yellow);
		this.changeInventory.setItem(33, yellow);
		this.changeInventory.setItem(39, yellow);
		this.changeInventory.setItem(40, yellow);
		this.changeInventory.setItem(41, yellow);
		this.changeInventory.setItem(49, yellow);
	}
}
