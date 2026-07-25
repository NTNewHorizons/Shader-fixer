package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.hbm.particle.ParticleAmatFlash;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(value = ParticleAmatFlash.class, priority = 999)
public class MixinParticleAmatFlash {

    @ModifyArg(method = "renderParticle"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_F(FFFF)V")
            , index = 3)
    private float alphaFix(float alpha, @Local(name = "inverse") double inverse) {
        return alpha == 0 ? (float) inverse / 2F : alpha * 10F;
    }
    
    @WrapMethod(method = "renderParticle")
    private void doThatDoThatThing(Tessellator tess, float interp, float x, float y, float z, float tx, float tz, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(tess, interp, x, y, z, tx, tz);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }

}
