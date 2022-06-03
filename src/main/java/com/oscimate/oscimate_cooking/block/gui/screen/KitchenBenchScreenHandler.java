package com.oscimate.oscimate_cooking.block.gui.screen;

import com.oscimate.oscimate_cooking.Main;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

public class KitchenBenchScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public static final Inventory result = new CraftingResultInventory();


    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }


    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(Main.SCREEN_REGISTRY.KITCHEN_BENCH_SCREEN_HANDLER, syncId);
        checkSize(inventory, 9);
        this.inventory = inventory;
        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

        //My Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }


        this.addSlot(new Slot(this.result, 0, 124, 35) {

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                for(int m = 0; m < 9; m++) {
                    KitchenBenchScreenHandler.this.inventory.setStack(m, ItemStack.EMPTY);
                }
            }
        });

    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

}
