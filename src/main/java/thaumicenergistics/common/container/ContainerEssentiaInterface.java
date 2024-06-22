package thaumicenergistics.common.container;

import net.minecraft.entity.player.InventoryPlayer;

import com.glodblock.github.common.tile.TileFluidInterface;

import appeng.api.config.Settings;
import appeng.api.config.SidelessMode;
import appeng.api.util.IConfigManager;
import appeng.container.guisync.GuiSync;
import appeng.container.implementations.ContainerInterface;
import appeng.helpers.IInterfaceHost;

public class ContainerEssentiaInterface extends ContainerInterface {

    @GuiSync(11)
    public SidelessMode sidelessMode;

    private final boolean isTile;

    public ContainerEssentiaInterface(final InventoryPlayer ip, final IInterfaceHost te) {
        super(ip, te);
        this.sidelessMode = SidelessMode.SIDELESS;
        this.isTile = te instanceof TileFluidInterface;
    }

    public SidelessMode getSidelessMode() {
        return this.sidelessMode;
    }

    @Override
    protected void loadSettingsFromHost(IConfigManager cm) {
        super.loadSettingsFromHost(cm);
        this.sidelessMode = this.isTile ? (SidelessMode) cm.getSetting(Settings.SIDELESS_MODE) : SidelessMode.SIDELESS;
    }

}
