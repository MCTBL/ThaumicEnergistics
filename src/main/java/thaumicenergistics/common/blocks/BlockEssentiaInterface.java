package thaumicenergistics.common.blocks;

import java.util.EnumSet;

import com.google.common.base.Optional;

import appeng.api.implementations.items.IMemoryCard;
import appeng.api.util.IOrientable;
import appeng.block.AEBaseTileBlock;
import appeng.client.render.BlockRenderInfo;
import appeng.client.render.blocks.RenderBlockInterface;
import appeng.client.texture.FlippableIcon;
import appeng.core.features.AEFeature;
import appeng.core.features.ActivityState;
import appeng.core.features.BlockStackSrc;
import appeng.core.sync.GuiBridge;
import appeng.tile.AEBaseTile;
import appeng.tile.misc.TileInterface;
import appeng.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumicenergistics.client.render.RenderBlockEssentiaInterface;
import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.ThEGuiHandler;
import thaumicenergistics.common.ThaumicEnergistics;
import thaumicenergistics.common.registries.Renderers;
import thaumicenergistics.common.registries.ThEStrings;
import thaumicenergistics.common.tiles.TileArcaneAssembler;
import thaumicenergistics.common.tiles.TileEssentiaInterface;
import thaumicenergistics.common.tiles.TileEssentiaProvider;

public class BlockEssentiaInterface extends AbstractBlockAEWrenchable {

	protected BlockEssentiaInterface() {
		// Call super with material machine (iron)
		super(Material.iron);

		// Basic hardness
		this.setHardness(1.0f);

		// Sound of metal
		this.setStepSound(Block.soundTypeMetal);
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

	@Override
	public TileEntity createNewTileEntity(World world, int metaData) {
		// Return the tile
		return new TileEssentiaInterface();
	}

	/**
	 * Called when the assembler is right-clicked
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @return
	 */
	@Override
	protected boolean onBlockActivated(final World world, final int x, final int y, final int z,
			final EntityPlayer player) {
		// Get what the player is holding
		ItemStack playerHolding = player.inventory.getCurrentItem();

		// Get the tile
		TileEntity tileInterface = world.getTileEntity(x, y, z);

		if (tileInterface instanceof TileEssentiaInterface) {
			// Are they holding a memory card?

			// Can the player interact with the assembler?
			if (((TileEssentiaInterface) tileInterface).isUseableByPlayer(player)) {
				// Launch the gui.
				ThEGuiHandler.launchGui(ThEGuiHandler.ESSENTIA_INTERFACE, player, world, x, y, z);

			}
		}

		return true;
	}

}
