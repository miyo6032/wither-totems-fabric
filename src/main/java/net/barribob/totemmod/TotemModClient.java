package net.barribob.totemmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class TotemModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
	BlockRenderLayerMap.INSTANCE.putBlock(TotemMod.TOTEM_TOP, RenderLayer.getCutout());
    }
}
