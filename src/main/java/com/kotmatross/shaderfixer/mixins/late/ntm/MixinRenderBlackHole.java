package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.Tessellator;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.hbm.render.entity.effect.RenderBlackHole;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(value = RenderBlackHole.class, priority = 999, remap = false)
public class MixinRenderBlackHole {
    
    
    @WrapMethod(method = "renderDisc")
    private void doThatDoThatDisc(Entity entity, float interp, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(entity, interp);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }
    
    @Inject(method = "renderDisc"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;startDrawingQuads()V"
                , shift = At.Shift.AFTER
                , remap = true))
    public void fixBrightnessDisk(CallbackInfo ci, @Local(name = "tess") Tessellator tess) {
        tess.setNormal(0.0F, 1.0F, 0.0F);
    }
    
    @WrapMethod(method = "renderSwirl")
    private void doThatDoThatSwirl(Entity entity, float interp, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(entity, interp);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }
    
    // Fix for angelica (brightness)
    @Inject(method = "renderSwirl"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;startDrawingQuads()V"
                , shift = At.Shift.AFTER))
    public void fixBrightnessSwirl(CallbackInfo ci, @Local(name = "tess") Tessellator tess) {
        tess.setNormal(0.0F, 1.0F, 0.0F);
    }

    @ModifyArg(method = "renderJets"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_F(FFFF)V")
            , index = 3)
    private float alphaFix(float alpha) {
        return alpha == 0 ? 0.01F : alpha * 2F;
    }
    
    @WrapMethod(method = "renderJets")
    private void doThatDoThatJet(Entity entity, float interp, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(entity, interp);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }

}
