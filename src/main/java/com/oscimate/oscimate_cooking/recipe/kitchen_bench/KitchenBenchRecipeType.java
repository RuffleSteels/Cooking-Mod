package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

public class KitchenBenchRecipeType<T extends Recipe<?>> {
    public static final RecipeType<CraftingRecipe> KITCHEN_BENCH_CRAFTING = RecipeType.register("kitchen_bench_crafting");

    public static void init() {

    }
}
