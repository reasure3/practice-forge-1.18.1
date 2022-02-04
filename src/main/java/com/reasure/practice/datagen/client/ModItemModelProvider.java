package com.reasure.practice.datagen.client;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        block(ModBlocks.SILVER_ORE.getId().getPath());
        block(ModBlocks.DEEPSLATE_SILVER_ORE.getId().getPath());
        block(ModBlocks.RAW_SILVER_BLOCK.getId().getPath());
        block(ModBlocks.SILVER_BLOCK.getId().getPath());

        block(ModBlocks.POWER_GEN_BLOCK.getId().getPath(), "power_gen_main");

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        build(ModItems.RAW_SILVER.getId().getPath(), itemGenerated);
        build(ModItems.SILVER_INGOT.getId().getPath(), itemGenerated);
        build(ModItems.SILVER_NUGGET.getId().getPath(), itemGenerated);
    }

    private void block(String itemPath) {
        block(itemPath, itemPath);
    }

    private void block(String itemPath, String BlockPath) {
        withExistingParent(itemPath, modLoc("block/" + BlockPath));
    }

    private void build(String path, ModelFile parent) {
        getBuilder(path).parent(parent).texture("layer0", "item/" + path);
    }
}
