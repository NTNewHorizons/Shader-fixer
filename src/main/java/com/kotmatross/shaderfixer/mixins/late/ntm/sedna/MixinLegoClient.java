package com.kotmatross.shaderfixer.mixins.late.ntm.sedna;

import net.minecraft.client.renderer.Tessellator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.hbm.items.weapon.sedna.factory.LegoClient;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(value = LegoClient.class, priority = 999, remap = false)
public class MixinLegoClient {

    @WrapMethod(method = "renderBulletStandard(Lnet/minecraft/client/renderer/Tessellator;IIDDDZ)V")
    private static void doThatDoThatThing(Tessellator tess, int dark, int light, double length, double widthF, double widthB, boolean fullbright,
                                          Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            boolean shouldDoTheThing = fullbright || AngelicaUtilsW.isShaderEnabled();
            if (shouldDoTheThing) ShaderUtils.enableFullBrightness();
            try {
                original.call(tess, dark, light, length, widthF, widthB, fullbright);
            } finally {
                if (shouldDoTheThing) ShaderUtils.disableFullBrightness();
            }
        }
    }
    
    @Inject(method = "drawLineSegment"
            , at = @At(value = "HEAD"))
    private static void drawLineSegment(CallbackInfo ci, @Local(argsOnly = true) Tessellator tessellator) {
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
    }

}
