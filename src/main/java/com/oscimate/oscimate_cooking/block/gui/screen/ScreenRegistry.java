package com.oscimate.oscimate_cooking.block.gui.screen;

import com.oscimate.oscimate_cooking.Main;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenRegistry {
    public final ScreenHandlerType<KitchenBenchScreenHandler> KITCHEN_BENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(getIdentifier("kitchen_bench"), KitchenBenchScreenHandler::new);

    public Identifier getIdentifier(String blockName) {
        return new Identifier(Main.MODID, blockName);
    }

    public void registerAllClient() {
        net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry.register(KITCHEN_BENCH_SCREEN_HANDLER, KitchenBenchScreen::new);
    }
}
