package net.tardis.mod.util;

public class ModTriggers {
    public static final CustomTrigger MAKE_1_ROOM = new CustomTrigger("make_1_room");
    public static final CustomTrigger MAKE_100_ROOMS = new CustomTrigger("make_100_rooms");
    public static final CustomTrigger IM_HELPING = new CustomTrigger("sonic_imhelping");
    public static final CustomTrigger LOCKDOWN = new CustomTrigger("sonic_lockdown");
    public static final CustomTrigger YOU_REDECORATED = new CustomTrigger("you_redecorated");

    /*
     * This array just makes it convenient to register all the criteria.
     */
    public static final CustomTrigger[] TRIGGER_ARRAY = new CustomTrigger[] {
            MAKE_1_ROOM,
            MAKE_100_ROOMS,
            IM_HELPING,
            LOCKDOWN,
            YOU_REDECORATED
            };
}