package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.hbm.render.tileentity.RenderMachineForceField;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RenderMachineForceField.class, priority = 999, remap = false)
public class MixinRenderMachineForceField {

    @WrapWithCondition(method = "renderTileEntityAt"
            , at = @At(value = "INVOKE"
                , target = "Lcom/hbm/render/tileentity/RenderMachineForceField;generateSphere(IIFI)V"
                , remap = false)
            , remap = true)
    private boolean dontCastShadow(RenderMachineForceField instance, int l, int s, float rad, int hex) {
        return !AngelicaUtilsW.isShadowPass();
    }
    
    @WrapMethod(method = {"generateSphere", "generateSphere2"})
    private void doThatDoThatThing(int l, int s, float rad, int hex, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(l, s, rad, hex);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
