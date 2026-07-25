package com.kotmatross.shaderfixer.mixins.late.avaritia;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;

import com.kotmatross.shaderfixer.utils.ShaderUtils;

import fox.spiteful.avaritia.render.RenderHeavenArrow;

@Mixin(value = RenderHeavenArrow.class, priority = 999)
public class MixinRenderHeavenArrow {
    
    @WrapMethod(method = "doRender")
    private static void doThatDoThatThing(EntityArrow p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
