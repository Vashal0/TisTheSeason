package net.vashal.tistheseason.items.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;

public class ToyRobotItem extends Item {

    public ToyRobotItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ToyRobotEntity toyRobotEntity = ToyRobotEntity.of(context.getLevel(), context.getPlayer());
        toyRobotEntity.setPos(context.getClickLocation());
        context.getLevel().addFreshEntity(toyRobotEntity);
        context.getItemInHand().setCount(0);
        return super.useOn(context);
    }
}
