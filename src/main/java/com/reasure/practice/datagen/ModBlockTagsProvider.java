package com.reasure.practice.datagen;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper helper) {
        super(generator, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(ModTags.Blocks.ORES_SILVER)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER)
                .add(ModBlocks.POWER_GEN.get())
                .add(ModBlocks.ORE_GENERATOR.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(ModTags.Blocks.ORES_SILVER)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER)
                .add(ModBlocks.POWER_GEN.get())
                .add(ModBlocks.ORE_GENERATOR.get());

        tag(Tags.Blocks.ORES)
                .addTag(ModTags.Blocks.ORES_SILVER);
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER);

        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(ModBlocks.SILVER_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get());

        tag(ModTags.Blocks.ORES_SILVER)
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get());
        tag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .add(ModBlocks.RAW_SILVER_BLOCK.get());
        tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER)
                .add(ModBlocks.SILVER_BLOCK.get());
    }

    @Override
    @NotNull
    public String getName() {
        return "Block Tags: " + modId;
    }
}
