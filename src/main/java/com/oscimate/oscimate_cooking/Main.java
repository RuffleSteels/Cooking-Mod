package com.oscimate.oscimate_cooking;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.ScreenRegistry;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeSerializer;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
import com.oscimate.oscimate_cooking.recipe.mixing_bowl.MixingBowlRecipe;
import com.oscimate.oscimate_cooking.recipe.mixing_bowl.MixingBowlSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
    public static final String MODID = "oscimate_cooking";
    public static final BlockRegistry BLOCK_REGISTRY = new BlockRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new ScreenRegistry();
    public static final KitchenBenchRecipeType KITCHEN_BENCH_RECIPE_TYPE = new KitchenBenchRecipeType();
    public static final KitchenBenchRecipeSerializer KITCHEN_BENCH_SERIALIZER = new KitchenBenchRecipeSerializer();


    @Override
    public void onInitialize() {
        KITCHEN_BENCH_RECIPE_TYPE.init();
        KITCHEN_BENCH_SERIALIZER.init();
        BLOCK_REGISTRY.registerAll();

        Registry.register(Registry.RECIPE_SERIALIZER, MixingBowlSerializer.ID, MixingBowlSerializer.INSTANCE);
        MixingBowlRecipe.Type.init();

    }
}
