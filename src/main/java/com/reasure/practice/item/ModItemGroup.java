package com.reasure.practice.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModItemGroup {
    public static final String PRACTICE_TAB_NAME = "practice";

    public static final CreativeModeTab PRACTICE_ITEM_GROUP = new CreativeModeTab(PRACTICE_TAB_NAME) {
        @Override @NotNull
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SILVER_INGOT.get());
        }
    };
}
