package net.enderman999517.tutorialmod;

import net.enderman999517.tutorialmod.block.ModBlocks;
import net.enderman999517.tutorialmod.entity.ModEntities;
import net.enderman999517.tutorialmod.entity.custom.PorcupineEntity;
import net.enderman999517.tutorialmod.item.ModItemGroups;
import net.enderman999517.tutorialmod.item.ModItems;
import net.enderman999517.tutorialmod.sound.ModSounds;
import net.enderman999517.tutorialmod.util.ModCustomTrades;
import net.enderman999517.tutorialmod.util.ModLootTableModifiers;
import net.enderman999517.tutorialmod.villager.ModVillagers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModCustomTrades.registerCustomTrades();

		ModVillagers.registerVillagers();

		ModSounds.registerSounds();

		ModEntities.registerModEntities();

		ModLootTableModifiers.modifyLootTables();

		FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);

		FabricDefaultAttributeRegistry.register(ModEntities.PORCUPINE, PorcupineEntity.createPorcupineAttributes());


	}
}