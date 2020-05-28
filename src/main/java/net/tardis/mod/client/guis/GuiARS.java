package net.tardis.mod.client.guis;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.tardis.mod.Tardis;
import net.tardis.mod.client.guis.elements.ButtonRecipe;
import net.tardis.mod.common.tileentity.TileEntityEgg;
import net.tardis.mod.common.tileentity.TileEntityTardis;
import net.tardis.mod.network.NetworkHandler;
import net.tardis.mod.network.packets.MessageARSSpawn;
import net.tardis.mod.util.common.helpers.PlayerHelper;
import net.minecraft.entity.player.EntityPlayer;

public class GuiARS extends GuiScreen {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Tardis.MODID, "textures/gui/ars_tree.png");
	private TileEntityTardis tardis;

	private static int guiWidth = 242;
	private static int guiHeight = 244;
	
	public GuiARS(TileEntityTardis tardis) {
		this.tardis = tardis;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		GuiARS.drawBackground(this, width, height);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		final int guiFirstX = width / 2 - GuiARS.getGuiWidth() / 2 + 13;
		final int guiFirstY = height / 2 - GuiARS.getGuiHeight() / 2 + 10;
		final int maxItem = 12;
		final int maxLine = 12;
		int id = 0;
		int line = 0;
		int collum = 0;
		int page = 0;
		for(ItemStack stack : TileEntityEgg.ITEMS) {
			this.addButton(new ButtonRecipe(id, guiFirstX + collum * 18, guiFirstY + line  * 18 , stack)).addAction(() -> {
				NetworkHandler.NETWORK.sendToServer(new MessageARSSpawn(tardis.getPos(), stack));
				Minecraft.getMinecraft().displayGuiScreen(null);
			});
			++id;
			collum++;
			if (collum >= maxItem){
				collum = 0;
				line++;
			}
			if (line >= maxLine){
				line = 0;
				page++;
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button instanceof ButtonRecipe) {
			((ButtonRecipe)button).doAction();
		}
	}

	public static void drawBackground(GuiScreen screen, int width, int height) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		screen.drawTexturedModalRect(width / 2 - 242 / 2, height / 2 - 128, 0, 0, guiWidth, guiHeight);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public static int getGuiWidth() {
		return guiWidth;
	}

	public static int getGuiHeight() {
		return guiHeight;
	}
}
