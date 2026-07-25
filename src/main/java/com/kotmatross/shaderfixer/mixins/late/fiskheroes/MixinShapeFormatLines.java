package com.kotmatross.shaderfixer.mixins.late.fiskheroes;

import com.fiskmods.heroes.client.pack.json.shape.IShapeFormat;
import com.fiskmods.heroes.client.pack.json.shape.JsonShape;
import com.fiskmods.heroes.client.pack.json.shape.ShapeFormatLines;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ShapeFormatLines.class, priority = 999, remap = false)
public abstract class MixinShapeFormatLines implements IShapeFormat {
    
    @WrapMethod(method = "render")
    private void doThatDoThatThing(JsonShape shape, Entity entity, float mult, float ticks, Operation<Void> original) {
        ShaderUtils.enableFullBrightness();
        try {
            original.call(shape, entity, mult, ticks);
        } finally {
            ShaderUtils.disableFullBrightness();
        }
    }

}
