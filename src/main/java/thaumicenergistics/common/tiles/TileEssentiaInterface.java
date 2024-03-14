package thaumicenergistics.common.tiles;

import net.minecraft.item.ItemStack;

import appeng.api.config.Settings;
import appeng.api.config.SidelessMode;
import appeng.tile.misc.TileInterface;

public class TileEssentiaInterface extends TileInterface {

    public TileEssentiaInterface() {
        super.getInterfaceDuality().getConfigManager().registerSetting(Settings.SIDELESS_MODE, SidelessMode.SIDELESS);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // TODO Auto-generated method stub
        return false;
    }

}
