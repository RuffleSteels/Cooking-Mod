package com.oscimate.oscimate_cooking.recipe.mixing_bowl;

import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlInventory;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
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
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
            recipeMatcher.addInput(itemStack, 1);
        }
        return i == 3 && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(MixingBowlInventory inventory) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean fits(int var1, int var2) {
        return false;
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
