package com.oscimate.oscimate_cooking.recipe.kitchen_bench;

import com.oscimate.oscimate_cooking.block.gui.screen.kitchen_bench.KitchenBenchScreenHandler;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;

public class Inv2 extends CraftingInventory {
    public  DefaultedList<ItemStack> stacks;
    private ScreenHandler container;

    public Inv2(ScreenHandler container_1, int int_1, int int_2) {
        super(container_1, int_1, int_2);
        this.stacks = DefaultedList.ofSize(int_1 * int_2, ItemStack.EMPTY);
        this.container = container_1;
    }

    @Override
    public int size() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator var1 = this.stacks.iterator();
        if (!var1.hasNext()) {
            return true;
        }

        ItemStack itemStack_1 = (ItemStack) var1.next();
        while (itemStack_1.isEmpty()) {
            if (!var1.hasNext()) {
                return true;
            }
            itemStack_1 = (ItemStack) var1.next();
        }
        return false;
    }

    @Override
    public ItemStack getStack(int int_1) {
        return int_1 >= this.size() ? ItemStack.EMPTY : this.stacks.get(int_1);
    }

    @Override
    public ItemStack removeStack(int int_1) {
        return Inventories.removeStack(this.stacks, int_1);
    }

    @Override
    public ItemStack removeStack(int int_1, int int_2) {
        ItemStack itemStack_1 = Inventories.splitStack(this.stacks, int_1, int_2);
        if (!itemStack_1.isEmpty()) {
            this.container.onContentChanged(this);
        }
        return itemStack_1;
    }

    @Override
    public void setStack(int int_1, ItemStack itemStack_1) {
        this.stacks.set(int_1, itemStack_1);
        this.container.onContentChanged(this);
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher recipeFinder_1) {
        for (ItemStack itemStack_1 : this.stacks) {
            recipeFinder_1.addInput(itemStack_1);
        }
    }

    public void setContainer(KitchenBenchScreenHandler craftingStationContainer) {
        this.container = craftingStationContainer;
    }


}
