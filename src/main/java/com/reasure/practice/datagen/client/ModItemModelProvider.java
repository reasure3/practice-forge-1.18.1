package com.reasure.practice.datagen.client;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, PracticeMod.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEMS.getEntries().stream()
                .filter(item -> item.get() instanceof BlockItem)
                .forEach(item -> {
                    String name = item.getId().getPath();
                    withExistingParent(name, modLoc("block/" + name));
                });

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        build(ModItems.RAW_SILVER.getId().getPath(), itemGenerated);
        build(ModItems.SILVER_INGOT.getId().getPath(), itemGenerated);
        build(ModItems.SILVER_NUGGET.getId().getPath(), itemGenerated);
    }

    private ItemModelBuilder build(String path, ModelFile parent) {
        return getBuilder(path).parent(parent).texture("layer0", "item/" + path);
    }
}
