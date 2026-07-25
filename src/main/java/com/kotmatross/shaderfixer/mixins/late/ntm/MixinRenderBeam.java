package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

import com.hbm.render.entity.projectile.RenderBeam;
import com.hbm.render.entity.projectile.RenderBeam5;
import com.kotmatross.shaderfixer.utils.ShaderUtils;

@Mixin(value = { RenderBeam.class, RenderBeam5.class, }, priority = 999)
public class MixinRenderBeam {
    
    @WrapMethod(method = "doRender")
    private void doThatDoThatThing(Entity entity, double x, double y, double z, float f0, float interp, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(entity, x, y, z, f0, interp);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }

}
