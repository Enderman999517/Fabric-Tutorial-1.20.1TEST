package net.enderman999517.tutorialmod.recipe;

import com.google.gson.JsonObject;
import net.enderman999517.tutorialmod.TutorialMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class GemPolishingRecipeSerializer implements RecipeSerializer<GemPolishingRecipe> {
    public static final GemPolishingRecipeSerializer INSTANCE = new GemPolishingRecipeSerializer();
    public static final Identifier ID = new Identifier(TutorialMod.MOD_ID, "gem_polishing");

    @Override
    public GemPolishingRecipe read(Identifier id, JsonObject json) {
        Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
        ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
        return new GemPolishingRecipe(id, input, output);
    }

    @Override
    public GemPolishingRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient input = Ingredient.fromPacket(buf);
        ItemStack output = buf.readItemStack();
        return new GemPolishingRecipe(id, input, output);
    }

    @Override
    public void write(PacketByteBuf buf, GemPolishingRecipe recipe) {
        recipe.getInput().write(buf);

        // In actual use, a DynamicRegistryManager should come from the game context
        DynamicRegistryManager registryManager = DynamicRegistryManager.EMPTY;
        buf.writeItemStack(recipe.getOutput(registryManager));
    }
}