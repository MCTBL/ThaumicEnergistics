package thaumicenergistics.common.utils;

import java.util.Arrays;
import java.util.Objects;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.storage.data.IAEItemStack;
import appeng.helpers.PatternHelper;
import appeng.util.item.AEItemStack;

public class EssentiaPatternDetails implements ICraftingPatternDetails, Comparable<EssentiaPatternDetails> {

    private final ItemStack patternStack;
    private IAEItemStack patternStackAe;
    private IAEItemStack[] inputs = null, inputsCond = null, outputs = null, outputsCond = null;
    private int priority = 0;
    private int combine = 0;
    private int beSubstitute = 0;

    public EssentiaPatternDetails(ItemStack stack) {
        this.patternStack = stack;
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("author")) {
            final ItemStack forComparison = this.patternStack.copy();
            forComparison.stackTagCompound.removeTag("author");
            this.patternStackAe = Objects.requireNonNull(AEItemStack.create(forComparison)); // s2g
        } else {
            this.patternStackAe = Objects.requireNonNull(AEItemStack.create(stack)); // s2g
        }
    }

    @Override
    public int compareTo(EssentiaPatternDetails o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack getPattern() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValidItemForSlot(int slotIndex, ItemStack itemStack, World world) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCraftable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IAEItemStack[] getInputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IAEItemStack[] getCondensedInputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IAEItemStack[] getCondensedOutputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IAEItemStack[] getOutputs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canSubstitute() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ItemStack getOutput(InventoryCrafting craftingInv, World world) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPriority() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setPriority(int priority) {
        // TODO Auto-generated method stub

    }

    private static IAEItemStack[] condenseStacks(IAEItemStack[] stacks) {
        return PatternHelper.convertToCondensedList(stacks);
    }

    public boolean setInputs(IAEItemStack[] inputs) {
        IAEItemStack[] condensed = condenseStacks(inputs);
        if (condensed.length == 0) {
            return false;
        }
        this.inputs = inputs;
        this.inputsCond = condensed;
        return true;
    }

    public boolean setOutputs(IAEItemStack[] outputs) {
        IAEItemStack[] condensed = condenseStacks(outputs);
        if (condensed.length == 0) {
            return false;
        }
        this.outputs = Arrays.stream(outputs).filter(Objects::nonNull).toArray(IAEItemStack[]::new);
        this.outputsCond = condensed;
        return true;
    }

    public boolean readFromStack() {
        if (!patternStack.hasTagCompound()) {
            return false;
        }
        NBTTagCompound tag = Objects.requireNonNull(patternStack.getTagCompound());
        // may be possible to enter a partially-correct state if setInputs succeeds but setOutputs failed
        // but outside code should treat it as completely incorrect and not attempt to make calls
        return setInputs(readStackArray(tag.getTagList("in", Constants.NBT.TAG_COMPOUND)))
                && setOutputs(readStackArray(tag.getTagList("out", Constants.NBT.TAG_COMPOUND)));
    }

    public static IAEItemStack[] readStackArray(NBTTagList listTag) {
        // see note at top of class
        IAEItemStack[] stacks = new IAEItemStack[listTag.tagCount()];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = AEItemStack.loadItemStackFromNBT(listTag.getCompoundTagAt(i));
        }
        return stacks;
    }

}
