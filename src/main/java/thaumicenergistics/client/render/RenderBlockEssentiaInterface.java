package thaumicenergistics.client.render;

import appeng.block.misc.BlockInterface;
import appeng.client.render.BaseBlockRender;
import appeng.client.render.BlockRenderInfo;
import appeng.client.texture.ExtraBlockTextures;
import appeng.tile.misc.TileInterface;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.blocks.BlockEssentiaInterface;
import thaumicenergistics.common.registries.Renderers;
import thaumicenergistics.common.tiles.TileEssentiaInterface;

public class RenderBlockEssentiaInterface extends RenderBlockProviderBase {
	
    public RenderBlockEssentiaInterface() {
        super(BlockTextureManager.ESSENTIA_INTERFACE);
    }

    @Override
    public int getRenderId() {
        // Return the ID of the essentia provider
        return Renderers.EssentiaInterfaceRenderID;
    }
	
}
