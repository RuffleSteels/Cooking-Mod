package com.oscimate.oscimate_cooking.block.entity.kitchen_bench;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.kitchen_bench.KitchenBenchScreenHandler;
import com.oscimate.oscimate_cooking.recipe.kitchen_bench.Inv2;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class KitchenBenchEntity extends LockableContainerBlockEntity implements NamedScreenHandlerFactory, InventoryProvider {
    public Inv2 inventory;
    DefaultedList<ItemStack> listTag = DefaultedList.ofSize(9);

    public KitchenBenchEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.KITCHEN_BENCH_ENTITY, pos, state);
        inventory = new Inv2(new ScreenHandler(null, 0) {
            @Override
            public boolean canUse(PlayerEntity var1) {
                return true;
            }
        }, 3, 3);
    }


    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= this.size() ? ItemStack.EMPTY : inventory.stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public Text getDisplayName() {
        return getContainerName();
    }

    @Override
    protected Text getContainerName() {
        return new LiteralText("poo");
    }

//    public DefaultedList<ItemStack> getDefaultStack() {
//        DefaultedList<ItemStack> inventoryWritten = DefaultedList.ofSize(9, ItemStack.EMPTY);
//        for (int i = 0; i < 9; i++) {
//            inventoryWritten.add(i, inventory.getStack(i));
//        }
//        return inventoryWritten;
//    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory.stacks);
        for (int i = 0; i < 9; i++) {
            inventory.setStack(i, inventory.stacks.get(i));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        for (int i = 0; i < 9; i++) {
            listTag.add(i, inventory.getStack(i));
        }
//        System.out.println(inventory.stacks);
        Inventories.writeNbt(nbt, inventory.stacks);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new KitchenBenchScreenHandler(syncId, inv, ScreenHandlerContext.create(this.world, this.pos), handler -> this.inventory);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return createMenu(syncId, playerInventory, playerInventory.player);
    }

    @Override
    public void markDirty() {
        super.markDirty();
//        System.out.println(inventory);
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!(player.currentScreenHandler instanceof KitchenBenchScreenHandler)) return;
        for (int i = 1; i < 10; ++i) {
            this.inventory.setStack(i-1, player.currentScreenHandler.slots.get(i).getStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
        return null;
    }

    @Override
    public void clear() {

    }
}
