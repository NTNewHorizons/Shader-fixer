package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.hbm.render.util.RenderSparks;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RenderSparks.class, priority = 999, remap = false)
public class MixinRenderSparks {

    @WrapMethod(method = "renderSpark")
    private static void doThatDoThatThing(int seed, double x, double y, double z, float length, int min, int max, int color1, int color2, Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(seed, x, y, z, length, min, max, color1, color2);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }

}
