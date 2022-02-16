package com.reasure.practice.setup;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.entity.ThiefEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, PracticeMod.MOD_ID);

    public static void register(IEventBus modbus) {
        ENTITIES.register(modbus);
    }

    public static final RegistryObject<EntityType<ThiefEntity>> THIEF = ENTITIES.register("thief", () ->
            EntityType.Builder.of(ThiefEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(false)
                    .build("thief"));
}
