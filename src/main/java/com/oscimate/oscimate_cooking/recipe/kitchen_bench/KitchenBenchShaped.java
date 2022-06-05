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
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 0; j < craftingInventory.size(); ++j) {
            ItemStack itemStack = craftingInventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
            recipeMatcher.addInput(itemStack, 1);
        }
        return i == this._recipe.getIngredients().size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return this._recipe.getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this._recipe.getIngredients().size();
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

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }

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
