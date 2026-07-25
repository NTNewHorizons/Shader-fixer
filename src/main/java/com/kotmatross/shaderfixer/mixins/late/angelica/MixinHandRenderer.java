package com.kotmatross.shaderfixer.mixins.late.angelica;

import net.coderbot.iris.pipeline.HandRenderer;
import net.minecraftforge.client.IItemRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.kotmatross.shaderfixer.utils.ntm.NTMUtilsW;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(value = HandRenderer.class, priority = 999, remap = false)
public class MixinHandRenderer {

    @Inject(method = "setupGlState"
            , at = @At(value = "HEAD"))
    public void handleInterp(CallbackInfo ci, @Local(argsOnly = true) float tickDelta) {
        if (NTMUtilsW.checkVibe(IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) 
            NTMUtilsW.handleInterpolation(tickDelta);
    }

    @ModifyArg(method = "setupGlState"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/EntityRenderer;getFOVModifier(FZ)F"
                , remap = true
            , ordinal = 0)
            , index = 1)
    private boolean applyFovCFG(boolean useFOVSetting) {
        if (NTMUtilsW.checkVibe(IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) 
            return NTMUtilsW.getFOVConf();
        return false;
    }

}
