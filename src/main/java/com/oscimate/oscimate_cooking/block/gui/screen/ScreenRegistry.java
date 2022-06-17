package com.oscimate.oscimate_cooking.block.gui.screen;

import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.block.gui.screen.kitchen_bench.KitchenBenchScreen;
import com.oscimate.oscimate_cooking.block.gui.screen.kitchen_bench.KitchenBenchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ScreenRegistry {
    public static final ScreenHandlerType<KitchenBenchScreenHandler> KITCHEN_BENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Main.MODID, "kitchen_bench"), KitchenBenchScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, id, new ScreenHandlerType<T>(factory));
    }

    public void registerAllClient() {
        HandledScreens.register(ScreenRegistry.KITCHEN_BENCH_SCREEN_HANDLER, KitchenBenchScreen::new);
    }
}
