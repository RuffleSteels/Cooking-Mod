package com.oscimate.oscimate_cooking.recipe.mixing_bowl;

import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlInventory;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MixingBowlRecipe implements Recipe<MixingBowlInventory> {
    private final Ingredient inputA;
    private final Ingredient inputB;
    private final Ingredient inputC;
    private final ItemStack result;
    private final Identifier id;

    public MixingBowlRecipe(Identifier id, ItemStack result, Ingredient inputA, Ingredient inputB, Ingredient inputC) {
        this.id = id;
        this.inputA = inputA;
        this.inputB = inputB;
        this.inputC = inputC;
        this.result = result;
    }

    public Ingredient getInputA() {
        return this.inputA;
    }

    public Ingredient getInputB() {
        return this.inputB;
    }

    public Ingredient getInputC() {
        return this.inputC;
    }

    @Override
    public boolean matches(MixingBowlInventory inventory, World world) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (inputA.test(itemStack) && !bl3) {
                bl3 = true;
                continue;
            }
            if (inputB.test(itemStack) && !bl2) {
                bl2 = true;
                continue;
            }
            if (inputC.test(itemStack) && !bl) {
                bl = true;
                continue;
            }
            return false;
        }
        return bl && bl3 && bl2;
    }

    @Override
    public ItemStack craft(MixingBowlInventory inventory) {
        return this.getOutput().copy();
    }
    @Override
    public boolean fits(int var1, int var2) {
        return true;
    }
    @Override
    public ItemStack getOutput() {
        return result;
    }



    @Override
    public Identifier getId() {
        return id;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    public static class Type implements RecipeType<MixingBowlRecipe> {
        // Define ExampleRecipe.Type as a singleton by making its constructor private and exposing an instance.
        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final RecipeType<MixingBowlRecipe> MIXING_BOWL_CRAFTING = RecipeType.register("mixing_bowl");

        public static void init() {}
    }

    @Override
    public RecipeType<?> getType() {
        return MixingBowlRecipe.Type.MIXING_BOWL_CRAFTING;
    }


}
