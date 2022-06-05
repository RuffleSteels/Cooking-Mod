package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class KitchenBenchShapeless extends ShapelessRecipe {
    public KitchenBenchShapeless(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public RecipeType<?> getType() {
        return KitchenBenchRecipeType.KITCHEN_BENCH_CRAFTING;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return KitchenBenchSerializer.SHAPELESS;
    }
}
