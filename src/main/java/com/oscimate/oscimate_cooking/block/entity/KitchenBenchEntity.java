package com.oscimate.oscimate_cooking.block.entity;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.gui.screen.KitchenBenchScreenHandler;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.ItemStringReader;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class KitchenBenchEntity extends BlockEntity implements KitchenBenchInventory, NamedScreenHandlerFactory {
    public final DefaultedList<ItemStack> input = DefaultedList.ofSize(10, ItemStack.EMPTY);

    public List<String> testRecipe = new ArrayList<String>(List.of(
        "minecraft:white_wool", "minecraft:white_wool", "minecraft:white_wool", "minecraft:diamond", "minecraft:white_wool", "minecraft:diamond", "minecraft:diamond", "minecraft:white_wool", "minecraft:diamond", "minecraft:string"
    ));

    public List<Item> testRecipe1 = new ArrayList<Item>(List.of(
            Items.WHITE_WOOL, Items.WHITE_WOOL, Items.WHITE_WOOL, Items.DIAMOND, Items.WHITE_WOOL, Items.DIAMOND, Items.DIAMOND, Items.WHITE_WOOL, Items.DIAMOND, Items.STRING
    ));

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
        boolean recipeAccepted = false;
        loop:
        for (int i = 0 ; i < 9; i++) {
            if(Registry.ITEM.get(new Identifier(blockEntity.testRecipe.get(i))) == blockEntity.input.get(i).getItem()) {
                recipeAccepted = true;
                continue loop;
            }
            else {
                recipeAccepted = false;
                break loop;
            }
        }
        if (recipeAccepted) {
            blockEntity.input.set(9, blockEntity.testRecipe1.get(9).getDefaultStack());
        } else {
            blockEntity.input.set(9, ItemStack.EMPTY);
        }
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return input;
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
        Inventories.readNbt(nbt, this.input);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.input);
    }

}
