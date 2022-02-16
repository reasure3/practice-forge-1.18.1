package com.reasure.practice.block.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.inventory.PowerGenMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class PowerGenScreen extends AbstractContainerScreen<PowerGenMenu> {
    private final ResourceLocation GUI = new ResourceLocation(PracticeMod.MOD_ID, "textures/gui/power_gen_gui.png");

    public PowerGenScreen(PowerGenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 180;
        this.imageHeight = 152;
        this.titleLabelX = 10;
        this.titleLabelY = 4;
        this.inventoryLabelX = 10;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    // 요놈이 계속 실행됨.
    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    // render 함수 중에 실행됨.
    @Override
    protected void renderLabels(@NotNull PoseStack matrixStack, int mouseX, int mouseY) {
        drawString(matrixStack, Minecraft.getInstance().font, "Energy: " + menu.getEnergy(), 10, 13, 0xffffff);
        super.renderLabels(matrixStack, mouseX, mouseY);
    }

    // 스크린이 처음 불러와지면 실행됨.
    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
