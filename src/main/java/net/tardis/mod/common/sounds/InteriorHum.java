package net.tardis.mod.common.sounds;

import java.util.Arrays;
import java.util.List;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.tardis.mod.Tardis;

public class InteriorHum {

	public static InteriorHum TOYOTA = new InteriorHum(TSounds.interior_hum_toyota,580, "toyota");
	public static InteriorHum CORAL = new InteriorHum(TSounds.interior_hum_coral,1060, "coral");
	public static InteriorHum COPPER = new InteriorHum(TSounds.interior_hum_copper,620, "copper");
	public static InteriorHum SHARP = new InteriorHum(TSounds.interior_hum_sharp, 620, "sharp");
	public static InteriorHum BRACHACKI = new InteriorHum(TSounds.interior_hum_brachacki, 600, "brachacki");
	public static InteriorHum KELT = new InteriorHum(TSounds.interior_hum_kelt, 50, "kelt");
	public static InteriorHum DISABLED = new InteriorHum(null, 20, "disabled");

	public static List<InteriorHum> hums = Arrays.asList(DISABLED, KELT, BRACHACKI, SHARP, COPPER, CORAL, TOYOTA);

	private SoundEvent event;
	private int ticks;
	private String name;

	public InteriorHum(SoundEvent event, int ticks, String name) {
		this.event = event;
		this.ticks = ticks;
		this.name = name;
	}

	public SoundEvent getSoundEvent() {
		return this.event;
	}

	public int getTicks() {
		return ticks;
	}
	
	public String getTranslatedName() {
		return new TextComponentTranslation("hum." + Tardis.MODID + "." + name).getFormattedText();
	}
}
