package thaumicenergistics.common.container;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import appeng.container.slot.IOptionalSlotHost;
import appeng.container.slot.OptionalSlotFake;
import appeng.container.slot.SlotFake;
import thaumicenergistics.common.container.slot.SlotRestrictive;
import thaumicenergistics.common.inventory.TheInternalInventory;
import thaumicenergistics.common.tiles.TileInfusionPatternEncoder;
import thaumicenergistics.common.utils.EffectiveSide;

public class ContainerInfusionEncoder extends ContainerWithPlayerInventory implements IOptionalSlotHost {

    /**
     * Y position for the player and hotbar inventory.
     */
    private static final int PLAYER_INV_POSITION_Y = 152,
            HOTBAR_INV_POSITION_Y = ContainerInfusionEncoder.PLAYER_INV_POSITION_Y + 58;

    /**
     * Position of the blank patterns.
     */
    private static final int SLOT_PATTERNS_BLANK_POS_X = 153, SLOT_PATTERNS_BLANK_POS_Y = 47;

    /**
     * Position of the encoded pattern.
     */
    private static final int SLOT_PATTERN_ENCODED_POS_X = 153, SLOT_PATTERN_ENCODED_POS_Y = 93;

    /**
     * Position of the source item.
     */
    private static final int SLOT_SOURCE_ITEM_POS_X = 132, SLOT_SOURCE_ITEM_POS_Y = 47;

    /**
     * Position of the target item.
     */
    private static final int SLOT_TARGET_ITEM_POS_X = 132, SLOT_TARGET_ITEM_POS_Y = 93;

    /**
     * Position of the fake slots.
     */
    private static final int FIRST_FAKE_SLOT_X = 10, FIRST_FAKE_SLOT_Y = 25, FAKE_SLOT_COUNT = 72;

    /**
     * Slot holding the source item.
     */
    public final SlotFake slotSourceItem;

    /**
     * Slot holding the source item.
     */
    public final SlotFake slotTragetItem;

    /**
     * Slots holding the source item's aspects.
     */
    public final ProcessingSlotFake[] slotCraftingItems = new ProcessingSlotFake[ContainerInfusionEncoder.FAKE_SLOT_COUNT];

    /**
     * Host encoder.
     */
    private final TileInfusionPatternEncoder encoder;

    /**
     * Blank patterns slot.
     */
    private final SlotRestrictive slotPatternsBlank;

    /**
     * Encoded pattern slot.
     */
    private final SlotRestrictive slotPatternEncoded;

    /**
     * Stores the source items.
     */
    private final TheInternalInventory craftingInventory;

    /*
     * Default active page.
     */
    private int activePage;

    public ContainerInfusionEncoder(final EntityPlayer player, final World world, final int x, final int y,
            final int z) {
        super(player);
        // Get the encoder
        this.encoder = (TileInfusionPatternEncoder) world.getTileEntity(x, y, z);

        this.craftingInventory = new TheInternalInventory("sourceItems", 72, 1000);

        this.activePage = 0;

        // Add the source item slots
        for (int index = 0; index < ContainerInfusionEncoder.FAKE_SLOT_COUNT; index++) {

            // Create the slot and add it
            this.slotCraftingItems[index] = new ProcessingSlotFake(
                    this.craftingInventory,
                    this,
                    index,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_X,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_Y,
                    (index % 6),
                    (index / 6),
                    0);
            this.addSlotToContainer(this.slotCraftingItems[index]);
            // Hidden the half
            this.slotCraftingItems[index].setHidden(index >= 36 ? true : false);
        }

        // Add the source and target item slot
        this.slotSourceItem = new SlotFake(
                encoder,
                TileInfusionPatternEncoder.SLOT_SOURCE_ITEM,
                ContainerInfusionEncoder.SLOT_SOURCE_ITEM_POS_X,
                ContainerInfusionEncoder.SLOT_SOURCE_ITEM_POS_Y);
        this.slotTragetItem = new SlotFake(
                encoder,
                TileInfusionPatternEncoder.SLOT_TARGET_ITEM,
                ContainerInfusionEncoder.SLOT_TARGET_ITEM_POS_X,
                ContainerInfusionEncoder.SLOT_TARGET_ITEM_POS_Y);
        this.addSlotToContainer(this.slotSourceItem);
        this.addSlotToContainer(this.slotTragetItem);

        // Add blank pattern slot
        this.slotPatternsBlank = new SlotRestrictive(
                this.encoder,
                TileInfusionPatternEncoder.SLOT_BLANK_PATTERNS,
                ContainerInfusionEncoder.SLOT_PATTERNS_BLANK_POS_X,
                ContainerInfusionEncoder.SLOT_PATTERNS_BLANK_POS_Y);
        this.addSlotToContainer(this.slotPatternsBlank);

        // Add encoded pattern slot
        this.slotPatternEncoded = new SlotRestrictive(
                this.encoder,
                TileInfusionPatternEncoder.SLOT_ENCODED_PATTERN,
                ContainerInfusionEncoder.SLOT_PATTERN_ENCODED_POS_X,
                ContainerInfusionEncoder.SLOT_PATTERN_ENCODED_POS_Y);
        this.addSlotToContainer(this.slotPatternEncoded);

        // Bind to the players inventory
        this.bindPlayerInventory(
                player.inventory,
                ContainerInfusionEncoder.PLAYER_INV_POSITION_Y,
                ContainerInfusionEncoder.HOTBAR_INV_POSITION_Y);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        // Ensure there is an encoder
        if (this.encoder != null) {
            // Ask it if this player can interact
            return this.encoder.isUseableByPlayer(player);
        }

        return false;
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer player, final int slotIndex) {
        if (EffectiveSide.isClientSide()) {
            // Ignored client side
            return null;
        }

        // Get the slot
        Slot clickedSlot = this.getSlotOrNull(slotIndex);

        // Slot empty?
        if ((clickedSlot == null) || !clickedSlot.getHasStack()) {
            // Done
            return null;
        }
        // Was this handled?
        if (this.handleSlotTransfer(clickedSlot)) {
            // Was the itemstack in the slot drained?
            if (clickedSlot.getHasStack() && (clickedSlot.getStack().stackSize <= 0)) {
                // Set to null
                clickedSlot.putStack(null);
            } else {
                // Let the slot know it has changed.
                clickedSlot.onSlotChanged();
            }

            // Update
            this.detectAndSendChanges();
        }

        return null;
    }

