package net.enderman999517.tutorialmod.world.tree;

import net.enderman999517.tutorialmod.TutorialMod;
import net.enderman999517.tutorialmod.mixin.FoliagePlacerTypeInvoker;
import net.enderman999517.tutorialmod.world.tree.custom.ChestnutFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> CHESTNUT_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("chestnut_foliage_placer", ChestnutFoliagePlacer.CODEC);

    public static void register() {
        TutorialMod.LOGGER.info("Registering Foliage Placers for " + TutorialMod.MOD_ID);
    }

}
