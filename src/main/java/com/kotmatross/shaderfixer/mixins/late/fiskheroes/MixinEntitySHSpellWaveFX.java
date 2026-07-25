package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.particle.EntitySHSpellWaveFX;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = EntitySHSpellWaveFX.class, priority = 999)
public class MixinEntitySHSpellWaveFX {

    @ModifyArg(method = "renderParticle"
            , at = @At(value = "INVOKE"
                , target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_F(FFFF)V")
            , index = 3)
    private float alphaFix(float alpha, @Local(name = "opacity") float opacity) {
        return alpha == 0 ? opacity : alpha * 3;
    }

}
