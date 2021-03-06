package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;

public class KitchenBenchRecipeSerializer<T extends Recipe<?>>{
    public static final RecipeSerializer<KitchenBenchShaped> SHAPED = RecipeSerializer.register("kitchen_bench_crafting_shaped", new KitchenBenchShaped.Serializer());
    public static final RecipeSerializer<KitchenBenchShapeless> SHAPELESS = RecipeSerializer.register("kitchen_bench_crafting_shapeless", new KitchenBenchShapeless.Serializer());

    public static void init() {

    }

}
