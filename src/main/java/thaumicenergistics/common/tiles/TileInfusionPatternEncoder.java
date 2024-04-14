package thaumicenergistics.common.tiles;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

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
     * Slot ID's
     */
    public static int SLOT_SOURCE_ITEM = 0, SLOT_TARGET_ITEM = 1, SLOT_BLANK_PATTERNS = 2, SLOT_ENCODED_PATTERN = 3;

    public TileInfusionPatternEncoder() {
        super("infusion.encoder", TileInfusionPatternEncoder.SLOT_TOTAL_COUNT, 64);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
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

}
