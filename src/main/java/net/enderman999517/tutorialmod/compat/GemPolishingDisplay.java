package net.enderman999517.tutorialmod.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.enderman999517.tutorialmod.recipe.GemPolishingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GemPolishingDisplay extends BasicDisplay {

    public GemPolishingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GemPolishingDisplay(GemPolishingRecipe recipe) {
        //super(getInputList(recipe), List.of(EntryIngredients.of(recipe.getOutput(null))));
        super(getInputList(recipe), List.of(EntryIngredient.of(EntryStacks.of(recipe.getOutput(null)))));
    }


    private static List<EntryIngredient> getInputList(GemPolishingRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GemPolishingCategory.GEM_POLISHING;
    }
}
