package com.reasure.practice.block.entity;

import com.reasure.practice.capability.CustomEnergyStorage;
import com.reasure.practice.setup.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class PowerGenBlockEntity extends BlockEntity {
    public static final int POWER_GEN_CAPACITY = 50000; // Max capacity
    public static final int POWER_GEN_GENERATE = 30; // Generation per tick
    public static final int POWER_GEN_SEND = 200; // Power to send out per tick

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private final CustomEnergyStorage energyStorage = createEnergy();
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    private int counter;

    public PowerGenBlockEntity(BlockPos worldPos, BlockState blockState) {
        super(ModBlockEntityTypes.POWER_GEN_BLOCK_ENTITY.get(), worldPos, blockState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    // counter = 남아있는 연료 시간
    public void tickServer() {
        if (isGenerating()) {
            if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                energyStorage.addEnergy(POWER_GEN_GENERATE);
                counter--;
                setChanged();
            }
        }

        if (counter <= 0) {
            ItemStack fuel = itemHandler.getStackInSlot(0);
            int burnTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
            if (burnTime > 0) {
                ItemStack stack = itemHandler.extractItem(0, 1, false);
                if (stack.hasContainerItem() && itemHandler.getStackInSlot(0).isEmpty()) {
                    itemHandler.setStackInSlot(0, stack.getContainerItem());
                }
                counter = burnTime;
                setChanged();
            }
        }

        if (level != null) {
            BlockState blockState = level.getBlockState(worldPosition);
            boolean powered = isGenerating();
            if (blockState.getValue(BlockStateProperties.POWERED) != powered) {
                level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
            }
        }

        sendOutPower();
    }

    public boolean isGenerating() {
        return counter > 0 && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored();
    }

    private void sendOutPower() {
        AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        if (level != null && energy.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity blockEntity = level.getBlockEntity(worldPosition.relative(direction));
                if (blockEntity != null) {
                    boolean doContinue = blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                                if (handler.canReceive()) {
                                    int received = handler.receiveEnergy(Math.min(energy.get(), POWER_GEN_SEND), false);
                                    energy.addAndGet(-received);
                                    energyStorage.consumeEnergy(received);
                                    setChanged();
                                    return energy.get() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Energy")) {
            energyStorage.deserializeNBT(tag.get("Energy"));
        }
        if (tag.contains("Info")) {
            counter = tag.getCompound("Info").getInt("Counter");
        }
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("Counter", counter);
        tag.put("Info", infoTag);
        super.saveAdditional(tag);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the BE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                super.onContentsChanged(slot);
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private CustomEnergyStorage createEnergy() {
        return new CustomEnergyStorage(POWER_GEN_CAPACITY, POWER_GEN_SEND) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    public void dropContents(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemHandler.getStackInSlot(i).copy());
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
