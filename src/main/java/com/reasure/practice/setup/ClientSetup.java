package com.reasure.practice.setup;

import com.reasure.practice.client.screens.PowerGenScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.POWER_GEN_CONTAINER.get(), PowerGenScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.POWER_GEN_BLOCK.get(), RenderType.translucent());
        });
    }
}
