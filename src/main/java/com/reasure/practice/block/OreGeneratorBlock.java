package com.reasure.practice.block;

import com.reasure.practice.block.entity.OreGeneratorBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OreGeneratorBlock extends Block implements EntityBlock {
    public static final String MESSAGE_ORE_GENERATOR = "message.ore_generator";

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0, .2, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_UP = Shapes.box(0, 0, 0, 1, .8, 1);
    private static final VoxelShape SHAPE_NORTH = Shapes.box(0, 0, .2, 1, 1, 1);
    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0, 0, 0, 1, 1, .8);
    private static final VoxelShape SHAPE_WEST = Shapes.box(.2, 0, 0, 1, 1, 1);
    private static final VoxelShape SHAPE_EAST = Shapes.box(0, 0, 0, .8, 1, 1);

    public OreGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent(MESSAGE_ORE_GENERATOR).withStyle(ChatFormatting.BLUE));
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof OreGeneratorBlockEntity generator) {
                Direction dir = hit.getDirection();
                Direction facing = state.getValue(BlockStateProperties.FACING);
                // If the face that we hit is the same as the direction that our block is facing we know that we hit the front of the block
                if (dir == facing) {
                    // Subtract the position of our block from the location that we hit to get the relative location in 3D
                    Vec3 hitVec = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
                    // We want to transform this 3D location to 2D so that we can more easily check which quadrant is hit
                    double x = getXFromHit(facing, hitVec);
                    double y = getYFromHit(facing, hitVec);

                    if (x < .5 && y > .5) {
                        boolean correctBlock = false;
                        ItemStack item = player.getItemInHand(hand);
                        // If the item that the player is holding is a BlockItem then we get the blockstate from it
                        // and give that to our block entity
                        if (item.getItem() instanceof BlockItem blockItem) {
                            correctBlock = generator.setGeneratingBlock(blockItem.getBlock().defaultBlockState());
                        }

                        if (!correctBlock)
                            return InteractionResult.FAIL;
                    } else if (x > .5 && y > .5) {
                        generator.setGenerating(!generator.isGenerating());
                    } else if (x > .5 && y < .5) {
                        generator.setCollecting(!generator.isCollecting());
                    } else
                        return InteractionResult.FAIL;
                }
            }
        }

        return InteractionResult.sidedSuccess(!level.isClientSide());
    }

    private double getXFromHit(Direction facing, Vec3 hit) {
        return switch (facing) {
            case UP, DOWN, NORTH -> 1 - hit.x;
            case SOUTH -> hit.x;
            case WEST -> hit.z;
            case EAST -> 1 - hit.z;
        };
    }

    private double getYFromHit(Direction facing, Vec3 hit) {
        return switch (facing) {
            case UP -> hit.z;
            case DOWN -> 1 - hit.z;
            default -> hit.y;
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OreGeneratorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide()) {
            return (lvl, pos, stt, be) -> {
                if (be instanceof OreGeneratorBlockEntity generator)
                    generator.tickServer();
            };
        }
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
}
