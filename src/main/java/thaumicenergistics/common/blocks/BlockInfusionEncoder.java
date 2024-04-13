package thaumicenergistics.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.ThEGuiHandler;
import thaumicenergistics.common.ThaumicEnergistics;
import thaumicenergistics.common.tiles.TileInfusionPatternEncoder;

public class BlockInfusionEncoder extends AbstractBlockAEWrenchable {

    protected BlockInfusionEncoder() {
        // Call super with material machine (iron)
        super(Material.iron);

        // Basic hardness
        this.setHardness(1.0f);

        // Sound of metal
        this.setStepSound(Block.soundTypeMetal);

        // Place in the ThE creative tab
        this.setCreativeTab(ThaumicEnergistics.ThETab);
    }

    @Override
    public TileEntity createNewTileEntity(final World world, final int metaData) {
        return new TileInfusionPatternEncoder();
    }

    @Override
    protected final boolean onBlockActivated(final World world, final int x, final int y, final int z,
            final EntityPlayer player) {
        // Launch the gui.
        ThEGuiHandler.launchGui(ThEGuiHandler.INFUSION_ENCODER, player, world, x, y, z);

        return true;
    }

    /**
     * Gets the standard block icon.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, final int meta) {
        // Face?
        if (side == ForgeDirection.OPPOSITES[meta]) {
            // Face texture
            return BlockTextureManager.INFUSION_ENCODER.getTextures()[1];
        }

        // Top or bottom?
        if ((ForgeDirection.VALID_DIRECTIONS[side] == ForgeDirection.UP)
                || (ForgeDirection.VALID_DIRECTIONS[side] == ForgeDirection.DOWN)) {
            // Bottom texture
            return BlockTextureManager.INFUSION_ENCODER.getTextures()[2];
        }

        // Sides
        return BlockTextureManager.INFUSION_ENCODER.getTextures()[0];
    }

    /**
     * Gets the unlocalized name of the block.
     */
    @Override
    public String getUnlocalizedName() {
        return BlockEnum.INFUSION_ENCODER.getUnlocalizedName();
    }

    @Override
    public boolean rotateBlock(final World world, final int x, final int y, final int z, final ForgeDirection side) {
        // Get and increment the meta data
        int metaData = world.getBlockMetadata(x, y, z) + 1;

        // Bounds check
        if (metaData >= ForgeDirection.VALID_DIRECTIONS.length) {
            metaData = 0;
        }

        // Set the meta data
        world.setBlockMetadataWithNotify(x, y, z, metaData, 3);

        return true;
    }

}
