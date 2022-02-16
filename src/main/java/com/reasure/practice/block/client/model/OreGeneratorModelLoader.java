package com.reasure.practice.block.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.reasure.practice.PracticeMod;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class OreGeneratorModelLoader implements IModelLoader<OreGeneratorModelLoader.OreGeneratorModelGeometry> {

    public static final ResourceLocation ORE_GENERATOR_LOADER = new ResourceLocation(PracticeMod.MOD_ID, "ore_generator_loader");

    public static final ResourceLocation ORE_GENERATOR_FRONT_POWERED = new ResourceLocation(PracticeMod.MOD_ID, "block/ore_generator_front_powered");
    public static final ResourceLocation ORE_GENERATOR_FRONT = new ResourceLocation(PracticeMod.MOD_ID, "block/ore_generator_front");
    public static final ResourceLocation ORE_GENERATOR_SIDE = new ResourceLocation(PracticeMod.MOD_ID, "block/ore_generator_side");
    public static final ResourceLocation ORE_GENERATOR_ON = new ResourceLocation(PracticeMod.MOD_ID, "block/ore_generator_on");
    public static final ResourceLocation ORE_GENERATOR_OFF = new ResourceLocation(PracticeMod.MOD_ID, "block/ore_generator_off");

    public static final Material MATERIAL_FRONT_POWERED = ForgeHooksClient.getBlockMaterial(ORE_GENERATOR_FRONT_POWERED);
    public static final Material MATERIAL_FRONT = ForgeHooksClient.getBlockMaterial(ORE_GENERATOR_FRONT);
    public static final Material MATERIAL_SIDE = ForgeHooksClient.getBlockMaterial(ORE_GENERATOR_SIDE);
    public static final Material MATERIAL_ON = ForgeHooksClient.getBlockMaterial(ORE_GENERATOR_ON);
    public static final Material MATERIAL_OFF = ForgeHooksClient.getBlockMaterial(ORE_GENERATOR_OFF);

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
    }

    @Override
    public @NotNull OreGeneratorModelGeometry read(@NotNull JsonDeserializationContext deserializationContext, @NotNull JsonObject modelContents) {
        return new OreGeneratorModelGeometry();
    }


    public static class OreGeneratorModelGeometry implements IModelGeometry<OreGeneratorModelGeometry> {
        @Override
        public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter,
                               ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
            return new OreGeneratorBakedModel(modelTransform, spriteGetter, overrides, owner.getCameraTransforms());
        }

        @Override
        public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter,
                                                Set<Pair<String, String>> missingTextureErrors) {
            return List.of(MATERIAL_FRONT, MATERIAL_FRONT_POWERED, MATERIAL_SIDE, MATERIAL_ON, MATERIAL_OFF);
        }
    }
}