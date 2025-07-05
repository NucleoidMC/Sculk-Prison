package io.github.haykam821.sculkprison.game.player;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import xyz.nucleoid.plasmid.api.util.ItemStackBuilder;

public class WardenInventoryManager {
	private final ItemStack helmet;
	private final ItemStack chestplate;
	private final ItemStack leggings;
	private final ItemStack boots;

	private final ItemStack activeHelmet;

	public WardenInventoryManager(RegistryWrapper.WrapperLookup registries) {
		this.helmet = WardenInventoryManager.createArmorStack(registries, Items.LEATHER_HELMET, false);
		this.chestplate = WardenInventoryManager.createArmorStack(registries, Items.LEATHER_CHESTPLATE, false);
		this.leggings = WardenInventoryManager.createArmorStack(registries, Items.LEATHER_LEGGINGS, false);
		this.boots = WardenInventoryManager.createArmorStack(registries, Items.LEATHER_BOOTS, false);

		this.activeHelmet = WardenInventoryManager.createArmorStack(registries, Items.LEATHER_HELMET, true);
	}

	public void applyTo(ServerPlayerEntity player) {
		equipSilently(player, EquipmentSlot.HEAD, this.helmet.copy());
		equipSilently(player, EquipmentSlot.CHEST, this.chestplate.copy());
		equipSilently(player, EquipmentSlot.LEGS, this.leggings.copy());
		equipSilently(player, EquipmentSlot.FEET, this.boots.copy());
	}

	public void applyHelmet(ServerPlayerEntity player) {
		equipSilently(player, EquipmentSlot.HEAD, this.helmet.copy());
	}

	public void applyActiveHelmet(ServerPlayerEntity player) {
		equipSilently(player, EquipmentSlot.HEAD, this.activeHelmet.copy());
	}

	private static ItemStack createArmorStack(RegistryWrapper.WrapperLookup registries, ItemConvertible item, boolean active) {
		return ItemStackBuilder.of(item)
			.addEnchantment(registries, Enchantments.BINDING_CURSE, 1)
			.setDyeColor(active ? WardenColors.ACTIVE_ARMOR : WardenColors.INACTIVE_ARMOR)
			.setUnbreakable()
			.build();
	}

	private static void equipSilently(ServerPlayerEntity player, EquipmentSlot slot, ItemStack stack) {
		int slotId = slot.getOffsetEntitySlotId(PlayerInventory.MAIN_SIZE);
		player.getInventory().setStack(slotId, stack);
	}
}