package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.render.entity.projectile.RenderSpellWhip;
import com.fiskmods.heroes.common.entity.projectile.EntitySpellWhip;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RenderSpellWhip.class, priority = 999, remap = false)
public abstract class MixinRenderSpellWhip extends Render {
    
    @WrapMethod(method = "doRender(Lcom/fiskmods/heroes/common/entity/projectile/EntitySpellWhip;DDDFF)V")
    private void doThatDoThatThing(EntitySpellWhip entity, double x, double y, double z, float entityYaw, float partialTicks, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(entity, x, y, z, entityYaw, partialTicks);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
