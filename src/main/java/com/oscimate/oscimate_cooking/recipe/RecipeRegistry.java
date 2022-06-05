package com.oscimate.oscimate_cooking.recipe;

import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init() {
        Registry.register(Registry.RECIPE_TYPE, new Identifier(Main.MODID, "kitchen_bench_crafting"), KitchenBenchRecipeType.KITCHEN_BENCH_CRAFTING);
//        Registry.register(Registry.RECIPE_TYPE, new Identifier(Main.MODID, KitchenBenchRecipe.Type.ID), KitchenBenchRecipe.Type.INSTANCE);
    }

}
