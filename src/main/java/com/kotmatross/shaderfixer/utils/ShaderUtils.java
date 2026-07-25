package com.kotmatross.shaderfixer.utils;

import com.kotmatross.shaderfixer.Tags;
import com.kotmatross.shaderfixer.gui.GuiBSOD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@SideOnly(Side.CLIENT)
public class ShaderUtils {

    public static final ResourceLocation shader_fix = new ResourceLocation(
        Tags.MODID,
        "textures/shaders_workaround.png");

    public static void fix() {
        Minecraft.getMinecraft().renderEngine.bindTexture(shader_fix);
    }
    
    public static int getCurrentTextureID() {
        return GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
    }

    public static void bindTextureByID(int textureID) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public enum BrightnessState {
        NULL,
        ENABLED,
        DISABLED,
        
        ;
        
        public boolean isNull() {return this == BrightnessState.NULL;}
        public boolean isOn() {return this == BrightnessState.ENABLED;}
        public boolean isOff() {return this == BrightnessState.DISABLED;}
        
        public boolean isInvalid(BrightnessState newState) {
            return (newState.isOn() && this.isOn()) ||
                    (newState.isOff() && (this.isOff() || this.isNull()));
        }
        
    }
    
    public static float lbx;
    public static float lby;
    public static boolean killSwitch = false;
    private static BrightnessState lastState = BrightnessState.NULL;
    
    public static void enableFullBrightness() {
        if(shouldAnnihilateItself(BrightnessState.ENABLED)) return;
        lbx = OpenGlHelper.lastBrightnessX;
        lby = OpenGlHelper.lastBrightnessY;
        /// In case called before entity render
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        lastState = BrightnessState.ENABLED;
    }

    public static void disableFullBrightness() {
        if(shouldAnnihilateItself(BrightnessState.DISABLED)) return;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
        GL11.glPopAttrib();
        lastState = BrightnessState.DISABLED;
    }
    
    public static final String ERROR_CODE_ShaderUtils = "53 68 61 64 65 72 55 74 69 6c 73 23";
    public static final String ERROR_CODE_enableFullBrightness = "65 6e 61 62 6c 65 46 75 6c 6c 42 72 69 67 68 74 6e 65 73 73";
    public static final String ERROR_CODE_disableFullBrightness = "64 69 73 61 62 6c 65 46 75 6c 6c 42 72 69 67 68 74 6e 65 73 73";
    
    public static GuiBSOD.BSOD_ENTRY BSOD_BRIGHTNESS =
            new GuiBSOD.BSOD_ENTRY(
                    "ShaderFixer"
                    , new String[]{"bsod.invalid", "bsod.crash.attrib"}
                    , new String[]{ERROR_CODE_ShaderUtils, ERROR_CODE_enableFullBrightness}
            );
    
    private static boolean shouldAnnihilateItself(BrightnessState state) {
        if(killSwitch) return true;
        if (lastState.isInvalid(state)) {
            boolean enabled = lastState.isOn();
            if(enabled) {
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbx, lby);
                GL11.glPopAttrib();
            }
            BSOD_BRIGHTNESS.ERROR_CODE[1] = state.isOn() ? ERROR_CODE_enableFullBrightness : ERROR_CODE_disableFullBrightness;
            logFuckedUpState(enabled ? "ShaderUtils#enableFullBrightness" : "ShaderUtils#disableFullBrightness");
            killSwitch = true;
            return true;
        }
        return false;
    }
    
    private static boolean didLog = false;
    
    private static void logFuckedUpState(String method) {
        if (!didLog) {
			//noinspection CallToPrintStackTrace
			new IllegalStateException("[ShaderFixer] ::: CRASH HERE ::: " + method).printStackTrace();
            didLog = true;
        }
    }
    
    public static int getCurrentProgram() {
        return GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
    }

    public static void useDefaultProgram() {
        GL20.glUseProgram(0);
    }

    public static void useProgram(int program) {
        GL20.glUseProgram(program);
    }

}
