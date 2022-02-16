package com.reasure.practice.setup;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.entity.ThiefEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = PracticeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    public static void init(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.THIEF.get(), ThiefEntity.prepareAttributes().build());
    }
}
