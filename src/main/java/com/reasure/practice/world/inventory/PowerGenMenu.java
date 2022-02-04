package com.reasure.practice.world.inventory;

import com.reasure.practice.capability.CustomEnergyStorage;
import com.reasure.practice.setup.ModBlockEntityTypes;
import com.reasure.practice.setup.ModBlocks;
import com.reasure.practice.setup.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class PowerGenMenu extends AbstractContainerMenu {
    private final BlockEntity blockEntity;
    private final IItemHandler playerInventory;

    public PowerGenMenu(int windowId, Inventory playerInventory, BlockPos pos) {
        super(ModMenuTypes.POWER_GEN_CONTAINER.get(), windowId);
        this.blockEntity = playerInventory.player.level.getBlockEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .ifPresent(itemHandler -> addSlot(new SlotItemHandler(itemHandler, 0, 64, 24)));
        }
        layoutPlayerInvSlots(10, 70);
        trackPower();
    }

    // Setup syncing of power from server to client so that the GUI can show the amount of power in the block
    private void trackPower() {
        // Unfortunately on a dedicated server ints are actually truncated to short, so we need
        // to split our integer here (split our 32-bit integer into two 16-bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                if (blockEntity != null) {
                    blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
                        int energyStored = iEnergyStorage.getEnergyStored() & 0xffff0000;
                        ((CustomEnergyStorage) iEnergyStorage).setEnergy(energyStored | (value & 0xffff));
                    });
                }
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                if (blockEntity != null) {
                    blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
                        int energyStored = iEnergyStorage.getEnergyStored() & 0xffff;
                        ((CustomEnergyStorage) iEnergyStorage).setEnergy(energyStored | (value << 16));
                    });
                }
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        //noinspection ConstantConditions
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, ModBlocks.POWER_GEN_BLOCK.get());
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        // 리턴 값 : 움직일 아이템 스택의 복사본 (이동 전)
        // slot 0 : 발전기의 연료 슬롯
        // slot 1 ~ 27 : 플레이어 인벤토리
        // slot 28 ~ 36 : 핫바
        ItemStack returnStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack clickedStack = slot.getItem();
            returnStack = clickedStack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(clickedStack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                //                slot.onQuickCraft(clickedStack, returnStack);
            } else {
                if (ForgeHooks.getBurnTime(clickedStack, RecipeType.SMELTING) > 0) {
                    if (!this.moveItemStackTo(clickedStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(clickedStack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(clickedStack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (clickedStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (clickedStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, clickedStack);
        }

        return returnStack;
    }

    // return last + 1 index
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int horAmount, int dx) {
        for (int i = 0; i < horAmount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    // return last + 1 index
    @SuppressWarnings({"SameParameterValue", "UnusedReturnValue"})
    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    @SuppressWarnings("SameParameterValue")
    private void layoutPlayerInvSlots(int leftCol, int topRow) {
        // Player Inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        addSlotRange(playerInventory, 0, leftCol, topRow + 58, 9, 18);
    }
}
