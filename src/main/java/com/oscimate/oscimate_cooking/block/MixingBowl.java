package com.oscimate.oscimate_cooking.block;

import com.oscimate.oscimate_cooking.block.entity.kitchen_bench.KitchenBenchEntity;
import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlEntity;
import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlInventory;
import com.oscimate.oscimate_cooking.recipe.mixing_bowl.MixingBowlRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MixingBowl extends BlockWithEntity {
    public static final BooleanProperty WATERED = BooleanProperty.of("watered");
    public static final IntProperty BATTER = IntProperty.of("batter", 0, 3);

    public MixingBowl(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        DefaultedList<ItemStack> inventory = ((MixingBowlEntity)world.getBlockEntity(pos)).getItems();

        world.setBlockState(pos, state.with(BATTER, this.getState(inventory)));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERED, BATTER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HitboxFields.MIXING_BOWL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MixingBowlEntity be = ((MixingBowlEntity) world.getBlockEntity(pos));
        if(player.getStackInHand(hand).isOf(Items.WOODEN_SHOVEL)) {
            if (!world.isClient()) {
                System.out.println("wooden shovel");
                Optional<MixingBowlRecipe> match = world.getRecipeManager().getFirstMatch(MixingBowlRecipe.Type.MIXING_BOWL_CRAFTING, be, world);

                if (match.isPresent()) {
                    System.out.println("hi");
                    be.clear();
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), match.get().getOutput().copy());
                }
            }
        } else {
            if (!player.getStackInHand(hand).isEmpty()) {
                loop:
                for (int i = 0; i < be.size(); i++) {
                    if (be.getStack(i).isEmpty()) {
                        be.setStack(i, player.getStackInHand(hand).split(1));
                        break loop;
                    }
                }
            } else {
                loop:
                for (int i = 0; i < be.size(); i++) {
                    if (!be.getStack(i).isEmpty()) {
                        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), be.getStack(i));
                        be.removeStack(i, 1);
                        break loop;
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public Integer getState(DefaultedList<ItemStack> itemStacks) {
        int batterState = 3;
        for (int i = 0; i < itemStacks.size(); i++) {
            if (itemStacks.get(i).isEmpty()) {
                batterState -= 1;
            }
        }
        return batterState;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockRegistry.MIXING_BOWL_ENTITY, ((world1, pos, state1, blockEntity) -> MixingBowlEntity.tick(world1, pos, state1, blockEntity)));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MixingBowlEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MixingBowlEntity mixingBowlEntity) {
                ItemScatterer.spawn(world, pos, mixingBowlEntity.getItems());
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
