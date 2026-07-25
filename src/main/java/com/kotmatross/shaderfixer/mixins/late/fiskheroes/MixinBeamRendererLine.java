package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.pack.json.beam.BeamRendererLine;
import com.fiskmods.heroes.client.pack.json.beam.IBeamRenderer;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BeamRendererLine.class, priority = 999, remap = false)
public abstract class MixinBeamRendererLine implements IBeamRenderer {
    
    @WrapMethod(method = "render")
    private void doThatDoThatThing(Entity anchor, float width, float height, float beamScale, Long seed, Vec3 src, Vec3 dst, Vec3 color, float opacity0, float opacity1, float scale0, float scale1, float time, float scale, boolean isClientPlayer, boolean isFirstPerson, float partialTicks, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(anchor, width, height, beamScale, seed, src, dst, color, opacity0, opacity1, scale0, scale1, time, scale, isClientPlayer, isFirstPerson, partialTicks);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
