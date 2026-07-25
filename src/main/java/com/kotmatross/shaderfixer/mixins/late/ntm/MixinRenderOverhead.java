package com.kotmatross.shaderfixer.mixins.late.ntm;

import com.hbm.render.util.RenderOverhead;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RenderOverhead.class, priority = 999, remap = false)
public class MixinRenderOverhead {
    
	@Unique
    @SuppressWarnings("AddedMixinMembersNamePattern")
    private static final String drawTagDesc = "drawTag(FDLjava/lang/String;DDDIZII)V";

    @WrapWithCondition(method = drawTagDesc
            , at = @At(value = "INVOKE"
                , target = "Lorg/lwjgl/opengl/GL11;glNormal3f(FFF)V"))
    private static boolean disableNormalsSetup(float nx, float ny, float nz) {
        // Peak mjoang coding v2
        return false;
    }

    @WrapMethod(method = drawTagDesc)
    private static void doThatDoThatThing(float offset, double distsq, String name, double x, double y, double z, int dist, boolean depthTest, int color, int shadowColor, Operation<Void> original) {
        if (distsq <= (double)(dist * dist)) {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        ShaderUtils.enableFullBrightness();
            try {
                original.call(offset, distsq, name, x, y, z, dist, depthTest, color, shadowColor);
            } finally {
                ShaderUtils.disableFullBrightness();
                GL11.glPopAttrib();
            }
        }
    }

}
