package com.reasure.practice.entity.goal;

import com.reasure.practice.entity.ThiefEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Random;

public class ThiefFindChestGoal extends MoveToBlockGoal {
    private final ThiefEntity thief;
    private final Random random = new Random();

    private int stealingCounter = 20;

    public ThiefFindChestGoal(ThiefEntity mob, double speedModifier) {
        super(mob, speedModifier, 16);
        this.thief = mob;
    }

    /**
     * 과제의 내부 상태 재설정. 이 과제가 다른 과제에 의해 중단될 때 호출
     */
    @Override
    public void stop() {
        super.stop();
        thief.setStealing(false);
        BlockEntity be = mob.level.getBlockEntity(blockPos);
        if (be instanceof ChestBlockEntity) {
            mob.level.blockEvent(blockPos, be.getBlockState().getBlock(), 1, 0);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget()) {
            BlockEntity be = mob.level.getBlockEntity(blockPos);
            if (be instanceof ChestBlockEntity chest) {
                if (thief.isStealing()) {
                    stealingCounter--;
                    if (stealingCounter <= 0) {
                        stealingCounter = 20;
                        ItemStack stack = extractRandomItem(chest);
                        if (!stack.isEmpty()) {
                            spawnInWorld(mob.level, blockPos.above(), stack);
                        }
                    }
                } else {
                    mob.level.blockEvent(blockPos, be.getBlockState().getBlock(), 1, 1);
                    stealingCounter = 20;
                    thief.setStealing(true);
                }
            }
        }
    }

    private ItemStack extractRandomItem(BlockEntity e) {
        return e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).map(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    if (random.nextFloat() < .3f) {
                        return handler.extractItem(i, 1, false);
                    }
                }
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    private static void spawnInWorld(Level level, BlockPos pos, ItemStack remaining) {
        if (!remaining.isEmpty()) {
            ItemEntity entityItem = new ItemEntity(level, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, remaining);
            entityItem.setPickUpDelay(40);
            entityItem.setDeltaMovement(entityItem.getDeltaMovement().multiply(0, 1, 0));
            level.addFreshEntity(entityItem);
        }
    }

    /**
     * 지정된 위치를 대상으로 설정하고 싶으면 true 반환
     */
    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        if (!level.isEmptyBlock(pos.above())) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(pos);
            return blockState.is(Tags.Blocks.CHESTS);
        }
    }
}
