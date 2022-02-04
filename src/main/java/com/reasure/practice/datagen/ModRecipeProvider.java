package com.reasure.practice.datagen;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.setup.ModItems;
import com.reasure.practice.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.RAW_MATERIALS_SILVER), ModItems.SILVER_INGOT.get(), 0.9f, 200)
                .group("silver_ingot")
                .unlockedBy("has_raw_silver", has(ModTags.Items.RAW_MATERIALS_SILVER))
                .save(consumer, modLoc("silver_ingot_from_smelting_raw"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.RAW_MATERIALS_SILVER), ModItems.SILVER_INGOT.get(), 0.9f, 100)
                .group("silver_ingot")
                .unlockedBy("has_raw_silver", has(ModTags.Items.RAW_MATERIALS_SILVER))
                .save(consumer, modLoc("silver_ingot_from_blasting_raw"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.ORES_SILVER), ModItems.SILVER_INGOT.get(), 0.9f, 200)
                .group("silver_ingot")
                .unlockedBy("has_silver_ore", has(ModTags.Items.ORES_SILVER))
                .save(consumer, modLoc("silver_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.ORES_SILVER), ModItems.SILVER_INGOT.get(), 0.9f, 100)
                .group("silver_ingot")
                .unlockedBy("has_silver_ore", has(ModTags.Items.ORES_SILVER))
                .save(consumer, modLoc("silver_ingot_from_blasting_ore"));

        ShapedRecipeBuilder.shaped(ModBlocks.RAW_SILVER_BLOCK.get())
                .define('#', ModTags.Items.RAW_MATERIALS_SILVER)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_raw_silver", has(ModTags.Items.RAW_MATERIALS_SILVER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.SILVER_BLOCK.get())
                .define('#', ModTags.Items.INGOTS_SILVER)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_silver_ingot", has(ModTags.Items.INGOTS_SILVER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_INGOT.get())
                .group("silver_ingot")
                .define('#', ModTags.Items.NUGGETS_SILVER)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_silver_nugget", has(ModTags.Items.NUGGETS_SILVER))
                .save(consumer, modLoc("silver_ingot_from_nugget"));

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_SILVER.get(), 9)
                .requires(ModTags.Items.STORAGE_BLOCKS_RAW_SILVER)
                .unlockedBy("has_raw_silver_block", has(ModTags.Items.STORAGE_BLOCKS_RAW_SILVER))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.SILVER_INGOT.get(), 9)
                .group("silver_ingot")
                .requires(ModTags.Items.STORAGE_BLOCKS_SILVER)
                .unlockedBy("has_silver_block", has(ModTags.Items.STORAGE_BLOCKS_SILVER))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.SILVER_NUGGET.get(), 9)
                .group("silver_nugget")
                .requires(ModTags.Items.INGOTS_SILVER)
                .unlockedBy("has_silver_ingot", has(ModTags.Items.INGOTS_SILVER))
                .save(consumer);
    }

    private ResourceLocation modLoc(String path) {
        return new ResourceLocation(PracticeMod.MOD_ID, path);
    }
}
