package com.kotmatross.shaderfixer.mixins.late.ntm.space;

import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.hbm.render.util.BeamPronter;
import com.kotmatross.shaderfixer.shrimp.SPEKJORK;
import com.kotmatross.shaderfixer.utils.angelica.AngelicaUtilsW;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@SPEKJORK
@Mixin(value = BeamPronter.class, priority = 999, remap = false)
public class MixinBeamPronter {

    @Unique
    private static final String prontBeamDescSpace = "prontBeam(Lnet/minecraft/util/Vec3;Lcom/hbm/render/util/BeamPronter$EnumWaveType;Lcom/hbm/render/util/BeamPronter$EnumBeamType;IIIIFIFF)V";

    @WrapMethod(method = prontBeamDescSpace)
    private static void doThatDoThatThing(Vec3 skeleton, BeamPronter.EnumWaveType wave, BeamPronter.EnumBeamType beam,
        int outerColor, int innerColor, int start, int segments, float size, int layers, float thickness, float alpha,
        Operation<Void> original) {
        if (!AngelicaUtilsW.isShadowPass()) {
            ShaderUtils.enableFullBrightness();
            try {
                original.call(skeleton, wave, beam,
                        outerColor, innerColor, start, segments, size, layers, thickness, alpha);
            } finally {
                ShaderUtils.disableFullBrightness();
            }
        }
    }
    
    // Fix alpha != 1
    // For some reason, James put 256 (and 0.5) as the alpha value
    @Redirect(method = "setColorWithAlpha"
            , at = @At(value = "INVOKE"
                    , target = "org/lwjgl/opengl/GL11.glColor4f(FFFF)V"))
    private static void transformGLColor(float r, float g, float b, float a) {
        GL11.glColor4f(r, g, b, 1F);
    }

}
