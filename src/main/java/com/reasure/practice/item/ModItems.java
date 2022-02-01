package com.reasure.practice.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.reasure.practice.PracticeMod.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    // Items
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () ->
            new Item(new Item.Properties().tab(ModItemGroup.PRACTICE_ITEM_GROUP)));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().tab(ModItemGroup.PRACTICE_ITEM_GROUP)));

    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () ->
            new Item(new Item.Properties().tab(ModItemGroup.PRACTICE_ITEM_GROUP)));

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
