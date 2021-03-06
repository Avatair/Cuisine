package snownee.cuisine.plugins.crafttweaker;

import java.util.ArrayList;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import snownee.cuisine.Cuisine;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.crafting.input.RegularItemStackInput;
import snownee.kiwi.util.definition.OreDictDefinition;

@KiwiModule(modid = Cuisine.MODID, name = "crafttweaker", dependency = "crafttweaker")
public final class CTSupport implements IModule
{
    static ArrayList<IAction> DELAYED_ACTIONS = new ArrayList<>(16);

    @Override
    public void postInit()
    {
        DELAYED_ACTIONS.forEach(CraftTweakerAPI::apply);
        DELAYED_ACTIONS.clear();
    }

    static OreDictDefinition fromOreEntry(IOreDictEntry entry)
    {
        return entry == null ? OreDictDefinition.EMPTY : OreDictDefinition.of(entry.getName(), entry.getAmount());
    }

    static RegularItemStackInput fromItemStack(IItemStack ctDefinition)
    {
        return RegularItemStackInput.of(toNative(ctDefinition));
    }

    static ItemStack toNative(IItemStack ctDefinition)
    {
        return CraftTweakerMC.getItemStack(ctDefinition);
    }

    static FluidStack toNative(ILiquidStack ctDefinition)
    {
        return CraftTweakerMC.getLiquidStack(ctDefinition);
    }

}
