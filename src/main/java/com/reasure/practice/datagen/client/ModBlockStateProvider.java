package com.reasure.practice.datagen.client;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.reasure.practice.block.client.model.OreGeneratorModelLoader.ORE_GENERATOR_LOADER;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_SILVER_ORE.get());
        simpleBlock(ModBlocks.RAW_SILVER_BLOCK.get());
        simpleBlock(ModBlocks.SILVER_BLOCK.get());

        registerPowerGenBlock();
    }

    private void registerPowerGenBlock() {
        BlockModelBuilder frame = models().getBuilder("block/power_gen_main");
        frame.parent(models().getExistingFile(mcLoc("cube")));

        // pillar (for y)
        floatingCube(frame, 0f, 0f, 0f, 1f, 16f, 1f);
        floatingCube(frame, 15f, 0f, 0f, 16f, 16f, 1f);
        floatingCube(frame, 0f, 0f, 15f, 1f, 16f, 16f);
        floatingCube(frame, 15f, 0f, 15f, 16f, 16f, 16f);

        // pillar (for x)
        floatingCube(frame, 1f, 0f, 0f, 15f, 1f, 1f);
        floatingCube(frame, 1f, 15f, 0f, 15f, 16f, 1f);
        floatingCube(frame, 1f, 0f, 15f, 15f, 1f, 16f);
        floatingCube(frame, 1f, 15f, 15f, 15f, 16f, 16f);

        // pillar (for z)
        floatingCube(frame, 0f, 0f, 1f, 1f, 1f, 15f);
        floatingCube(frame, 15f, 0f, 1f, 16f, 1f, 15f);
        floatingCube(frame, 0f, 15f, 1f, 1f, 16f, 15f);
        floatingCube(frame, 15f, 15f, 1f, 16f, 16f, 15f);

        // center glass
        floatingCube(frame, 1f, 1f, 1f, 15f, 15f, 15f);

        frame.texture("window", modLoc("block/power_gen_window"));
        frame.texture("particle", modLoc("block/power_gen_off"));

        createPowerGenModel(ModBlocks.POWER_GEN.get(), frame);
        registerOreGenerator();
    }

    private void floatingCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz) {
        builder.element()
                .from(fx, fy, fz).to(tx, ty, tz)
                .allFaces(((direction, faceBuilder) -> faceBuilder.texture("#window")))
                .end();
    }

    private void createPowerGenModel(Block block, BlockModelBuilder frame) {
        BlockModelBuilder singleOff = models().getBuilder("block/power_gen_off")
                .element().from(3, 3, 3).to(13, 13, 13).allFaces((dir, builder) -> builder.texture("#single")).end()
                .texture("single", modLoc("block/power_gen_off"));
        BlockModelBuilder singleOn = models().getBuilder("block/power_gen_on")
                .element().from(3, 3, 3).to(13, 13, 13).allFaces((dir, builder) -> builder.texture("#single")).end()
                .texture("single", modLoc("block/power_gen_on"));

        getMultipartBuilder(block).part().modelFile(frame).addModel().end()
                .part().modelFile(singleOff).addModel().condition(BlockStateProperties.POWERED, false).end()
                .part().modelFile(singleOn).addModel().condition(BlockStateProperties.POWERED, true).end();
    }

    private void registerOreGenerator() {
        // Using CustomLoaderBuilder we can define a json file for our model that will use our baked model
        @SuppressWarnings("ConstantConditions")
        BlockModelBuilder generatorModel = models().getBuilder(ModBlocks.ORE_GENERATOR.get().getRegistryName().getPath())
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((blockModelBuilder, helper) -> new CustomLoaderBuilder<BlockModelBuilder>(ORE_GENERATOR_LOADER, blockModelBuilder, helper) {})
                .end();
        directionalBlock(ModBlocks.ORE_GENERATOR.get(), generatorModel);
    }
}
