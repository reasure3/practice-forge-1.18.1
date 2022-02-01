package com.reasure.practice.datagen.client;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_SILVER_ORE.get());
        simpleBlock(ModBlocks.SILVER_BLOCK.get());
    }
}
