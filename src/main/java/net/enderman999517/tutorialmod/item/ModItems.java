package net.enderman999517.tutorialmod.item;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import net.enderman999517.tutorialmod.TutorialMod;
import net.enderman999517.tutorialmod.block.ModBlocks;
import net.enderman999517.tutorialmod.entity.ModBoats;
import net.enderman999517.tutorialmod.entity.ModEntities;
import net.enderman999517.tutorialmod.item.custom.DiceItem;
import net.enderman999517.tutorialmod.item.custom.MetalDetectorItem;
import net.enderman999517.tutorialmod.item.custom.ModArmorItem;
import net.enderman999517.tutorialmod.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    //items
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));
    public static final Item METAL_DETECTOR = registerItem("metal_detector",
            new MetalDetectorItem(new FabricItemSettings().maxDamage(64)));
    public static final Item RUBY_STAFF = registerItem("ruby_staff",
            new Item(new FabricItemSettings().maxCount(1)));
    public static final Item DICE = registerItem("dice", new DiceItem(new FabricItemSettings()));

    //tools
    public static final Item RUBY_PICKAXE = registerItem("ruby_pickaxe",
            new PickaxeItem(ModToolMaterial.RUBY, 2, 2f, new FabricItemSettings()));
    public static final Item RUBY_AXE = registerItem("ruby_axe",
            new AxeItem(ModToolMaterial.RUBY, 1000, -3.8f, new FabricItemSettings()));
    public static final Item RUBY_SHOVEL = registerItem("ruby_shovel",
            new ShovelItem(ModToolMaterial.RUBY, 0, 0f, new FabricItemSettings()));
    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new SwordItem(ModToolMaterial.RUBY, 5, 3f, new FabricItemSettings()));
    public static final Item RUBY_HOE = registerItem("ruby_hoe",
            new HoeItem(ModToolMaterial.RUBY, 0, 0f, new FabricItemSettings()));

    //armor
    public static final Item RUBY_HELMET = registerItem("ruby_helmet",
            new ModArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item RUBY_CHESTPLATE = registerItem("ruby_chestplate",
            new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item RUBY_LEGGINGS = registerItem("ruby_leggings",
            new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item RUBY_BOOTS = registerItem("ruby_boots",
            new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    //foods
    public static final Item TOMATO = registerItem("tomato", new Item(new FabricItemSettings().food(ModFoodComponents.TOMATO)));
    public static final Item CORN = registerItem("corn", new Item(new FabricItemSettings()));

    //seeds
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            new AliasedBlockItem(ModBlocks.TOMATO_CROP, new FabricItemSettings()));
    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new FabricItemSettings()));

    //fuels
    public static final Item COAL_BRIQUETTE = registerItem("coal_briquette",
            new Item(new FabricItemSettings()));

    //music disks
    public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc",
            new MusicDiscItem(7, ModSounds.BAR_BRAWL, new FabricItemSettings().maxCount(1), 122));

    //spawn eggs
    public static final Item PORCUPINE_SPAWN_EGG = registerItem("porcupine_spawn_egg",
            new SpawnEggItem(ModEntities.PORCUPINE, 0xa86518, 0x3b260f, new FabricItemSettings()));

    //signs
    public static final Item CHESTNUT_SIGN = registerItem("chestnut_sign",
            new SignItem(new FabricItemSettings().maxCount(16), ModBlocks.STANDING_CHESTNUT_SIGN, ModBlocks.WALL_CHESTNUT_SIGN));
    public static final Item HANGING_CHESTNUT_SIGN = registerItem("chestnut_hanging_sign",
            new HangingSignItem(ModBlocks.HANGING_CHESTNUT_SIGN, ModBlocks.WALL_HANGING_CHESTNUT_SIGN, new FabricItemSettings().maxCount(16)));

    //boats
    public static final Item CHESTNUT_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, false);
    public static final Item CHESTNUT_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.CHESTNUT_CHEST_BOAT_ID, ModBoats.CHESTNUT_BOAT_KEY, true);



    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
