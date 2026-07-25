package com.kotmatross.shaderfixer.gui;

import com.kotmatross.shaderfixer.ShaderFixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

public class GuiBSOD extends GuiScreen {
	
	public static final int BACKGROUND = 0xff0000aa;
	public static final int TEXT = 0xffffffff;
	public static final int MAIN_RECT = 0xffc0c0c0;
	public static final int MAIN_RECT_TEXT = 0xff000000;
	
	public static final String url = "https://github.com/kotmatross28729/Shader-fixer/issues/new";
	
	/// ---

	public static class BSOD_ENTRY {
		public String HEADER;
		public String[] TECH_INF;
		public String[] ERROR_CODE;
		public BSOD_ENTRY(String HEADER, String[] TECH_INF, String[] ERROR_CODE) {
			this.HEADER = HEADER;
			this.TECH_INF = TECH_INF;
			this.ERROR_CODE = ERROR_CODE;
		}
	}

	private final BSOD_ENTRY BSOD;
	
	public GuiBSOD(BSOD_ENTRY BSOD) {
		this.BSOD = BSOD;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawGradientRect(0, 0, this.width, this.height, BACKGROUND, BACKGROUND);
		
		int CX = this.width / 2;
		int CY = this.height / 2;
		
		String MAIN_TEXT = BSOD.HEADER;
		int PADDING_X = 8;
		int PADDING_Y = 2;
		
		int RECT_W = this.fontRendererObj.getStringWidth(MAIN_TEXT) + (PADDING_X * 2);
		int RECT_H = this.fontRendererObj.FONT_HEIGHT + (PADDING_Y * 2);
		
		int RECT_L = CX - (RECT_W / 2);
		int RECT_T = (int) (this.height * 0.05F);
		int RECT_R = RECT_L + RECT_W;
		int RECT_B = RECT_T + RECT_H;
		
		int TEXT_X = RECT_L + PADDING_X;
		int TEXT_Y = RECT_T + PADDING_Y;
		
		this.drawGradientRect(RECT_L, RECT_T, RECT_R, RECT_B, MAIN_RECT, MAIN_RECT);
		this.fontRendererObj.drawString(MAIN_TEXT, TEXT_X, TEXT_Y, MAIN_RECT_TEXT);
		
		int CURRENT_Y = RECT_B + 40;
		int SPACING = this.fontRendererObj.FONT_HEIGHT + 3;
		
		/// ---
		
		String LN_1 = I18n.format(BSOD.TECH_INF[0]); this.drawLineCentered(LN_1, CX, CURRENT_Y); CURRENT_Y += SPACING;
		String LN_1X = I18n.format(BSOD.TECH_INF[1]); this.drawLineCentered(LN_1X, CX, CURRENT_Y); 
		CURRENT_Y += SPACING * 4;
		
		/// ---
		
		String LN_2 = I18n.format("bsod.esc"); this.drawLineCentered(LN_2, CX, CURRENT_Y); CURRENT_Y += SPACING;
		String LN_3 = I18n.format("bsod.enter"); this.drawLineCentered(LN_3, CX, CURRENT_Y); CURRENT_Y += SPACING;
		String LN_3X = "  https://github.com/kotmatross28729/Shader-fixer/issues."; this.drawLineCentered(LN_3X, CX, CURRENT_Y); CURRENT_Y += SPACING;
		String LN_4 = I18n.format("bsod.log"); this.drawLineCentered(LN_4, CX, CURRENT_Y); 
		CURRENT_Y += SPACING * 3;
		
		/// ---
		
		String LN_5 = BSOD.ERROR_CODE[0]; this.drawLineCentered(LN_5, CX, CURRENT_Y); CURRENT_Y += SPACING;
		String LN_6 = BSOD.ERROR_CODE[1]; this.drawLineCentered(LN_6, CX, CURRENT_Y); CURRENT_Y += SPACING;
	}
	
	public void drawLineCentered(String line, int CX, int CURRENT_Y) {
		this.fontRendererObj.drawString(line, CX - (this.fontRendererObj.getStringWidth(line) / 2), CURRENT_Y, TEXT);
	}
	
	private int tickC = 0;
	private final int interv = 57;
	
	@Override
	public void initGui() {
		super.initGui();
		this.tickC = interv;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.tickC++;
		if (this.tickC >= interv) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("shaderfixer", "bsod"), 1.0F));
			this.tickC = 0;
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if(keyCode == Keyboard.KEY_NUMPADENTER || keyCode == Keyboard.KEY_RETURN) {
			openURL(url);
		}
		if(keyCode == Keyboard.KEY_ESCAPE) {
			if (this.mc.theWorld != null) 
				this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld(null);
			this.mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	public static void openURL(String url) {
		URI uri;
		try { uri = URI.create(url); }
		catch (IllegalArgumentException e) {
			ShaderFixer.logger.error("Invalid URL: {}", url, e);
			return;
		}
		if (!openURL2(uri)) openURL3(uri);
	}
	public static boolean openURL2(URI uri) {
		try {
			Class<?> AWT_DESKTOP_C = Class.forName("java.awt.Desktop");
			Object AWT_DESKTOP = AWT_DESKTOP_C.getMethod("getDesktop").invoke(null);
			AWT_DESKTOP_C.getMethod("browse", URI.class).invoke(AWT_DESKTOP, uri);
			return true;
		} catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			return false;
		}
	}
	public static boolean openURL3(URI uri) {
		try {
			Class<?> LWJGL3_DESKTOP_C = Class.forName("me.eigenraven.lwjgl3ify.redirects.Desktop");
			Object LWJGL3_DESKTOP = LWJGL3_DESKTOP_C.getMethod("getDesktop").invoke(null);
			LWJGL3_DESKTOP_C.getMethod("browse", URI.class).invoke(LWJGL3_DESKTOP, uri);
			return true;
		} catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
			return false;
		}
	}
	
}
