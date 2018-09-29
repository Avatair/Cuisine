package snownee.cuisine.plugins;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.cuisine.Cuisine;
import snownee.cuisine.items.ItemAbstractComposite;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;

@KiwiModule(modid = Cuisine.MODID, name = "darkutils", dependency = "darkutils", optional = true)
public class DarkUtilsCompat implements IModule
{
    private static Item itemGluttonyCharm;

    @Override
    public void init()
    {
        if ((itemGluttonyCharm = Item.getByNameOrId("darkutils:charm_gluttony")) != null)
        {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getItem().getItem() instanceof ItemAbstractComposite && hasItem(itemGluttonyCharm, (EntityPlayer) event.getEntityLiving()) && !event.getItem().isEmpty())
        {
            event.setDuration(0);
        }
    }

    private boolean hasItem(Item item, EntityPlayer player)
    {
        // Search for charm in equipment slots
        for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values())
        {
            if (player.getItemStackFromSlot(slot).getItem() == item)
            {
                return true;
            }
        }

        // Search for charm in player inventory
        for (final ItemStack stack : player.inventory.mainInventory)
        {
            if (stack.getItem() == item)
            {
                return true;
            }
        }

        return Loader.isModLoaded("baubles") && BaublesApi.isBaubleEquipped(player, item) != -1;
    }
}
