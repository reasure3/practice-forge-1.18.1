package com.reasure.practice.setup;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.client.model.OreGeneratorModelLoader;
import com.reasure.practice.block.client.renderer.PowerGenRenderer;
import com.reasure.practice.block.client.screens.PowerGenScreen;
import com.reasure.practice.entity.client.model.ThiefModel;
import com.reasure.practice.entity.client.renderer.ThiefRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PracticeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.POWER_GEN_CONTAINER.get(), PowerGenScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.POWER_GEN.get(), RenderType.translucent());
            PowerGenRenderer.register();
        });
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(OreGeneratorModelLoader.ORE_GENERATOR_LOADER, new OreGeneratorModelLoader());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            return;
        }
        event.addSprite(PowerGenRenderer.HALO);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ThiefModel.THIEF_LAYER, ThiefModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.THIEF.get(), ThiefRenderer::new);
    }
}
