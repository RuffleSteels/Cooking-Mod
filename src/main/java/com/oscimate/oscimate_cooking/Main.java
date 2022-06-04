package com.oscimate.oscimate_cooking;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.entity.KitchenBenchEntity;
import com.oscimate.oscimate_cooking.block.gui.screen.KitchenBenchScreenHandler;
import com.oscimate.oscimate_cooking.block.gui.screen.ScreenRegistry;
import com.oscimate.oscimate_cooking.recipe.RecipeRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.screen.CraftingScreenHandler;

public class Main implements ModInitializer {
    public static final String MODID = "oscimate_cooking";
    public static final BlockRegistry BLOCK_REGISTRY = new BlockRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new ScreenRegistry();
    public static final RecipeRegistry RECIPE_REGISTRY = new RecipeRegistry();


    @Override
    public void onInitialize() {
        BLOCK_REGISTRY.registerAll();
        RECIPE_REGISTRY.init();
    }
}
