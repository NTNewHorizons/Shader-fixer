package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.render.effect.Effect;
import com.fiskmods.heroes.client.render.effect.EffectTentacles;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EffectTentacles.class, priority = 999, remap = false)
public abstract class MixinEffectTentacles implements Effect {
    
    @WrapMethod(method = "doRender")
    private void doThatDoThatThing(Entry e, EntityLivingBase anchor, boolean isClientPlayer, boolean isFirstPerson, float partialTicks, Operation<Void> original) {
        GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT);
        if (!GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK)) GL11.glDepthMask(true);
        try {
            original.call(e, anchor, isClientPlayer, isFirstPerson, partialTicks);
        } finally {
            GL11.glPopAttrib();
        }
    }
    
}
