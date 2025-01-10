package net.enderman999517.tutorialmod.util;

import net.enderman999517.tutorialmod.block.ModBlocks;
import net.enderman999517.tutorialmod.item.ModItems;
import net.enderman999517.tutorialmod.villager.ModVillagers;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.TOMATO, 7),
                            6, 5, 0.05f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 7),
                            new ItemStack(ModItems.TOMATO_SEEDS, 1),
                            2, 8, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.GOLD_INGOT, 16),
                            new ItemStack(Items.DIAMOND, 12),
                            new ItemStack(ModItems.CORN_SEEDS, 1),
                            1, 20, 1.0f));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RUBY, 32),
                            EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(Enchantments.PIERCING, 2)),
                            2, 3, 0.001f));
                });
        TradeOfferHelper.registerWanderingTraderOffers(1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_RUBY, 16),
                            new ItemStack(ModItems.METAL_DETECTOR, 3),
                            1, 3, 0.025f));
                });
        TradeOfferHelper.registerWanderingTraderOffers(2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RAW_RUBY, 1),
                            new ItemStack(ModItems.COAL_BRIQUETTE, 16),
                            1, 3, 0.025f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CORN, 32),
                            new ItemStack(ModBlocks.SOUND_BLOCK, 2),
                            1, 100, 5.0f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.SOUND_MASTER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.RUBY_SWORD, 1),
                            new ItemStack(ModItems.TOMATO, 16),
                            new ItemStack(ModItems.RUBY_STAFF, 1),
                            1, 100, 10.0f));
                });
    }
}
