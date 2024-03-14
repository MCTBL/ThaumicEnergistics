package thaumicenergistics.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.ThaumicEnergistics;
import thaumicenergistics.common.tiles.TileEssentiaInterface;

public class BlockEssentiaInterface extends AbstractBlockAEWrenchable {

    protected BlockEssentiaInterface() {
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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEssentiaInterface();
    }

    @Override
    public String getUnlocalizedName() {
        return BlockEnum.ESSENTIA_INTERFACE.getUnlocalizedName();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return BlockTextureManager.ESSENTIA_INTERFACE.getTexture();
    }

    @Override
    public boolean isOpaqueCube() {
        return true;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

}
