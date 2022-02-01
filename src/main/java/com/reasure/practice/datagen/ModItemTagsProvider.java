package com.reasure.practice.datagen;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper helper) {
        super(generator, blockTagsProvider, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
    }

    @Override @NotNull
    public String getName() {
        return "Item Tags: " + modId;
    }
}
