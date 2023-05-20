package thaumicenergistics.common.integration;

import cpw.mods.fml.common.Optional;
import thaumicenergistics.api.ThEApi;
import thaumicenergistics.common.fluids.GaseousEssentia;

public class ModuleAe2fc {

    private ModuleAe2fc() {}

    /**
     * Integrates with CC
     */
    @Optional.Method(modid = "ae2fc")
    static void init() {
        if (ThEApi.instance().config().blacklistEssentiaFluidInExtraCells()) {
            FluidCraftAPI.instance().blacklistFluidInStorage(GaseousEssentia.class);
            FluidCraftAPI.instance().blacklistFluidInDisplay(GaseousEssentia.class);
        }
    }
}
