package thaumicenergistics.api.storage;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;

/**
 * An aspect container that can store multiple aspects separately and doesn't share its capacity between different
 * aspects.
 */
public interface IMultiAspectContainer extends IAspectContainer {

    /**
     * Returns the capacity of aspect container for particular aspect.
     *
     * @param aspect
     * @return Maximum amount of essentia of specific aspect this container can store.
     */
    int getContainerCapacity(Aspect aspect);
}
