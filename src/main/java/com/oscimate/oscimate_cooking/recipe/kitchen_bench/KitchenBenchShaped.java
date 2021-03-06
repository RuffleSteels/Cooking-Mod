package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Objects;

public class KitchenBenchShaped implements CraftingRecipe {
    private final ShapedRecipe _recipe;

    public KitchenBenchShaped(ShapedRecipe recipe) {
        this._recipe = Objects.requireNonNull(recipe);
    }

    public RecipeType<?> getType() {
        return KitchenBenchRecipeType.KITCHEN_BENCH_CRAFTING;
    }

    @Override
    public Identifier getId() {
        return this._recipe.getId();
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return KitchenBenchRecipeSerializer.SHAPED;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        return this._recipe.matches(craftingInventory, world);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return this._recipe.craft(craftingInventory);
    }

    @Override
    public boolean fits(int width, int height) {
        return this._recipe.fits(width, height);
    }
    @Override
    public ItemStack getOutput() {
        return this._recipe.getOutput();
    }

    @Override
    public String getGroup() {
        return this._recipe.getGroup();
    }

    public static class Serializer implements RecipeSerializer<KitchenBenchShaped> {

        @Override
        public KitchenBenchShaped read(Identifier id, JsonObject json) {
            return new KitchenBenchShaped(RecipeSerializer.SHAPED.read(id, json));
        }

        @Override
        public KitchenBenchShaped read(Identifier id, PacketByteBuf buf) {
            return new KitchenBenchShaped(RecipeSerializer.SHAPED.read(id, buf));
        }

        @Override
        public void write(PacketByteBuf buf, KitchenBenchShaped recipe) {
            RecipeSerializer.SHAPED.write(buf, recipe._recipe);
        }
    }
}
