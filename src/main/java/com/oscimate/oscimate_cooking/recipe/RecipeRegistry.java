package com.oscimate.oscimate_cooking.recipe;

import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipe;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init() {
        Registry.register(Registry.RECIPE_SERIALIZER, KitchenBenchSerializer.ID, KitchenBenchSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(Main.MODID, KitchenBenchRecipe.Type.ID), KitchenBenchRecipe.Type.INSTANCE);
    }

}
