package com.reasure.practice.datagen.client;

import com.reasure.practice.block.PowerGenBlock;
import com.reasure.practice.item.ModItemGroup;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.setup.ModItems;
import net.minecraft.data.DataGenerator;

public class ModLanguageProvider extends BaseLanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String locale) {
        super(gen, locale);
    }

    protected void addTranslations() {
        switch (locale) {
            case "en_us" -> addEnglishTranslations();
            case "ko_kr" -> addKoreanTranslations();
        }
    }

    private void addEnglishTranslations() {
        add("itemGroup." + ModItemGroup.PRACTICE_TAB_NAME, "Practice");

        add(ModItems.RAW_SILVER.get(), "Raw Silver");
        add(ModItems.SILVER_INGOT.get(), "Silver Ingot");
        add(ModItems.SILVER_NUGGET.get(), "Silver Nugget");

        add(ModBlocks.SILVER_ORE.get(), "Silver Ore");
        add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), "Deepslate Silver Ore");
        add(ModBlocks.RAW_SILVER_BLOCK.get(), "Raw Silver Block");
        add(ModBlocks.SILVER_BLOCK.get(), "Silver Block");

        add(PowerGenBlock.MESSAGE_POWER_GEN, "Power generator generating %s per tick!");
        add(PowerGenBlock.SCREEN_PRACTICE_POWER_GEN, "Power Generator");
        add(ModBlocks.POWER_GEN_BLOCK.get(), "Power Generator");
    }

    private void addKoreanTranslations() {
        add("itemGroup." + ModItemGroup.PRACTICE_TAB_NAME, "연습용 모드");

        add(ModItems.RAW_SILVER.get(), "은 원석");
        add(ModItems.SILVER_INGOT.get(), "은 주괴");
        add(ModItems.SILVER_NUGGET.get(), "은 조각");

        add(ModBlocks.SILVER_ORE.get(), "은 광석");
        add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), "심층암 은 광석");
        add(ModBlocks.RAW_SILVER_BLOCK.get(), "은 원석 블록");
        add(ModBlocks.SILVER_BLOCK.get(), "은 블록");

        add(PowerGenBlock.MESSAGE_POWER_GEN, "틱 당 %s를 생산하는 발전기!");
        add(PowerGenBlock.SCREEN_PRACTICE_POWER_GEN, "발전기");
        add(ModBlocks.POWER_GEN_BLOCK.get(), "발전기");
    }
}
