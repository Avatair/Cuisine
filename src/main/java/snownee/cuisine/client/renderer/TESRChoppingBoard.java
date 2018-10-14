package snownee.cuisine.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import snownee.cuisine.tiles.TileChoppingBoard;

public class TESRChoppingBoard extends TileEntitySpecialRenderer<TileChoppingBoard>
{

    @Override
    public void render(TileChoppingBoard tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        ItemStack itemStack = tile.stacks.getStackInSlot(0);
        if (!itemStack.isEmpty() && !tile.hasKitchenKnife())
        {
            Minecraft mc = Minecraft.getMinecraft();
            RenderItem renderItem = mc.getRenderItem();
            IBakedModel bakedModel = renderItem.getItemModelWithOverrides(itemStack, tile.getWorld(), mc.player);

            GlStateManager.pushMatrix();
            RenderHelper.disableStandardItemLighting();

            GlStateManager.translate(x + 0.5, y, z + 0.5);

            int angle = 0;
            if (tile.getFacing().getHorizontalAngle() % 180 != 0)
            {
                angle = 180;
            }

            GlStateManager.rotate(tile.getFacing().getHorizontalAngle() + angle, 0, 1, 0);

            if (bakedModel.isGui3d())
            {
                // Block
                GlStateManager.scale(0.8, 0.8, 0.8);
                GlStateManager.translate(0, 0.55, 0);
            }
            else
            {
                // Item
                GlStateManager.scale(0.5, 0.5, 0.5);
                GlStateManager.translate(0, 0.5, 0);
                GlStateManager.rotate(90, 1, 0, 0);
            }

            RenderHelper.enableStandardItemLighting();
            renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.popMatrix();
        }

    }
}
