package com.oscimate.oscimate_cooking.block.gui.screen.kitchen_bench;

import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipeType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Function;

public class KitchenBenchScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
    public CraftingInventory inventory;
    public CraftingInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, handler -> new CraftingInventory(handler, 3, 3));
    }

    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, Function<ScreenHandler, CraftingInventory> inventory) {
        super(Main.SCREEN_REGISTRY.KITCHEN_BENCH_SCREEN_HANDLER, syncId);
        this.inventory = inventory.apply(this);
        this.result = new CraftingResultInventory();
        this.input = new CraftingInventory(this, 3, 3);
        this.player = playerInventory.player;
        this.context = context;
        this.inventory.onOpen(playerInventory.player);

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
                this.addSlot(new Slot(this.inventory, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }



        this.addSlot(new CraftingResultSlot(playerInventory.player, this.inventory, this.result, 0, 124, 35));
    }

    @Override
    public void close(PlayerEntity player) {
        this.input.onClose(player);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, blockPos) -> {
            KitchenBenchScreenHandler.updateResult(this, world, this.player, this.inventory, this.result);
            world.getBlockEntity(blockPos).markDirty();
        });
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity playerEntity, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        CraftingRecipe craftingRecipe;
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)playerEntity;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(KitchenBenchRecipeType.KITCHEN_BENCH_CRAFTING, craftingInventory, world);
        if (optional.isPresent() && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe = optional.get())) {
            itemStack = craftingRecipe.craft(craftingInventory);
        }
        resultInventory.setStack(0, itemStack);
        handler.setPreviousTrackedSlot(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }


    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.inventory, this.player.world);
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, BlockRegistry.KITCHEN_BENCH);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, (World)world, player));
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 10 && index < 46 ? !this.insertItem(itemStack2, 1, 10, false) && (index < 37 ? !this.insertItem(itemStack2, 37, 46, false) : !this.insertItem(itemStack2, 10, 37, false)) : !this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 9;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getCraftingSlotCount() {
        return 9;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }

    @Override
    public boolean canInsertIntoSlot(int index) { return index != this.getCraftingResultSlotIndex(); }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) { return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot); }


}
