package com.reasure.practice.setup;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.block.entity.OreGeneratorBlockEntity;
import com.reasure.practice.block.entity.PowerGenBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class ModBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PracticeMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PowerGenBlockEntity>> POWER_GEN_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("power_gen", () ->
            BlockEntityType.Builder.of(PowerGenBlockEntity::new, ModBlocks.POWER_GEN.get()).build(null));

    public static final RegistryObject<BlockEntityType<OreGeneratorBlockEntity>> ORE_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("ore_generator", () ->
            BlockEntityType.Builder.of(OreGeneratorBlockEntity::new, ModBlocks.ORE_GENERATOR.get()).build(null));

    public static void register(IEventBus modBus) {
        BLOCK_ENTITY_TYPES.register(modBus);
    }
}
