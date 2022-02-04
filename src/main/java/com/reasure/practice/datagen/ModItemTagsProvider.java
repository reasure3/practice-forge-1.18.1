package com.reasure.practice.datagen;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.setup.ModItems;
import com.reasure.practice.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
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
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        copy(ModTags.Blocks.ORES_SILVER, ModTags.Items.ORES_SILVER);
        copy(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER);
        copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);

        tag(Tags.Items.RAW_MATERIALS)
                .addTag(ModTags.Items.RAW_MATERIALS_SILVER);
        tag(Tags.Items.INGOTS)
                .addTag(ModTags.Items.INGOTS_SILVER);
        tag(Tags.Items.NUGGETS)
                .addTag(ModTags.Items.NUGGETS_SILVER);

        tag(ModTags.Items.RAW_MATERIALS_SILVER)
                .add(ModItems.RAW_SILVER.get());
        tag(ModTags.Items.INGOTS_SILVER)
                .add(ModItems.SILVER_INGOT.get());
        tag(ModTags.Items.NUGGETS_SILVER)
                .add(ModItems.SILVER_NUGGET.get());
    }

    @Override
    @NotNull
    public String getName() {
        return "Item Tags: " + modId;
    }
}
