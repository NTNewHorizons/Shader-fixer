package com.kotmatross.shaderfixer.handler;

import com.kotmatross.shaderfixer.gui.GuiBSOD;
import com.kotmatross.shaderfixer.utils.ShaderUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class EventHandlerClientShaderFixer {
	
	@SubscribeEvent
	public void onClientTickEvent(TickEvent.ClientTickEvent event) {
		if(ShaderUtils.killSwitch) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.theWorld != null && !(mc.currentScreen instanceof GuiBSOD) && !(mc.currentScreen instanceof GuiMainMenu)) {
				mc.displayGuiScreen(new GuiBSOD(ShaderUtils.BSOD_BRIGHTNESS));
			}
		}
	}
	
}
