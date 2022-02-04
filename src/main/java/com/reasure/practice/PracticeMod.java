package com.reasure.practice;

import com.reasure.practice.setup.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PracticeMod.MOD_ID)
public class PracticeMod {
    public static final String MOD_ID = "practice";
    public static final String MOD_NAME = "Practice Mod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public PracticeMod() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the deferred registry
        ModItems.register(modbus);
        ModBlocks.register(modbus);
        ModBlockEntityTypes.register(modbus);
        ModMenuTypes.register(modbus);

        // Register the setup method for mod loading
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
