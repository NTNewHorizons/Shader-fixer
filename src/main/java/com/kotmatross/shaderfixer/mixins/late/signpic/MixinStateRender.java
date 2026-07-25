package com.kotmatross.shaderfixer.mixins.late.signpic;

import com.kamesuta.mc.signpic.entry.content.Content;
import com.kamesuta.mc.signpic.render.StateRender;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StateRender.class, priority = 999, remap = false)
public class MixinStateRender {
    
    @WrapMethod(method = "drawMessage")
    private static void doThatDoThatThing(Content content, FontRenderer fontrenderer, Operation<Void> original) {
        GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        try {
            original.call(content, fontrenderer);
        } finally {
            GL11.glPopAttrib();
        }
    }

}
