package com.kotmatross.shaderfixer.mixins.late.avaritia;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kotmatross.shaderfixer.utils.ShaderUtils;

import fox.spiteful.avaritia.render.CosmicRenderShenanigans;

@Mixin(value = CosmicRenderShenanigans.class, priority = 999, remap = false)
public class MixinCosmicRenderShenanigans {

    @Unique
    private static int sf$previousProgram;

    @Inject(method = "useShader"
            , at = @At(value = "HEAD"))
    private static void useShader(CallbackInfo ci) {
        sf$previousProgram = ShaderUtils.getCurrentProgram();
    }

    @Inject(method = "releaseShader"
            , at = @At(value = "TAIL"))
    private static void releaseShader(CallbackInfo ci) {
        ShaderUtils.useProgram(sf$previousProgram);
    }

}
