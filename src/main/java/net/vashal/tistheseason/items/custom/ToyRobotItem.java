package net.vashal.tistheseason.items.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import org.jetbrains.annotations.NotNull;

public class ToyRobotItem extends Item {

    public ToyRobotItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.isClientSide) {
            ItemStack stack = context.getItemInHand();
            ToyRobotEntity toyRobot = ToyRobotEntity.create(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            if (toyRobot == null) {
                return InteractionResult.FAIL;
            }

            toyRobot.tame(player);
            toyRobot.setOwnerUUID(player.getUUID());
            level.addFreshEntity(toyRobot);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, toyRobot.blockPosition());
            stack.shrink(1);
            CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayer) player, toyRobot);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
