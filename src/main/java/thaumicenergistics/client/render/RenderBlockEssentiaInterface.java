package thaumicenergistics.client.render;

import thaumicenergistics.client.textures.BlockTextureManager;
import thaumicenergistics.common.registries.Renderers;

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
