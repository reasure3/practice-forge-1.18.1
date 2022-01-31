package com.reasure.practice;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PracticeMod.MOD_ID)
public class PracticeMod {
    public static final String MOD_ID = "practice";
    public static final String MOD_NAME = "Practice Mod";
    public static final Logger LOGGER = LogManager.getLogger();

    public PracticeMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
