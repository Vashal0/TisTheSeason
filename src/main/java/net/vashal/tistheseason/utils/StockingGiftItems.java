package net.vashal.tistheseason.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.vashal.tistheseason.block.TTSBlocks;
import net.vashal.tistheseason.items.TTSItems;

import java.util.ArrayList;

public class StockingGiftItems {
    ArrayList<Item> badGifts;
    ArrayList<Item> mediumGifts;
    ArrayList<Item> goodGifts;

    public StockingGiftItems() {
        this.badGifts = new ArrayList<>();
        this.mediumGifts = new ArrayList<>();
        this.goodGifts = new ArrayList<>();
    }

    public void addGifts() {
        this.goodGifts.add(TTSItems.TOY_SOLDIER_ITEM.get());
        this.goodGifts.add(TTSItems.TOY_TANK_ITEM.get());
        this.goodGifts.add(TTSItems.TOY_ROBOT_ITEM.get());
        this.goodGifts.add(TTSItems.HOBBY_HORSE.get());
        this.goodGifts.add(TTSItems.POWER_GLOVE.get());
        this.goodGifts.add(TTSItems.SUPER_SOAKER.get());
        this.goodGifts.add(TTSItems.REINDEER_SLIPPERS.get());
        this.goodGifts.add(TTSItems.SNOWMAN_SLIPPERS.get());
        this.goodGifts.add(TTSItems.POP_GUN.get());
        this.goodGifts.add(TTSItems.CARAMEL.get());
        this.goodGifts.add(TTSItems.LOLLIPOP.get());
        this.goodGifts.add(TTSItems.ENCHANTED_CANDY_CANE.get());
        this.goodGifts.add(TTSItems.CANDY_CANE.get());
        this.mediumGifts.add(TTSBlocks.PET_ROCK.get().asItem());
        this.mediumGifts.add(TTSItems.HOBBY_HORSE.get());
        this.mediumGifts.add(TTSItems.POWER_GLOVE.get());
        this.mediumGifts.add(TTSItems.SUPER_SOAKER.get());
        this.mediumGifts.add(TTSItems.CARAMEL.get());
        this.mediumGifts.add(TTSItems.LOLLIPOP.get());
        this.mediumGifts.add(TTSItems.CANDY_CANE.get());
        this.badGifts.add(Items.CHARCOAL);
        this.badGifts.add(Items.COAL_ORE);
        this.badGifts.add(Items.DEEPSLATE_COAL_ORE);
        this.badGifts.add(Items.COAL_BLOCK);
        this.badGifts.add(Items.COAL);
    }

    public ArrayList<Item> getGoodGifts() {
        return goodGifts;
    }

    public ArrayList<Item> getBadGifts() {
        return badGifts;
    }

    public ArrayList<Item> getMediumGifts() {
        return mediumGifts;
    }
}
