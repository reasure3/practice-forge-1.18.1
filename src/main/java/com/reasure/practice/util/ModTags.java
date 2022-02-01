package com.reasure.practice.util;

import com.reasure.practice.PracticeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> ORES_SILVER = createCommonTag("ores/silver");
        public static final Tags.IOptionalNamedTag<Block> STORAGE_BLOCKS_RAW_SILVER = createCommonTag("storage_blocks/raw_silver");
        public static final Tags.IOptionalNamedTag<Block> STORAGE_BLOCKS_SILVER = createCommonTag("storage_blocks/silver");

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(PracticeMod.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createCommonTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final Tags.IOptionalNamedTag<Item> ORES_SILVER = createCommonTag("ores/silver");
        public static final Tags.IOptionalNamedTag<Item> STORAGE_BLOCKS_RAW_SILVER = createCommonTag("storage_blocks/raw_silver");
        public static final Tags.IOptionalNamedTag<Item> STORAGE_BLOCKS_SILVER = createCommonTag("storage_blocks/silver");

        public static final Tags.IOptionalNamedTag<Item> RAW_MATERIALS_SILVER = createCommonTag("raw_materials/silver");
        public static final Tags.IOptionalNamedTag<Item> INGOTS_SILVER = createCommonTag("ingots/silver");
        public static final Tags.IOptionalNamedTag<Item> NUGGETS_SILVER = createCommonTag("nuggets/silver");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(PracticeMod.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createCommonTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}