package net.enderman999517.tutorialmod.world.tree;

import net.enderman999517.tutorialmod.TutorialMod;
import net.enderman999517.tutorialmod.mixin.TrunkPlacerTypeInvoker;
import net.enderman999517.tutorialmod.world.tree.custom.ChestnutTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> CHESTNUT_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("chestnut_trunk_placer", ChestnutTrunkPlacer.CODEC);

    public static void register() {
        TutorialMod.LOGGER.info("Registering Trunk Placers for " + TutorialMod.MOD_ID);
    }
}
