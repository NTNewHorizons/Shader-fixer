package com.kotmatross.shaderfixer.mixins.late.ntm;

import net.minecraft.util.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.hbm.render.util.BeamPronter;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(value = BeamPronter.class, priority = 999, remap = false)
public class MixinBeamPronter {

    @Unique
    private static final String prontBeamDesc = "prontBeam(Lnet/minecraft/util/Vec3;Lcom/hbm/render/util/BeamPronter$EnumWaveType;Lcom/hbm/render/util/BeamPronter$EnumBeamType;IIIIFIF)V";

    @WrapMethod(method = prontBeamDesc)
    private static void doThatDoThatThing(Vec3 skeleton, BeamPronter.EnumWaveType wave, BeamPronter.EnumBeamType beam,
                                          int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness,
                                          Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(skeleton, wave, beam, outerColor, innerColor, start, segments, size, layers, thickness);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }
    
}
