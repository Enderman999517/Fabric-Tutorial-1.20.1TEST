package net.enderman999517.tutorialmod.recipe;

import net.minecraft.recipe.RecipeType;

public class GemPolishingRecipeType implements RecipeType<GemPolishingRecipe> {
    public static final GemPolishingRecipeType INSTANCE = new GemPolishingRecipeType();
    public static final String ID = "gem_polishing";

    private GemPolishingRecipeType() {}
}

