package thaumicenergistics.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

}