    private boolean handleSlotTransfer(@Nonnull final Slot clickedSlot) {
        // Get the stack
        ItemStack slotStack = clickedSlot.getStack();

        // Is the slot in the player inventory?
        if (clickedSlot.inventory == this.player.inventory) {
            // Will the blank pattern slot take this?
            if (this.slotPatternsBlank.isItemValid(slotStack)) {
                return this.mergeItemStack(
                        slotStack,
                        this.slotPatternsBlank.slotNumber,
                        this.slotPatternsBlank.slotNumber + 1,
                        false);
            }

            // Will the encoded pattern take this?
            if (this.slotPatternEncoded.isItemValid(slotStack)) {
                return this.mergeItemStack(
                        slotStack,
                        this.slotPatternEncoded.slotNumber,
                        this.slotPatternEncoded.slotNumber + 1,
                        false);
            }
            if (!this.slotSourceItem.getHasStack()) {
                this.slotSourceItem.putStack(slotStack);
                return true;
            }
            if (!this.slotTragetItem.getHasStack()) {
                this.slotTragetItem.putStack(slotStack);
                return true;
            }
        }

        // Pattern slot?
        if (((this.slotPatternsBlank == clickedSlot) || (this.slotPatternEncoded == clickedSlot))) {
            // Merge with the hotbar?
            if (!this.mergeSlotWithHotbarInventory(slotStack)) {
                // Merge with the player inventory
                return this.mergeSlotWithPlayerInventory(slotStack);
            }
            // Merged with hotbar
            return true;
        }

        // End
        return false;
    }

    @Override
    public ItemStack slotClick(final int slotNumber, final int buttonPressed, final int flag,
            final EntityPlayer player) {
        // If true detect will be called, and null returned.
        boolean handled = false;

        // click these two slot
        if (this.slotSourceItem.slotNumber == slotNumber || this.slotTragetItem.slotNumber == slotNumber) {
            // Get the slot
            SlotFake targetSlot = this.slotSourceItem.slotNumber == slotNumber ? this.slotSourceItem
                    : this.slotTragetItem;
            // Get the item the player is dragging
            ItemStack heldItem = player.inventory.getItemStack();

            // Set the source slot
            if (heldItem != null) {
                ItemStack copy = heldItem.copy();
                copy.stackSize = 1;
                targetSlot.putStack(copy);
            } else {
                targetSlot.putStack(null);
            }
            handled = true;
        }

        // check if is slotSourceItems
        if (!handled && slotNumber < ContainerInfusionEncoder.FAKE_SLOT_COUNT && slotNumber >= 0) {
            // Get the item the player is dragging
            ItemStack heldItem = player.inventory.getItemStack();
            // Set the source slot
            if (heldItem != null) {
                ItemStack copy = heldItem.copy();

                // If player dragging item is same as the item in slot
                if (this.slotCraftingItems[slotNumber].getStack() != null
                        && this.slotCraftingItems[slotNumber].getStack().isItemEqual(copy)) {
                    // Add the number
                    copy.stackSize = this.slotCraftingItems[slotNumber].getStack().stackSize + copy.stackSize;
                }

                this.slotCraftingItems[slotNumber].putStack(copy);
            } else {
                this.slotCraftingItems[slotNumber].putStack(null);
            }
            handled = true;
        }

        // Was the click handled?
        if (handled) {
            // Detect any changes
            this.detectAndSendChanges();

            // Done
            return null;
        }

        // better call sual
        return super.slotClick(slotNumber, buttonPressed, flag, player);
    }

    public void changeActivePage(final int currentScroll) {
        // TODO
        System.out.println("==========Roll==========");
    }

    public void clearSlots() {

        // Clear all the slot
        this.slotSourceItem.clearStack();
        this.slotTragetItem.clearStack();

        // Clear all slots
        for (ProcessingSlotFake slot : this.slotCraftingItems) {
            slot.clearStack();
        }
    }

    public void encodePattern() {
        // TODO
        System.out.println("==========encodePattern==========");
    }

    /**
     * Returns the player for this container.
     *
     * @return
     */
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        this.encoder.craftingInventory = this.craftingInventory;
        super.onContainerClosed(player);
    }

    private static class ProcessingSlotFake extends OptionalSlotFake {

        private static final int POSITION_SHIFT = 9000;
        private boolean hidden = false;

        public ProcessingSlotFake(IInventory inv, IOptionalSlotHost containerBus, int idx, int x, int y, int offX,
                int offY, int groupNum) {
            super(inv, containerBus, idx, x, y, offX, offY, groupNum);
        }

        public void setHidden(boolean hide) {
            if (this.hidden != hide) {
                this.hidden = hide;
                this.xDisplayPosition += (hide ? -1 : 1) * POSITION_SHIFT;
            }
        }
    }

    @Override
    public boolean isSlotEnabled(int idx) {
        return idx == this.activePage;
    }
}
