package com.oscimate.oscimate_cooking;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.ScreenRegistry;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static final String MODID = "oscimate_cooking";
    public static final BlockRegistry BLOCK_REGISTRY = new BlockRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new ScreenRegistry();
    public static final KitchenBenchRecipeType KITCHEN_BENCH_RECIPE_TYPE = new KitchenBenchRecipeType();


    @Override
    public void onInitialize() {
        KITCHEN_BENCH_RECIPE_TYPE.init();
        BLOCK_REGISTRY.registerAll();
    }
}
