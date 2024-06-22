package thaumicenergistics.common.blocks;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import appeng.block.AEBaseTileBlock;
import appeng.core.features.AEFeature;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.ThEGuiHandler;
import thaumicenergistics.common.registries.Renderers;
import thaumicenergistics.common.registries.ThEStrings;
import thaumicenergistics.common.tiles.TileEssentiaInterface;

public class BlockEssentiaInterface extends AEBaseTileBlock {

    protected BlockEssentiaInterface() {
        super(Material.iron);
        setTileEntity(TileEssentiaInterface.class);
        setFeature(EnumSet.of(AEFeature.Core));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int direction, final int metadata) {
        return BlockTextureManager.ESSENTIA_INTERFACE.getTexture();
    }

    @Override
    public String getUnlocalizedName() {
        return ThEStrings.Block_EssentiaInterface.getUnlocalized();
    }

    @Override
    public String getLocalizedName() {
        return ThEStrings.Block_EssentiaInterface.getLocalized();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType() {
        // Provide our custom ID
        return Renderers.EssentiaInterfaceRenderID;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        TileEntity tile = worldIn.getTileEntity(x, y, z);
        if (tile instanceof TileEssentiaInterface te) {
            te.getInterfaceDuality().updateRedstoneState();
        }
    }

    /**
     * Called when the assembler is right-clicked
     *
     * @param w      World
     * @param x
     * @param y
     * @param z
     * @param player EntityPlayer
     * @return
     */
    @Override
    public boolean onBlockActivated(final World w, final int x, final int y, final int z, final EntityPlayer player,
            final int side, final float hitX, final float hitY, final float hitZ) {
        // Get what the player is holding
        ItemStack playerHolding = player.inventory.getCurrentItem();

        // Get the tile
        TileEntity tileInterface = w.getTileEntity(x, y, z);

        if (tileInterface instanceof TileEssentiaInterface) {
            // Can the player interact with the assembler?
            if (((TileEssentiaInterface) tileInterface).isUseableByPlayer(player)) {
                // Launch the gui.
                ThEGuiHandler.launchGui(ThEGuiHandler.ESSENTIA_INTERFACE, player, w, x, y, z);

            }
        }

        return true;
    }
}
