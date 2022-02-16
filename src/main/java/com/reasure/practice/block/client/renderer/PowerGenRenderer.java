package com.reasure.practice.block.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.entity.PowerGenBlockEntity;
import com.reasure.practice.setup.ModBlockEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class PowerGenRenderer implements BlockEntityRenderer<PowerGenBlockEntity> {
    public static final ResourceLocation HALO = new ResourceLocation(PracticeMod.MOD_ID, "effect/halo");

    public PowerGenRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(@NotNull PowerGenBlockEntity powerGen, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer,
                       int combinedLight, int combinedOverlay) {
        Boolean powered = powerGen.getBlockState().getValue(BlockStateProperties.POWERED);
        if (Boolean.TRUE != powered) {
            return;
        }

        int brightness = LightTexture.FULL_BRIGHT;
        // 매동하는 효과를 위해 현재 시각을 사용합니다.
        float s = (System.currentTimeMillis() % 1000) / 1000.0f;
        if (s > 0.5f) {
            s = 1.0f - s;
        }
        float scale = 0.1f + s * .3f;

        // 아틀라스에서 텍스처 가져오기
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(HALO);

        // 나중에 복원할 수 있도록 현재 변환을 push 하는 것을 항상 기억해야 함.
        matrixStack.pushPose();

        // 블럭의 중앙으로 이동 후 1단위 위로 이동
        matrixStack.translate(0.5, 1.5, 0.5);

        // 메인 카메라의 방향을 사용해서 렌더링할 단일 쿼드가 항상 카메라를 향하도록 함
        Quaternion rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrixStack.mulPose(rotation);

        // 실제로 사용자 정의 렌더 유형으로 쿼드를 렌더링합니다.
        VertexConsumer vertexBuffer = buffer.getBuffer(CustomRenderType.ADD);
        Matrix4f matrix = matrixStack.last().pose();
        // 정점 데이터는 특정 순서로 나타나야 합니다:
        vertexBuffer.vertex(matrix, -scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f)
                .uv(sprite.getU0(), sprite.getV0()).uv2(brightness).normal(1, 0, 0).endVertex();
        vertexBuffer.vertex(matrix, -scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f)
                .uv(sprite.getU0(), sprite.getV1()).uv2(brightness).normal(1, 0, 0).endVertex();
        vertexBuffer.vertex(matrix, scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f)
                .uv(sprite.getU1(), sprite.getV1()).uv2(brightness).normal(1, 0, 0).endVertex();
        vertexBuffer.vertex(matrix, scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f)
                .uv(sprite.getU1(), sprite.getV0()).uv2(brightness).normal(1, 0, 0).endVertex();
        matrixStack.popPose();
    }

    public static void register() {
        BlockEntityRenderers.register(ModBlockEntityTypes.POWER_GEN_BLOCK_ENTITY.get(), PowerGenRenderer::new);
    }
}
