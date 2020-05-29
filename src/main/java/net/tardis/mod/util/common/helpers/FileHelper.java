package net.tardis.mod.util.common.helpers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.tardis.mod.common.tileentity.TileEntityEgg;

public class FileHelper {

	public static Map<UUID, String> getPlayersFromServerFile() {
		File usernameCache = FMLCommonHandler.instance().getMinecraftServerInstance().getFile("usernamecache.json");
		Map<UUID, String> usernames = new HashMap<>();
		try {
			JsonObject object = new JsonParser().parse(new FileReader(usernameCache)).getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
			for (Map.Entry<String, JsonElement> elem : entrySet) {
				usernames.put(UUID.fromString(elem.getKey()), object.get(elem.getKey()).getAsString());
			}
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		return usernames;
	}

	public static void readOrWriteARS(File dir) {
		try {
			dir = new File(dir.getPath() + File.separator + "tardis_ars.json");
			if(!dir.exists()) {
				dir.createNewFile();
				JsonWriter writer = new GsonBuilder().setPrettyPrinting().create().newJsonWriter(new FileWriter(dir));
				writer.beginArray();
				writer.value("tardis:hellbent_door");
				writer.value("tardis:hellbent_corridor");
				writer.value("tardis:hellbent_floor");
				writer.value("tardis:hellbent_roundel01");
				writer.value("tardis:hellbent_roundel02");
				writer.value("tardis:hellbent_roundel03");
				writer.value("tardis:hellbent_glass01");
				writer.value("tardis:hellbent_glass02");
				writer.value("tardis:hellbent_glass03");
				writer.value("tardis:hellbent_glass04");
				writer.value("tardis:hellbent_glass05");
				writer.value("tardis:hellbent_glass06");
				writer.value("tardis:hellbent_glass07");
				writer.value("tardis:hellbent_glass08");
				writer.value("tardis:hellbent_silverwall");
				writer.value("tardis:hellbent_vents");
				writer.value("tardis:hellbent_wall");
				writer.value("tardis:hellbent_light");
				writer.value("tardis:hellbent_monitor");
				writer.value("tardis:hellbent_pole");
				writer.value("tardis:hellbent_roof");
				writer.value("tardis:food_machine");
				writer.value("minecraft:air");
				writer.value("minecraft:air");

				writer.value("tardis:s13roundellit1");
				writer.value("tardis:s13roundellit2");
				writer.value("tardis:s13roundellit3");
				writer.value("tardis:s13blankroundel1");
				writer.value("tardis:s13blankroundel2");
				writer.value("tardis:s13blankroundel3");
				writer.value("tardis:s13floor");
				writer.value("tardis:s13flooralt");
				writer.value("tardis:zero_room");
				writer.value("tardis:zero_room_slab");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");
				writer.value("minecraft:air");

				writer.value("tardis:toyota_hexagon_1");
				writer.value("tardis:toyota_hexagon_2");
				writer.value("tardis:toyota_hexagon_3");
				writer.value("tardis:toyota_hexagon_4");
				writer.value("tardis:toyota_hexalight_off_1");
				writer.value("tardis:toyota_hexalight_off_2");
				writer.value("tardis:toyota_hexalight_off_3");
				writer.value("tardis:toyota_hexalight_off_4");
				writer.value("tardis:toyota_wallroundel_1");
				writer.value("tardis:toyota_wallroundel_2");
				writer.value("tardis:toyota_wallroundel_3");
				writer.value("tardis:toyota_wallroundel_4");
				writer.value("tardis:toyota_wallroundel_5");
				writer.value("tardis:toyota_wallroundel_6");
				writer.value("tardis:toyota_light_off");
				writer.value("tardis:toyota_upper_divider");
				writer.value("tardis:toyota_roof_light_off");
				writer.value("tardis:toyota_roof");
				writer.value("tardis:toyota_wall");
				writer.value("tardis:toyota_platform");
				writer.value("tardis:toyota_platform_top");
				writer.value("tardis:toyota_platform_light");
				writer.value("tardis:toyota_spin");
				writer.value("minecraft:air");

				writer.value("tardis:am_sphere");
				writer.value("tardis:electric_panel");
				writer.value("tardis:br_chair");
				writer.endArray();
				writer.close();
			}
				
			JsonReader reader = new GsonBuilder().create().newJsonReader(new FileReader(dir));
			reader.beginArray();
			while(reader.hasNext()) {
				ItemStack stack = new ItemStack(Item.getByNameOrId(reader.nextString()), 1);
				TileEntityEgg.register(stack);
			}
			reader.endArray();
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
