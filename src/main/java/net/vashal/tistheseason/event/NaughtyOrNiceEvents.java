package net.vashal.tistheseason.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.entity.StockingBlockEntity;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.custom.KrampusEntity;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.utils.StockingGiftItems;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class NaughtyOrNiceEvents {


    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {


        @SubscribeEvent
        public static void onLivingFriendlyDeath(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof Villager) {
                    if (event.getEntity().isBaby()) {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(100);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    } else {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(25);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    }
                } else if (event.getEntity() instanceof Animal) {
                    if (event.getEntity().isBaby()) {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(25);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    } else {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(2);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onLivingHostileDeath(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof Pillager) {
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        niceScore.addScore(10);
                        player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                    });
                } else if (event.getEntity() instanceof Monster) {
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        niceScore.addScore(5);
                        player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                    });
                }
            }
        }


        @SubscribeEvent
        public static void onSleepEvent(PlayerWakeUpEvent event) {
            Player player = event.getEntity();
            Level level = player.getLevel();
            StockingGiftItems gifts = new StockingGiftItems();
            gifts.addGoodGifts();
            player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                if (niceScore.getStocking() != null) {
                    StockingBlockEntity stocking = (StockingBlockEntity) level.getBlockEntity(niceScore.getStocking());
                    if (stocking != null && stocking.isOwnedBy(player)) {
                        if (niceScore.getCurrentScore() < -250) {
                            KrampusEntity krampus = TTS_EntityTypes.KRAMPUS.get().spawn((ServerLevel) level, null, null, null, stocking.getBlockPos(), MobSpawnType.COMMAND, true, false);
                            if (krampus != null) {
                                krampus.tryToSpawnToysFor((ServerLevel) level, krampus);
                            }
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        } else if (niceScore.getCurrentScore() < 0 && niceScore.getCurrentScore() >= -250) {
                            for (int i = 0; i < stocking.getContainerSize() - 1; i++) {
                                if (stocking.getItem(i).isEmpty()) {
                                    ItemStack stack = ItemStack.EMPTY;
                                    if (new Random().nextInt(2) + 1 == 1) {
                                        int result = new Random().nextInt(20 - 1) + 1;
                                        if (result > gifts.getBadGifts().size() - 1) {
                                            result = gifts.getBadGifts().size() - 1;
                                        }
                                        stack = gifts.getBadGifts().get(result).getDefaultInstance();
                                        stack.setCount(new Random().nextInt(8 - 1) + 1);
                                    }
                                    stocking.setItem(i, stack);
                                }
                            }
                        } else if (niceScore.getCurrentScore() > 0 && niceScore.getCurrentScore() <= 250) {
                            for (int i = 0; i < stocking.getContainerSize(); i++) {
                                if (stocking.getItem(i).isEmpty()) {
                                    ItemStack stack = ItemStack.EMPTY;
                                    if (new Random().nextInt(2) + 1 == 1) {
                                        int result = new Random().nextInt(40 - 1) + 1;
                                        if (result > gifts.getMediumGifts().size() - 1) {
                                            result = gifts.getMediumGifts().size() - 1;
                                        }
                                        stack = gifts.getMediumGifts().get(result).getDefaultInstance();
                                        if (stack.getItem() == TTS_Items.CANDY_CANE.get()) {
                                            stack.setCount(new Random().nextInt(12 - 1) + 1);
                                        }
                                    }
                                    stocking.setItem(i, stack);
                                }
                            }

                        } else if (niceScore.getCurrentScore() > 250) {
                            for (int i = 0; i < stocking.getContainerSize() - 1; i++) {
                                if (stocking.getItem(i).isEmpty()) {
                                    ItemStack stack = ItemStack.EMPTY;
                                    if (new Random().nextInt(2) + 1 == 1) {
                                        int result = new Random().nextInt(120 - 1) + 1;
                                        if (result > gifts.getGoodGifts().size() - 1) {
                                            result = gifts.getGoodGifts().size() - 1;
                                        }
                                        stack = gifts.getGoodGifts().get(result).getDefaultInstance();
                                        if (stack.getItem() == TTS_Items.CANDY_CANE.get()) {
                                            stack.setCount(new Random().nextInt(12 - 1) + 1);
                                        }
                                    }
                                    stocking.setItem(i, stack);
                                }
                            }
                            if (stocking.getItem(13).isEmpty()) {
                                stocking.setItem(13, gifts.getGoodGifts().get(new Random().nextInt((gifts.getGoodGifts().size() - 2) -1)+1).getDefaultInstance());
                            }
                        }
                    }
                }
            });
            player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                if (niceScore.hasStocking()) {
                    niceScore.setMaxScore(500);
                    niceScore.setMinScore(-500);
                    niceScore.setScore(0);
                }
            });
        }
    }
}
