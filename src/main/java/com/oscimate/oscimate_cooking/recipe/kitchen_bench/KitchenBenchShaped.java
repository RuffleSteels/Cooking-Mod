package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class KitchenBenchShaped extends ShapedRecipe {
    public KitchenBenchShaped(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
        super(id, group, width, height, input, output);
    }

    @Override
    public RecipeType<?> getType() {
        return KitchenBenchRecipeType.KITCHEN_BENCH_CRAFTING;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return KitchenBenchSerializer.SHAPED;
    }
}
