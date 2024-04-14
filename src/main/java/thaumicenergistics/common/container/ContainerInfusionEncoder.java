package thaumicenergistics.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

import appeng.container.slot.IOptionalSlotHost;
import appeng.container.slot.OptionalSlotFake;
import appeng.container.slot.SlotFake;
import thaumicenergistics.common.container.slot.SlotRestrictive;
import thaumicenergistics.common.inventory.TheInternalInventory;
import thaumicenergistics.common.tiles.TileInfusionPatternEncoder;

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
    public final ProcessingSlotFake[] slotSourceItems = new ProcessingSlotFake[ContainerInfusionEncoder.FAKE_SLOT_COUNT];

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
    private final TheInternalInventory internalInventory;

    /*
     * Default active page.
     */
    private int activePage;

    public ContainerInfusionEncoder(final EntityPlayer player, final World world, final int x, final int y,
            final int z) {
        super(player);
        // Get the encoder
        this.encoder = (TileInfusionPatternEncoder) world.getTileEntity(x, y, z);

        this.internalInventory = new TheInternalInventory("sourceItems", 72, 1000);

        this.activePage = 0;

        // Add the source item slots
        for (int index = 0; index < ContainerInfusionEncoder.FAKE_SLOT_COUNT / 2; index++) {

            // Create the slot and add it
            this.slotSourceItems[index] = new ProcessingSlotFake(
                    this.internalInventory,
                    this,
                    index,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_X,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_Y,
                    (index % 6),
                    (index / 6),
                    0);
            this.slotSourceItems[index + 36] = new ProcessingSlotFake(
                    this.internalInventory,
                    this,
                    index + 36,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_X,
                    ContainerInfusionEncoder.FIRST_FAKE_SLOT_Y,
                    (index % 6),
                    (index / 6),
                    1);
            this.slotSourceItems[index + 36].setHidden(true);
            this.addSlotToContainer(this.slotSourceItems[index]);
            this.addSlotToContainer(this.slotSourceItems[index + 36]);
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
