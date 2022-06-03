package com.oscimate.oscimate_cooking.block.entity;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.KitchenBenchScreenHandler;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class KitchenBenchEntity extends BlockEntity implements KitchenBenchInventory, NamedScreenHandlerFactory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    public final Inventory result = KitchenBenchScreenHandler.result;

    public KitchenBenchEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.KITCHEN_BENCH_ENTITY, pos, state);
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

    public static void tick(World world, BlockPos pos, BlockState state, KitchenBenchEntity blockEntity) {
        ItemStack itemStack = blockEntity.inventory.get(1);
        if (itemStack.isOf(Items.ACACIA_BOAT)) {
            blockEntity.result.setStack(0, Items.FURNACE.getDefaultStack());
        } else {
            blockEntity.result.clear();
        }
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new KitchenBenchScreenHandler(syncId, inv, this);
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("Hi");
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

}
