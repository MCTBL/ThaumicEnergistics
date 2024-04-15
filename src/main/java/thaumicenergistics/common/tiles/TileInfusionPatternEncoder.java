package thaumicenergistics.common.tiles;

import java.util.ArrayList;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import appeng.api.AEApi;
import thaumicenergistics.api.ThEApi;
import thaumicenergistics.common.tiles.abstraction.ThETileInventory;

public class TileInfusionPatternEncoder extends ThETileInventory implements ISidedInventory {

    /**
     * Slot counts
     */
    public static int SLOT_PATTERNS_COUNT = 2, SLOT_SOURCE_ITEM_COUNT = 73, SLOT_TARGET_ITEM_COUNT = 1,
            SLOT_TOTAL_COUNT = TileInfusionPatternEncoder.SLOT_PATTERNS_COUNT
                    + TileInfusionPatternEncoder.SLOT_SOURCE_ITEM_COUNT
                    + TileInfusionPatternEncoder.SLOT_TARGET_ITEM_COUNT;

    /**
     * NBT Keys
     */
    private static String NBTKEY_INVENTORY = "inventory";

    /**
     * Slot ID's
     */
    public static int SLOT_SOURCE_ITEM = 0, SLOT_TARGET_ITEM = 1, SLOT_BLANK_PATTERNS = 2, SLOT_ENCODED_PATTERN = 3;

    public TileInfusionPatternEncoder() {
        super("infusion.encoder", TileInfusionPatternEncoder.SLOT_TOTAL_COUNT, 64);
    }

    /**
     * Returns a list of items to drop when broken.
     *
     * @return
     */
    public ArrayList<ItemStack> getDrops(final ArrayList<ItemStack> drops) {
        // Add encoded
        if (this.internalInventory.getHasStack(TileInfusionPatternEncoder.SLOT_ENCODED_PATTERN)) {
            drops.add(this.internalInventory.getStackInSlot(TileInfusionPatternEncoder.SLOT_ENCODED_PATTERN));
        }

        // Add blank
        if (this.internalInventory.getHasStack(TileInfusionPatternEncoder.SLOT_BLANK_PATTERNS)) {
            drops.add(this.internalInventory.getStackInSlot(TileInfusionPatternEncoder.SLOT_BLANK_PATTERNS));
        }

        return drops;
    }

    /**
     * True if there is a pattern to encode onto.
     *
     * @return
     */
    public boolean hasPatterns() {
        // Is there anything in the pattern slots?
        return this.internalInventory.getHasStack(TileInfusionPatternEncoder.SLOT_ENCODED_PATTERN)
                || this.internalInventory.getHasStack(TileInfusionPatternEncoder.SLOT_BLANK_PATTERNS);
    }

    @Override
    public boolean isItemValidForSlot(final int slotId, final ItemStack itemStack) {
        // Can always clear a slot
        if (itemStack == null) {
            return true;
        }

        // Empty pattern slot?
        if (slotId == TileInfusionPatternEncoder.SLOT_BLANK_PATTERNS) {
            return AEApi.instance().definitions().materials().blankPattern().isSameAs(itemStack);
        }

        // Encoded pattern slot?
        if (slotId == TileInfusionPatternEncoder.SLOT_ENCODED_PATTERN) {
            return ThEApi.instance().items().EssentiaEncodedPattern.getStack().isItemEqual(itemStack);
        }

        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return null;
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    /**
     * Does not need ticks.
     */
    @Override
    public boolean canUpdate() {
        return false;
    }

    /**
     * Read tile state from NBT.
     */
    @Override
    public void readFromNBT(final NBTTagCompound data) {
        // Call super
        super.readFromNBT(data);

        // Has saved inventory?
        if (data.hasKey(TileInfusionPatternEncoder.NBTKEY_INVENTORY)) {
            this.internalInventory.readFromNBT(data, TileInfusionPatternEncoder.NBTKEY_INVENTORY);
        }
    }

    /**
     * Write tile state to NBT.
     */
    @Override
    public void writeToNBT(final NBTTagCompound data) {
        // Call super
        super.writeToNBT(data);

        // Write the inventory
        this.internalInventory.writeToNBT(data, TileInfusionPatternEncoder.NBTKEY_INVENTORY);
    }

}
