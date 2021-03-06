package com.oscimate.oscimate_cooking.block;

import com.oscimate.oscimate_cooking.Main;
import com.oscimate.oscimate_cooking.block.entity.kitchen_bench.KitchenBenchEntity;
import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

    // Block Registry
    public static final KitchenBench KITCHEN_BENCH = new KitchenBench(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(4.0f));
    public static final MixingBowl MIXING_BOWL = new MixingBowl(FabricBlockSettings.of(Material.METAL).nonOpaque().strength(4.0f));

    // Block Entities
    public static BlockEntityType<KitchenBenchEntity> KITCHEN_BENCH_ENTITY;
    public static BlockEntityType<MixingBowlEntity> MIXING_BOWL_ENTITY;


    public Identifier getIdentifier(String blockName) {
        return new Identifier(Main.MODID, blockName);
    }

    public void registerBlock(Identifier identifier, Block block) {
        Registry.register(Registry.BLOCK, identifier, block);
    }

    public void registerBlockItem(Identifier identifier, Block block, ItemGroup itemGroup) {
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }

    public void registerAll() {
        registerBlock(getIdentifier("kitchen_bench"), KITCHEN_BENCH);
        registerBlockItem(getIdentifier("kitchen_bench"), KITCHEN_BENCH, ItemGroup.BUILDING_BLOCKS);

        registerBlock(getIdentifier("mixing_bowl"), MIXING_BOWL);
        registerBlockItem(getIdentifier("mixing_bowl"), MIXING_BOWL, ItemGroup.FOOD);

        KITCHEN_BENCH_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, getIdentifier("kitchen_bench_entity"), FabricBlockEntityTypeBuilder.create(KitchenBenchEntity::new, KITCHEN_BENCH).build(null));
        MIXING_BOWL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, getIdentifier("mixing_bowl_entity"), FabricBlockEntityTypeBuilder.create(MixingBowlEntity::new, MIXING_BOWL).build(null));
    }

}
