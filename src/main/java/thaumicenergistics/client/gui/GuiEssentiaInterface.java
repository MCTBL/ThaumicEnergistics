package thaumicenergistics.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;

import appeng.api.config.AdvancedBlockingMode;
import appeng.api.config.InsertionMode;
import appeng.api.config.LockCraftingMode;
import appeng.api.config.Settings;
import appeng.api.config.Upgrades;
import appeng.api.config.YesNo;
import appeng.client.gui.implementations.GuiUpgradeable;
import appeng.client.gui.widgets.GuiImgButton;
import appeng.client.gui.widgets.GuiSimpleImgButton;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.client.gui.widgets.GuiToggleButton;
import appeng.core.localization.ButtonToolTips;
import appeng.core.localization.GuiColors;
import appeng.core.localization.GuiText;
import appeng.helpers.IInterfaceHost;
import thaumicenergistics.common.container.ContainerEssentiaInterface;
import thaumicenergistics.common.registries.ThEStrings;

public class GuiEssentiaInterface extends GuiUpgradeable {

    private GuiTabButton priority;
    private GuiImgButton BlockMode;
    private GuiToggleButton interfaceMode;
    private GuiImgButton insertionMode;
    private GuiSimpleImgButton doublePatterns;
    private GuiToggleButton patternOptimization;

    private GuiImgButton advancedBlockingMode;
    private GuiImgButton lockCraftingMode;

    public GuiEssentiaInterface(final EntityPlayer player, final World world, final int x, final int y, final int z) {
        super(new ContainerEssentiaInterface(player.inventory, (IInterfaceHost) world.getTileEntity(x, y, z)));
        this.ySize = 211;
    }

    @Override
    protected void addButtons() {
        this.priority = new GuiTabButton(
                this.guiLeft + 154,
                this.guiTop,
                2 + 4 * 16,
                GuiText.Priority.getLocal(),
                itemRender);
        this.buttonList.add(this.priority);

        int offset = 8;

        this.BlockMode = new GuiImgButton(this.guiLeft - 18, this.guiTop + offset, Settings.BLOCK, YesNo.NO);
        this.buttonList.add(this.BlockMode);

        offset += 18;

        this.interfaceMode = new GuiToggleButton(
                this.guiLeft - 18,
                this.guiTop + offset,
                84,
                85,
                GuiText.InterfaceTerminal.getLocal(),
                GuiText.InterfaceTerminalHint.getLocal());
        this.buttonList.add(this.interfaceMode);

        offset += 18;

        this.insertionMode = new GuiImgButton(
                this.guiLeft - 18,
                this.guiTop + offset,
                Settings.INSERTION_MODE,
                InsertionMode.DEFAULT);
        this.buttonList.add(this.insertionMode);

        offset += 18;

        this.doublePatterns = new GuiSimpleImgButton(this.guiLeft - 18, this.guiTop + offset, 71, "");
        this.doublePatterns.enabled = false;
        this.buttonList.add(this.doublePatterns);

        offset += 18;

        this.patternOptimization = new GuiToggleButton(
                this.guiLeft - 18,
                this.guiTop + offset,
                178,
                194,
                GuiText.PatternOptimization.getLocal(),
                GuiText.PatternOptimizationHint.getLocal());
        this.buttonList.add(this.patternOptimization);

        offset += 18;

        this.advancedBlockingMode = new GuiImgButton(
                this.guiLeft - 18,
                this.guiTop + offset,
                Settings.ADVANCED_BLOCKING_MODE,
                AdvancedBlockingMode.DEFAULT);
        this.advancedBlockingMode.visible = this.bc.getInstalledUpgrades(Upgrades.ADVANCED_BLOCKING) > 0;
        this.buttonList.add(advancedBlockingMode);

        offset += 18;

        this.lockCraftingMode = new GuiImgButton(
                this.guiLeft - 18,
                this.guiTop + offset,
                Settings.LOCK_CRAFTING_MODE,
                LockCraftingMode.NONE);
        this.lockCraftingMode.visible = this.bc.getInstalledUpgrades(Upgrades.LOCK_CRAFTING) > 0;
        this.buttonList.add(lockCraftingMode);
    }

    protected String getBackground() {
        return switch (((ContainerEssentiaInterface) this.cvb).getPatternCapacityCardsInstalled()) {
            case -1 -> "guis/interfacenone.png";
            case 1 -> "guis/interface2.png";
            case 2 -> "guis/interface3.png";
            case 3 -> "guis/interface4.png";
            default -> "guis/interface.png";
        };
    }

    @Override
    public void drawFG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        if (this.BlockMode != null) {
            this.BlockMode.set(((ContainerEssentiaInterface) this.cvb).getBlockingMode());
        }

        if (this.interfaceMode != null) {
            this.interfaceMode
                    .setState(((ContainerEssentiaInterface) this.cvb).getInterfaceTerminalMode() == YesNo.YES);
        }

        if (this.insertionMode != null) {
            this.insertionMode.set(((ContainerEssentiaInterface) this.cvb).getInsertionMode());
        }

        if (this.doublePatterns != null) {
            this.doublePatterns.enabled = ((ContainerEssentiaInterface) this.cvb).isAllowedToMultiplyPatterns;
            if (this.doublePatterns.enabled) this.doublePatterns.setTooltip(
                    ButtonToolTips.DoublePatterns.getLocal() + "\n" + ButtonToolTips.DoublePatternsHint.getLocal());
            else this.doublePatterns.setTooltip(
                    ButtonToolTips.DoublePatterns.getLocal() + "\n" + ButtonToolTips.OptimizePatternsNoReq.getLocal());
        }

        if (this.patternOptimization != null) {
            this.patternOptimization
                    .setState(((ContainerEssentiaInterface) this.cvb).getPatternOptimization() == YesNo.YES);
        }

        if (this.advancedBlockingMode != null) {
            this.advancedBlockingMode.set(((ContainerEssentiaInterface) this.cvb).getAdvancedBlockingMode());
        }

        if (this.lockCraftingMode != null) {
            this.lockCraftingMode.set(((ContainerEssentiaInterface) this.cvb).getLockCraftingMode());
        }

        this.fontRendererObj.drawString(
                this.getGuiDisplayName(ThEStrings.Block_EssentiaInterface.getLocalized()),
                8,
                6,
                GuiColors.InterfaceTitle.getColor());
    }

    @Override
    protected void actionPerformed(final GuiButton btn) {
        super.actionPerformed(btn);

        final boolean backwards = Mouse.isButtonDown(1);

    }

    @Override
    protected void handleButtonVisibility() {
        super.handleButtonVisibility();
        if (this.advancedBlockingMode != null) {
            this.advancedBlockingMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.ADVANCED_BLOCKING) > 0);
        }
        if (this.lockCraftingMode != null) {
            this.lockCraftingMode.setVisibility(this.bc.getInstalledUpgrades(Upgrades.LOCK_CRAFTING) > 0);
        }
    }

}
