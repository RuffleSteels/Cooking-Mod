package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import com.oscimate.oscimate_cooking.KitchenBenchInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class KitchenBenchRecipe implements Recipe<KitchenBenchInventory> {

    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final Ingredient input4;
    private final Ingredient input5;
    private final Ingredient input6;
    private final Ingredient input7;
    private final Ingredient input8;
    private final Ingredient input9;
    private final ItemStack outputStack;
    private final Identifier identifier;
    private final DefaultedList<Ingredient> inputs;


    public KitchenBenchRecipe(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, Ingredient input5, Ingredient input6, Ingredient input7, Ingredient input8, Ingredient input9, ItemStack outputStack, Identifier identifier) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input5 = input5;
        this.input6 = input6;
        this.input7 = input7;
        this.input8 = input8;
        this.input9 = input9;
        this.outputStack = outputStack;
        this.identifier = identifier;

        inputs = DefaultedList.of();
        inputs.add(input1);
        inputs.add(input2);
        inputs.add(input3);
        inputs.add(input4);
        inputs.add(input5);
        inputs.add(input6);
        inputs.add(input7);
        inputs.add(input8);
        inputs.add(input9);
    }

    public Ingredient getInput1() { return input1; }

    public Ingredient getInput2() { return input2; }

    public Ingredient getInput3() { return input3; }

    public Ingredient getInput4() { return input4; }

    public Ingredient getInput5() { return input5; }

    public Ingredient getInput6() { return input6; }

    public Ingredient getInput7() { return input7; }

    public Ingredient getInput8() { return input8; }

    public Ingredient getInput9() { return input9; }

    @Override
    public boolean matches(KitchenBenchInventory inventory, World world) {
        if (inventory.size() < 9) return false;
        return input1.test(inventory.getStack(0)) &&
                input2.test(inventory.getStack(1)) &&
                input3.test(inventory.getStack(2)) &&
                input4.test(inventory.getStack(3)) &&
                input5.test(inventory.getStack(4)) &&
                input6.test(inventory.getStack(5)) &&
                input7.test(inventory.getStack(6)) &&
                input8.test(inventory.getStack(7)) &&
                input9.test(inventory.getStack(8));

    }

    @Override
    public ItemStack craft(KitchenBenchInventory inventory) {
        return outputStack.copy();
    }

    @Override
    public boolean fits(int var1, int var2) {
        return false;
    }

    public DefaultedList<Ingredient> getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getOutput() {
        return outputStack.copy();
    }

    @Override
    public Identifier getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return KitchenBenchSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<KitchenBenchRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final String ID = "kitchen_bench";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
}

