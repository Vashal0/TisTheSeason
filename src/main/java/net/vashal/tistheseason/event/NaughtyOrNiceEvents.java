package net.vashal.tistheseason.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTSBlocks;
import net.vashal.tistheseason.block.entity.StockingBlockEntity;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.custom.KrampusEntity;
import net.vashal.tistheseason.items.TTSItems;
import net.vashal.tistheseason.utils.StockingGiftItems;
import org.jetbrains.annotations.NotNull;

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
                            if (!niceScore.isReadyForGift()) {
                                niceScore.removeScore(100);
                            }
                        });
                    } else {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            if (!niceScore.isReadyForGift()) {
                                niceScore.removeScore(10);
                            }
                        });
                    }
                } else if (event.getEntity() instanceof Animal) {
                    if (event.getEntity().isBaby()) {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            if (!niceScore.isReadyForGift()) {
                                niceScore.removeScore(25);
                            }
                        });
                    } else {
                        player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            if (!niceScore.isReadyForGift()) {
                                niceScore.removeScore(2);
                            }
                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onGainEffect(MobEffectEvent.Added event) {
            @NotNull MobEffect effect = event.getEffectInstance().getEffect();
            if (event.getEntity() instanceof Player player) {
                if (effect == MobEffects.HERO_OF_THE_VILLAGE) {
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        niceScore.setScore(niceScore.getCurrentScore()+250);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                niceScore.setTime(niceScore.getTime()+1);
            });
        }

        @SubscribeEvent
        public static void onLivingHostileDeath(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof Pillager) {
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        if (!niceScore.isReadyForGift()) {
                            niceScore.addScore(20);
                        }
                    });
                } else if (event.getEntity() instanceof Monster) {
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        if (!niceScore.isReadyForGift()) {
                            niceScore.addScore(3);
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onDropItem(ItemTossEvent event) {
            Player player = event.getPlayer();
            Level level = player.getLevel();
            ItemEntity item = event.getEntity();
            if (item.getItem().getItem() instanceof WrittenBookItem book) {
                if (book.getName(item.getItem()).toString().toLowerCase().contains("santa")) {
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, item.getRandomX(10), item.getY() - 0.35d, item.getRandomZ(10), 25, 0.5, 0, 0.5, 0.5);
                        item.remove(Entity.RemovalReason.KILLED);
                    }
                    player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        if (niceScore.getTime() > 200) { //TODO CHANGE BACK TO 18000
                            if (!niceScore.isReadyForGift()) {
                                if (niceScore.getCurrentScore() > 250) {
                                    player.displayClientMessage(Component.translatable("Santa: Ho Ho Ho! You've been very good this year!"), true);
                                } else if (niceScore.getCurrentScore() > 50 && niceScore.getCurrentScore() <= 250) {
                                    player.displayClientMessage(Component.translatable("Santa: Ho Ho Ho! You've done alright this year"), true);
                                } else if (niceScore.getCurrentScore() < -250) {
                                    player.displayClientMessage(Component.translatable("Santa: You've been very naughty, we have something special for heartless people like you"), true);
                                } else if (niceScore.getCurrentScore() < -50 && niceScore.getCurrentScore() >= -250) {
                                    player.displayClientMessage(Component.translatable("Santa: I'm very disappointed in you this year, you've been naughty"), true);
                                } else if (niceScore.getCurrentScore() >= -50 && niceScore.getCurrentScore() <= 50) {
                                    player.displayClientMessage(Component.translatable("Santa: Sorry, Write another letter when you've done more"), true);
                                    player.spawnAtLocation(item.copy().getItem(), 1);
                                }
                                niceScore.setGiftStatus(niceScore.getCurrentScore() < -50 || niceScore.getCurrentScore() > 50);
                            } else {
                                player.displayClientMessage(Component.translatable("Santa: You've already sent me a letter this year"), true);
                                player.spawnAtLocation(item.copy().getItem(), 1);
                            }
                        } else {
                            player.displayClientMessage(Component.translatable("Santa: Too soon, the elves are still hard at work"), true);
                            ItemEntity itementity = player.spawnAtLocation(Items.WRITABLE_BOOK, 1);
                            assert itementity != null;
                            itementity.getItem().setHoverName(Component.translatable("Time Remaining: " + (900 - niceScore.getTime() / 20) + " seconds"));
                        }
                    });
                }
            }
        }




        @SubscribeEvent
        public static void onSleepEvent(PlayerWakeUpEvent event) {
            Player player = event.getEntity();
            Level level = player.getLevel();
            StockingGiftItems gifts = new StockingGiftItems();
            gifts.addGifts();
            if (!event.updateLevel()) {
                player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                    if (niceScore.getStocking() != null) {
                        if (level.getBlockEntity(niceScore.getStocking()) instanceof StockingBlockEntity stocking && niceScore.isReadyForGift()) {
                            if (stocking.isOwnedBy(player)) {
                                if (niceScore.getCurrentScore() < -250) {
                                    KrampusEntity krampus = TTSEntityTypes.KRAMPUS.get().spawn((ServerLevel) level, null, null, null, stocking.getBlockPos(), MobSpawnType.COMMAND, true, false);
                                    if (krampus != null) {
                                        krampus.tryToSpawnToysFor((ServerLevel) level, krampus);
                                    }
                                } else if (niceScore.getCurrentScore() < -50 && niceScore.getCurrentScore() >= -250) {
                                    for (int i = 0; i < stocking.getContainerSize() - 1; i++) {
                                        if (stocking.getItem(i).isEmpty()) {
                                            ItemStack stack = ItemStack.EMPTY;
                                            if (new Random().nextInt((2)+1) != 0) {
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
                                } else if (niceScore.getCurrentScore() > 50 && niceScore.getCurrentScore() <= 250) {
                                    for (int i = 0; i < stocking.getContainerSize() - 1; i++) {
                                        if (stocking.getItem(i).isEmpty()) {
                                            ItemStack stack = ItemStack.EMPTY;
                                            int randomCandy = new Random().nextInt((2)+1);
                                            if (new Random().nextInt((2)+1) != 0) {
                                                int result = new Random().nextInt(40 - 1) + 1;
                                                if (result > gifts.getMediumGifts().size() - 1) {
                                                    result = gifts.getMediumGifts().size() - 1 - randomCandy;
                                                }
                                                stack = gifts.getMediumGifts().get(result).getDefaultInstance();
                                                if (stack.getItem() == TTSItems.CANDY_CANE.get() || stack.getItem() ==  TTSItems.CARAMEL.get()) {
                                                    stack.setCount(new Random().nextInt(12 - 1) + 1);
                                                }
                                            }
                                            stocking.setItem(i, stack);
                                        }
                                    }
                                    if (stocking.getItem(13).isEmpty()) {
                                        stocking.setItem(13, TTSBlocks.PET_ROCK.get().asItem().getDefaultInstance());
                                    }

                                } else if (niceScore.getCurrentScore() > 250) {
                                    for (int i = 0; i < stocking.getContainerSize() - 1; i++) {
                                        if (stocking.getItem(i).isEmpty()) {
                                            ItemStack stack = ItemStack.EMPTY;
                                            int randomCandy = new Random().nextInt((3)+1);
                                            if (new Random().nextInt((2)+1) != 0) {
                                                int result = new Random().nextInt(50 - 1) + 1;
                                                if (result > gifts.getGoodGifts().size() - 1) {
                                                    result = gifts.getGoodGifts().size() - 1 - randomCandy;
                                                }
                                                stack = gifts.getGoodGifts().get(result).getDefaultInstance();
                                                if (stack.getItem() == TTSItems.CANDY_CANE.get() || stack.getItem() ==  TTSItems.CARAMEL.get()) {
                                                    stack.setCount(new Random().nextInt(32 - 1) + 1);
                                                }
                                            }
                                            stocking.setItem(i, stack);
                                        }
                                    }
                                    if (stocking.getItem(13).isEmpty()) {
                                        stocking.setItem(13, gifts.getGoodGifts().get(new Random().nextInt((gifts.getGoodGifts().size() - 4) - 1) + 1).getDefaultInstance());
                                    }
                                }
                            }
                        }
                    }
                });

                player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                    if (niceScore.getStocking() != null) {
                        if (level.getBlockEntity(niceScore.getStocking()) instanceof StockingBlockEntity stockingBlock && niceScore.isReadyForGift()) {
                            if (stockingBlock.isOwnedBy(player)) {
                                niceScore.setMaxScore(500);
                                niceScore.setMinScore(-500);
                                niceScore.setScore(0);
                                niceScore.setGiftStatus(false);
                                niceScore.setTime(0);
                            } else {
                                player.displayClientMessage(Component.translatable("Santa: I couldn't find your stocking, make sure to place one and I'll try again."), true);
                            }
                        } else if (!(level.getBlockEntity(niceScore.getStocking()) instanceof StockingBlockEntity)) {
                            player.displayClientMessage(Component.translatable("Santa: I couldn't find your stocking, make sure to place one and I'll try again."), true);
                        }
                    } //else {
                        //player.displayClientMessage(Component.translatable("Santa: I couldn't find your stocking3, make sure to place one and I'll try again."), true); i don't know why this even triggers, but it seems to work either way
                });
            }
        }
    }
}
