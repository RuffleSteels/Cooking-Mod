package com.oscimate.oscimate_cooking.block.gui.screen;

import com.oscimate.oscimate_cooking.KitchenBenchInventory;
import com.oscimate.oscimate_cooking.KitchenBenchResultSlot;
import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.KitchenBenchRecipe;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class KitchenBenchScreenHandler extends AbstractRecipeScreenHandler<KitchenBenchInventory> {
    public final KitchenBenchInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    public final PlayerInventory playerInventory;

    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }


    public KitchenBenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Main.SCREEN_REGISTRY.KITCHEN_BENCH_SCREEN_HANDLER, syncId);
        this.input = new KitchenBenchInventory(this, 9);
        this.playerInventory = playerInventory;
        this.result = new CraftingResultInventory();
        this.context = context;

        checkSize(this.input, 9);
        this.input.onOpen(playerInventory.player);

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
                this.addSlot(new Slot(this.input, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }


        this.addSlot(new KitchenBenchResultSlot(playerInventory.player, this.input, this.result, 10, 124, 35));
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity playerEntity, KitchenBenchInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerEntity;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<KitchenBenchRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(KitchenBenchRecipe.Type.INSTANCE, craftingInventory, world);
            if (optional.isPresent()) {
                KitchenBenchRecipe spellRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, spellRecipe)) {
                    itemStack = spellRecipe.craft(craftingInventory);
                }
            }

            resultInventory.setStack(9, itemStack);
            handler.setPreviousTrackedSlot(9, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 8, itemStack));
        }
    }

    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.playerInventory.player, this.input, this.result));
    }

    @Override // Shift-Click interactions
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 8) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));
                if (!this.insertItem(itemStack2, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 9 && index < 46) {
                if (!this.insertItem(itemStack2, 0, 9, false)) {
                    if (index < 37) {
                        if (!this.insertItem(itemStack2, 37, 45, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 9, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 9, 45, false)) {
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
            if (index == 8) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) { return canUse(this.context, player, BlockRegistry.KITCHEN_BENCH); }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) { this.input.provideRecipeInputs(finder); }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super KitchenBenchInventory> recipe) { return recipe.matches(this.input, this.playerInventory.player.world); }

    @Override
    public int getCraftingResultSlotIndex() { return 9; }

    @Override
    public int getCraftingWidth() { return Integer.MAX_VALUE; }

    @Override
    public int getCraftingHeight() { return Integer.MAX_VALUE; }

    @Override
    public int getCraftingSlotCount() { return 9; }

    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }

    @Override
    public boolean canInsertIntoSlot(int index) { return index != this.getCraftingResultSlotIndex(); }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) { return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot); }


}
