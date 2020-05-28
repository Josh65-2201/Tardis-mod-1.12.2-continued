package net.tardis.mod.client.guis;

import java.io.IOException;
import java.text.DecimalFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.tardis.mod.client.guis.elements.ButtonMonitor;
import net.tardis.mod.common.systems.TardisSystems;
import net.tardis.mod.common.systems.TardisSystems.BaseSystem;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.network.NetworkHandler;
import net.tardis.mod.network.packets.MessageDamageSystem;

public class GuiSecurity extends GuiScreen{

	private TileEntityTardis tardis;
	
	public GuiSecurity(TileEntityTardis tardis) {
		this.tardis = tardis;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GuiMonitor.drawMonitorBackground(this, width, height);
		this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Security protocols", width / 2, height / 2 - 55, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button instanceof ButtonMonitor)
			((ButtonMonitor)button).doAction();
	}

	@Override
	public void initGui() {
		super.initGui();
		this.addButton(addButton(4, "> Dead lock")).addAction(() -> {
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
		this.addButton(addButton(3, "> Alarm")).addAction(() -> {
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
		this.addButton(addButton(2, "> Escape")).addAction(() -> {
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
		this.addButton(addButton(1, "> Forcefiled")).addAction(() -> {
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
		this.addButton(addButton(0, "> Stealth")).addAction(() -> {
			Minecraft.getMinecraft().displayGuiScreen(null);
		});
	}
	
	public ButtonMonitor addButton(int id, String name) {
		return new ButtonMonitor(id, width / 2 - 110, (height / 2 + 50) - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * id, name);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}	
}