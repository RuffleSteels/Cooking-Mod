package com.oscimate.oscimate_cooking;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.ScreenRegistry;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    public static final String MODID = "oscimate_cooking";
    public static final BlockRegistry BLOCK_REGISTRY = new BlockRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new ScreenRegistry();


    @Override
    public void onInitialize() {
        BLOCK_REGISTRY.registerAll();
    }
}
