package com.reasure.practice.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.reasure.practice.setup.ModBlockEntityTypes;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    @NotNull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(ModLootTables::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationTracker) {
        map.forEach((id, table) -> LootTables.validate(validationTracker, id, table));
    }

    public static class ModLootTables extends BlockLoot {
        private static final List<Block> knownBlocks = new ArrayList<>();

        @Override
        protected void addTables() {
            dropSelf(ModBlocks.RAW_SILVER_BLOCK.get());
            dropSelf(ModBlocks.SILVER_BLOCK.get());

            add(ModBlocks.SILVER_ORE.get(), ore -> createOreDrop(ore, ModItems.RAW_SILVER.get()));
            add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ore -> createOreDrop(ore, ModItems.RAW_SILVER.get()));

            add(ModBlocks.POWER_GEN.get(), gen -> createStandardTable(gen, ModBlockEntityTypes.POWER_GEN_BLOCK_ENTITY.get(), "Energy", "Info"));
            add(ModBlocks.ORE_GENERATOR.get(), gen -> createStandardTable(gen, ModBlockEntityTypes.ORE_GENERATOR_BLOCK_ENTITY.get(), "Energy", "Info", "Inventory"));
        }

        @Override
        @NotNull
        protected Iterable<Block> getKnownBlocks() {
            return knownBlocks;
        }

        @Override
        protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
            super.add(block, builder);
            knownBlocks.add(block);
        }

        protected LootTable.Builder createStandardTable(Block block, BlockEntityType<?> type, String... nbtCopyPathArr) {
            CopyNbtFunction.Builder copyNbtBuilder = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);
            for (String nbtCopyPath : nbtCopyPathArr) {
                copyNbtBuilder = copyNbtBuilder.copy(nbtCopyPath, "BlockEntityTag." + nbtCopyPath);
            }
            LootPool.Builder builder = LootPool.lootPool()
                    .name(Objects.requireNonNull(block.getRegistryName()).getPath())
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block)
                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                            .apply(copyNbtBuilder)
                            .apply(SetContainerContents.setContents(type)
                                    .withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents"))))
                    );
            return LootTable.lootTable().withPool(builder);
        }

    }
}
