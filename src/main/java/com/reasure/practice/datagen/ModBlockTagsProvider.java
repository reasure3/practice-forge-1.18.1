package com.reasure.practice.datagen;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.ModBlocks;
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
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocks.SILVER_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get())
                .add(ModBlocks.SILVER_BLOCK.get());
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.SILVER_ORE.get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.get());
    }

    @Override @NotNull
    public String getName() {
        return "Block Tags: " + modId;
    }
}
