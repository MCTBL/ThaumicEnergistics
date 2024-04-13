package thaumicenergistics.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ContainerInfusionEncoder extends ContainerWithPlayerInventory {

    /**
     * Y position for the player and hotbar inventory.
     */
    private static final int PLAYER_INV_POSITION_Y = 152,
            HOTBAR_INV_POSITION_Y = ContainerInfusionEncoder.PLAYER_INV_POSITION_Y + 58;

    public ContainerInfusionEncoder(final EntityPlayer player, final World world, final int x, final int y,
            final int z) {
        super(player);

        // Bind to the players inventory
        this.bindPlayerInventory(
                player.inventory,
                ContainerInfusionEncoder.PLAYER_INV_POSITION_Y,
                ContainerInfusionEncoder.HOTBAR_INV_POSITION_Y);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        // TODO Auto-generated method stub
        return false;
    }

}
