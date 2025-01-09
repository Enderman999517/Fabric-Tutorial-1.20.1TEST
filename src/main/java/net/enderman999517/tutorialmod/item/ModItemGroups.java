package net.enderman999517.tutorialmod.item;

import net.enderman999517.tutorialmod.TutorialMod;
import net.enderman999517.tutorialmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(TutorialMod.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ruby"))
                    .icon(() -> new ItemStack(ModItems.RUBY)).entries((displayContext, entries) -> {
                        //items
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.METAL_DETECTOR);
                        entries.add(ModItems.RUBY_STAFF);

                        //tools
                        entries.add(ModItems.RUBY_PICKAXE);
                        entries.add(ModItems.RUBY_AXE);
                        entries.add(ModItems.RUBY_SHOVEL);
                        entries.add(ModItems.RUBY_SWORD);
                        entries.add(ModItems.RUBY_HOE);

                        //armor
                        entries.add(ModItems.RUBY_HELMET);
                        entries.add(ModItems.RUBY_CHESTPLATE);
                        entries.add(ModItems.RUBY_LEGGINGS);
                        entries.add(ModItems.RUBY_BOOTS);

                        //blocks
                            //ores
                            entries.add(ModBlocks.RUBY_BLOCK);
                            entries.add(ModBlocks.RAW_RUBY_BLOCK);
                            entries.add(ModBlocks.RUBY_ORE);
                            entries.add(ModBlocks.DEEPSLATE_RUBY_ORE);
                            entries.add(ModBlocks.NETHER_RUBY_ORE);
                            entries.add(ModBlocks.END_STONE_RUBY_ORE);

                            //custom
                            entries.add(ModBlocks.SOUND_BLOCK);

                            //blocks
                            entries.add(ModBlocks.RUBY_STAIRS);
                            entries.add(ModBlocks.RUBY_SLAB);
                            entries.add(ModBlocks.RUBY_FENCE);
                            entries.add(ModBlocks.RUBY_FENCE_GATE);
                            entries.add(ModBlocks.RUBY_WALL);
                            entries.add(ModBlocks.RUBY_BUTTON);
                            entries.add(ModBlocks.RUBY_PRESSURE_PLATE);
                            entries.add(ModBlocks.RUBY_DOOR);
                            entries.add(ModBlocks.RUBY_TRAPDOOR);

                        //fuels
                        entries.add(ModItems.COAL_BRIQUETTE);

                        //foods
                        entries.add(ModItems.TOMATO);

                        //random
                        entries.add(Items.DIAMOND);

                    }).build());

    public static void registerItemGroups() {
        TutorialMod.LOGGER.info("Registering Item Groups for " + TutorialMod.MOD_ID);
    }
}
