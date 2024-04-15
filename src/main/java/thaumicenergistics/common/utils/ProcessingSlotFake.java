package thaumicenergistics.common.utils;

import net.minecraft.inventory.IInventory;

import appeng.container.slot.IOptionalSlotHost;
import appeng.container.slot.OptionalSlotFake;

public class ProcessingSlotFake extends OptionalSlotFake {

    private static final int POSITION_SHIFT = 9000;
    private boolean hidden = false;

    public ProcessingSlotFake(IInventory inv, IOptionalSlotHost containerBus, int idx, int x, int y, int offX, int offY,
            int groupNum) {
        super(inv, containerBus, idx, x, y, offX, offY, groupNum);
    }

    public void setHidden(boolean hide) {
        if (this.hidden != hide) {
            this.hidden = hide;
            this.xDisplayPosition += (hide ? -1 : 1) * POSITION_SHIFT;
        }
    }
}
