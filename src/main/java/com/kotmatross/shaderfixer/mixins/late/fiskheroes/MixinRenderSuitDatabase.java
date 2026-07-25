package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.render.tile.RenderSuitDatabase;
import com.fiskmods.heroes.common.tileentity.TileEntitySuitDatabase;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RenderSuitDatabase.class, priority = 999, remap = false)
public abstract class MixinRenderSuitDatabase extends TileEntitySpecialRenderer {
    
    @WrapMethod(method = "render")
    private void doThatDoThatThing(TileEntitySuitDatabase tile, double x, double y, double z, float partialTicks, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(tile, x, y, z, partialTicks);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
