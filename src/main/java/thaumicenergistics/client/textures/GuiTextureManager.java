package thaumicenergistics.client.textures;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import thaumicenergistics.common.ThaumicEnergistics;

/**
 * Textures for all ThE GUIs.
 *
 * @author Nividica
 *
 */
@SideOnly(Side.CLIENT)
public enum GuiTextureManager {

    ESSENTIA_LEVEL_EMITTER("essentia.level.emitter"),
    ESSENTIA_STORAGE_BUS("essentia.storage.bus"),
    ESSENTIA_TERMINAL("essentia.terminal"),
    ESSENTIA_IO_BUS("essentia.io.bus"),
    ARCANE_CRAFTING_TERMINAL("arcane.crafting"),
    PRIORITY("priority"),
    CELL_WORKBENCH("essentia.cell.workbench"),
    ARCANE_ASSEMBLER("arcane.assembler"),
    ARCANE_ASSEMBLER_VISBAR("arcane.assembler.visbar"),
    KNOWLEDGE_INSCRIBER("knowledge.inscriber"),
    ESSENTIA_VIBRATION_CHAMBER("essentia.vibration.chamber"),
    DISTILLATION_ENCODER("distillation.encoder"),
    INFUSION_ENCODER("infusion.encoder"),
    ADVANCED_TOOLBOX("advanced.toolbox"),
    ESSENTIA_INTERFACE1("interface"),
    ESSENTIA_INTERFACE2("interface2"),
    ESSENTIA_INTERFACE3("interface3"),
    ESSENTIA_INTERFACE4("interface4"),
    ESSENTIA_INTERFACE_ONE("interfaceone");

    private ResourceLocation texture;

    private GuiTextureManager(final String textureName) {
        // Create the resource location
        this.texture = new ResourceLocation(ThaumicEnergistics.MOD_ID, "textures/gui/" + textureName + ".png");
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
