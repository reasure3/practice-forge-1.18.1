package com.reasure.practice.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.reasure.practice.block.ModBlocks;
import com.reasure.practice.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override @NotNull
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
        }

        @Override @NotNull
        protected Iterable<Block> getKnownBlocks() {
            return knownBlocks;
        }

        @Override
        protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
            super.add(block, builder);
            knownBlocks.add(block);
        }
    }
}
