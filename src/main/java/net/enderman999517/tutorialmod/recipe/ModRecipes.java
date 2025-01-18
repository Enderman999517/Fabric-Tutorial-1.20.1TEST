package net.enderman999517.tutorialmod.recipe;

import net.enderman999517.tutorialmod.TutorialMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, new Identifier(TutorialMod.MOD_ID, GemPolishingRecipeType.ID), GemPolishingRecipeType.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, GemPolishingRecipeSerializer.ID, GemPolishingRecipeSerializer.INSTANCE);
    }
}
