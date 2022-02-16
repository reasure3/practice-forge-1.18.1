package com.reasure.practice.entity.client.renderer;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.entity.ThiefEntity;
import com.reasure.practice.entity.client.model.ThiefModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ThiefRenderer extends HumanoidMobRenderer<ThiefEntity, ThiefModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(PracticeMod.MOD_ID, "textures/entity/thief.png");

    public ThiefRenderer(EntityRendererProvider.Context context) {
        // 마지막 파라미터: shadowRadius
        super(context, new ThiefModel(context.bakeLayer(ThiefModel.THIEF_LAYER)), 1f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@NotNull ThiefEntity entity) {
        return TEXTURE;
    }
}
