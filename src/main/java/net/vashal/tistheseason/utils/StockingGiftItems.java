package net.vashal.tistheseason.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.items.TTS_Items;

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
        this.goodGifts.add(TTS_Items.TOY_SOLDIER_ITEM.get());
        this.goodGifts.add(TTS_Items.TOY_TANK_ITEM.get());
        this.goodGifts.add(TTS_Items.TOY_ROBOT_ITEM.get());
        this.goodGifts.add(TTS_Items.HOBBY_HORSE.get());
        this.goodGifts.add(TTS_Items.POWER_GLOVE.get());
        this.goodGifts.add(TTS_Items.SUPER_SOAKER.get());
        this.goodGifts.add(TTS_Items.REINDEER_SLIPPERS.get());
        this.goodGifts.add(TTS_Items.CANDY_CANE.get());
        this.mediumGifts.add(TTS_Blocks.PET_ROCK.get().asItem());
        this.mediumGifts.add(TTS_Items.HOBBY_HORSE.get());
        this.mediumGifts.add(TTS_Items.POWER_GLOVE.get());
        this.mediumGifts.add(TTS_Items.CANDY_CANE.get());
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
