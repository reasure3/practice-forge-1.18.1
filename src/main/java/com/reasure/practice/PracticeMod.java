package com.reasure.practice;

import com.reasure.practice.setup.ClientSetup;
import com.reasure.practice.setup.ModSetup;
import com.reasure.practice.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
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
    public static final Logger LOGGER = LogManager.getLogger();

    public PracticeMod() {
        // Register the deferred registry
        Registration.init();

        // Register the setup method for mod loading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
    }
}
