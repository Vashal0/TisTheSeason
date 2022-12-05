package net.vashal.tistheseason.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.capabilities.INaughtyOrNice;
import net.vashal.tistheseason.capabilities.IRedstoneTouch;
import net.vashal.tistheseason.capabilities.NaughtyOrNiceAttacher;
import net.vashal.tistheseason.capabilities.RedstoneTouchAttacher;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.custom.*;
import net.vashal.tistheseason.items.TTS_Items;

import java.util.Objects;


public class ModEvents {


    public static final Capability<INaughtyOrNice> NAUGHTY_OR_NICE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Capability<IRedstoneTouch> REDSTONE_TOUCH = CapabilityManager.get(new CapabilityToken<>() {
    });


    public static LazyOptional<INaughtyOrNice> getScore(final LivingEntity entity) {
        if (entity == null)
            return LazyOptional.empty();
        return entity.getCapability(NAUGHTY_OR_NICE);
    }

    public static LazyOptional<IRedstoneTouch> getRedstoneTouch(final LivingEntity entity) {
        if (entity == null)
            return LazyOptional.empty();
        return entity.getCapability(REDSTONE_TOUCH);
    }

    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {


        @SubscribeEvent
        public static void onLivingFriendlyDeath(LivingDeathEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof Villager) {
                    if (event.getEntity().isBaby()) {
                        player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(25);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    } else {
                        player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(10);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    }
                } else if (event.getEntity() instanceof Animal) {
                    if (event.getEntity().isBaby()) {
                        player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(5);
                            player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                        });
                    } else {
                        player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            niceScore.removeScore(1);
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
                    player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        niceScore.addScore(2);
                        player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                    });
                } else if (event.getEntity() instanceof Monster) {
                    player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                        niceScore.addScore(1);
                        player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
            Player player = event.getEntity();
            BlockPos pos = event.getPos().relative(Objects.requireNonNull(event.getFace()));
            BlockState state = TTS_Blocks.INVISIBLE_REDSTONE.get().defaultBlockState();

            if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()) {
                Level world = event.getLevel();
                if (!(world.getBlockState(pos).getMaterial() == Material.AIR && world.getBlockState(pos).getBlock() != TTS_Blocks.INVISIBLE_REDSTONE.get())) {
                    return;
                }
                player.getCapability(REDSTONE_TOUCH).ifPresent(redstoneTouch -> {
                    if (redstoneTouch.getCurrentState() == 1) {
                        world.setBlockAndUpdate(pos, state);
                        world.scheduleTick(pos, state.getBlock(), 20);
                        if (!world.isOutsideBuildHeight(pos)) {
                            world.sendBlockUpdated(pos, state, state, 3);
                            world.updateNeighborsAt(pos, state.getBlock());
                        }
                    }
                });
            }
        }


        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                NaughtyOrNiceAttacher.attach(event);
                RedstoneTouchAttacher.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(INaughtyOrNice.class);
            event.register(IRedstoneTouch.class);
        }

        @SubscribeEvent
        public static void onSleepEvent(PlayerWakeUpEvent event) {
            Player player = event.getEntity();
            Level level = player.getLevel();
            player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                if (niceScore.getCurrentScore() <= -400) {
                    for (int i = 0; i < 10; i++) {
                        TTS_EntityTypes.EVIL_ROBOT.get().spawn((ServerLevel) level, null, null, player.blockPosition(),
                                MobSpawnType.COMMAND, true, false);
                    }
                } else if (niceScore.getCurrentScore() <= -100 && niceScore.getCurrentScore() > -400) {
                    TTS_EntityTypes.TOY_TANK.get().spawn((ServerLevel) level, null, null, player.blockPosition(),
                            MobSpawnType.COMMAND, true, false);
                    player.sendSystemMessage(Component.literal("score: " + niceScore.getCurrentScore()));
                } else if (niceScore.getCurrentScore() < 0 && niceScore.getCurrentScore() > -100) {
                    player.sendSystemMessage(Component.literal("do better"));
                } else if (niceScore.getCurrentScore() > 0 && niceScore.getCurrentScore() <= 100) {
                    player.addItem(TTS_Items.TOYROBOT.get().getDefaultInstance());
                } else if (niceScore.getCurrentScore() > 100) {
                    player.sendSystemMessage(Component.literal("ran out of tests"));
                }
            });
            player.getCapability(NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                niceScore.setMinScore(-500);
                niceScore.setMaxScore(500);
                niceScore.setScore(0);
            });
        }


        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            Player oldPlayer = event.getOriginal();
            oldPlayer.revive();
            getScore(oldPlayer).ifPresent(oldMaxNiceScore -> getScore(event.getEntity()).ifPresent(newMaxNiceScore -> {
                newMaxNiceScore.setMaxScore(oldMaxNiceScore.getMaxScore());
                newMaxNiceScore.setMinScore(oldMaxNiceScore.getMinScore());
                newMaxNiceScore.setScore(oldMaxNiceScore.getCurrentScore());
            }));

            getRedstoneTouch(oldPlayer).ifPresent(oldRedstoneTouch -> getRedstoneTouch(event.getEntity()).ifPresent(newRedstoneTouch -> {
                newRedstoneTouch.setState(oldRedstoneTouch.getCurrentState());
            }));
            event.getOriginal().invalidateCaps();
        }


        @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
                event.put(TTS_EntityTypes.TOYROBOT.get(), ToyRobotEntity.setAttributes());
                event.put(TTS_EntityTypes.EVIL_ROBOT.get(), EvilToyRobotEntity.setAttributes());
                event.put(TTS_EntityTypes.TOYSOLDIER.get(), ToySoldierEntity.setAttributes());
                event.put(TTS_EntityTypes.TOY_TANK.get(), EvilToyTankEntity.setAttributes());
                event.put(TTS_EntityTypes.KRAMPUS.get(), KrampusEntity.setAttributes());
            }
        }
    }
}

