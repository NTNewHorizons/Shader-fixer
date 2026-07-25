package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.render.entity.projectile.RenderSiegeLaser;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RenderSiegeLaser.class, priority = 999, remap = false)
public class MixinRenderSiegeLaser {
    
    @WrapMethod(method = "renderDart")
    private void doThatDoThatThing(EntitySiegeLaser laser, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(laser);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }

}
