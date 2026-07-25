package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.hbm.entity.projectile.EntityChemical;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.hbm.render.entity.projectile.RenderChemical;
import com.kotmatross.shaderfixer.utils.ShaderUtils;

@Mixin(value = RenderChemical.class, priority = 999, remap = false)
public class MixinRenderChemical {
    
    @WrapMethod(method = "renderAmatBeam")
    private void doThatDoThatThing(EntityChemical chem, float interp, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(chem, interp);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }
    
    @ModifyArg(method = "renderAmatBeam"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_F(FFFF)V"
                , remap = true)
            , index = 3)
    private float alphaFix(float alpha) {
        return alpha == 0 ? 0.01F : alpha * 2F;
    }

}
