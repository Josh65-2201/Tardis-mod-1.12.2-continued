package net.tardis.mod.common.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.tardis.mod.Tardis;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class TSounds {

	private static ArrayList<SoundEvent> sounds = new ArrayList<SoundEvent>();

	public static SoundEvent takeoff = register("takeoff");
	public static SoundEvent dalek_ray = register("dalek_ray");
	public static SoundEvent dalek = register("dalek");
	public static SoundEvent force_field = register("force_field");
	public static SoundEvent door_locked = register("locked");
	public static SoundEvent door_open = register("door_open");
	public static SoundEvent door_closed = register("door_closed");
	public static SoundEvent interior_hum_toyota = register("interior_hum_toyota");
	public static SoundEvent interior_hum_coral = register("interior_hum_coral");
	public static SoundEvent interior_hum_copper = register("interior_hum_copper");
	public static SoundEvent interior_hum_sharp = register("interior_hum_sharp");
	public static SoundEvent interior_hum_brachacki = register("interior_hum_brachacki");
	public static SoundEvent interior_hum_kelt = register("interior_hum_kelt");
	public static SoundEvent cloister_bell = register("cloister_bell");
	public static SoundEvent drum_beat = register("drum_beat");
	public static SoundEvent engine_stutter = register("stutter");
	public static SoundEvent phone = register("phone");
	public static SoundEvent knocking = register("knocking");
	public static SoundEvent vmSound = register("vm_teleport");
	public static SoundEvent flyLoop = register("fly_loop");
	public static SoundEvent bessieHorn = register("bessie_horn");
	public static SoundEvent repair_kit = register("repair_kit");
	public static SoundEvent control_generic_01 = register("control_generic_01");
	public static SoundEvent control_generic_02 = register("control_generic_02");
	public static SoundEvent control_generic_03 = register("control_generic_03");
	public static SoundEvent control_stabilizers_toggle = register("control_stabilizers_toggle");
	public static SoundEvent control_dimension = register("control_dimension");
	public static SoundEvent control_fast_return = register("control_fast_return");
	public static SoundEvent control_landing_type = register("control_landing_type");
	public static SoundEvent control_refuel_start = register("control_refuel_start");
	public static SoundEvent control_refuel_stop = register("control_refuel_stop");
	public static SoundEvent control_telepathic_circuit = register("control_telepathic_circuit");
	public static SoundEvent control_direction = register("control_direction");
	public static SoundEvent sonic_generic = register("sonic_generic");
	public static SoundEvent sonic_silver = register("sonic_silver");
	public static SoundEvent remote_accept = register("remote_accept");
	public static SoundEvent INTERIOR_DOOR_1963 = register("1963_int_door");
	public static SoundEvent FOOD_MACHINE = register("1963_food_machine");
	public static SoundEvent tardis_land = register("tardis_land");
	public static SoundEvent tardis_no_fuel = register("tardis_no_fuel");

	public static SoundEvent register(String name) {
		ResourceLocation rl = new ResourceLocation(Tardis.MODID, name);
		SoundEvent event = new SoundEvent(rl);
		event.setRegistryName(rl);
		sounds.add(event);
		return event;
	}

	@SubscribeEvent
	public static void regSounds(RegistryEvent.Register<SoundEvent> e) {
		for (SoundEvent sound : sounds) {
			e.getRegistry().register(sound);
		}
	}

}