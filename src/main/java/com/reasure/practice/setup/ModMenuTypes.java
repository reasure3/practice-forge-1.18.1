package com.reasure.practice.setup;

import com.reasure.practice.PracticeMod;
import com.reasure.practice.world.inventory.PowerGenMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, PracticeMod.MOD_ID);

    public static final RegistryObject<MenuType<PowerGenMenu>> POWER_GEN_CONTAINER = MENU_TYPES.register("power_gen", () ->
            IForgeMenuType.create(((windowId, inv, data) -> new PowerGenMenu(windowId, inv, data.readBlockPos()))));

    public static void register(IEventBus modBus) {
        MENU_TYPES.register(modBus);
    }
}
