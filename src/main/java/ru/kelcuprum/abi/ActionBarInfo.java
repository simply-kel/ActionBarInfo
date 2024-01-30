package ru.kelcuprum.abi;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.Level;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.abi.localization.StarScript;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.config.Localization;

public class ActionBarInfo implements ClientModInitializer {
    public static final String MOD_ID = "actionbarinfo";
    public static DecimalFormat DF = new DecimalFormat("#.##");
    public static final Logger LOG = LogManager.getLogger("Action Bar Info");
    private static final Timer TIMER = new Timer();
    private static String lastException;
    public static void log(String message) { log(message, Level.INFO);}
    public static void log(String message, Level level) { LOG.log(level, "[" + LOG.getName() + "] " + message); }
    public static Config config = new Config("config/ActionBarInfo/config.json");
    public static Localization localization = new Localization("abi", "config/ActionBarInfo/lang");
    @Override
    public void onInitializeClient() {
        config.load();
        StarScript.init();
        localization.setParser((s) -> StarScript.run(StarScript.compile(s)));
        KeyMapping toggleKeyBind;
        toggleKeyBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "abi.key.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
                "abi.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            while (toggleKeyBind.consumeClick()) {
                config.setBoolean("ENABLE_AB_INFORMATION", !config.getBoolean("ENABLE_AB_INFORMATION", true));
                config.save();
            }
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
            config.load();
            log("Client started!");
            start();
            HUDHandler hud = new HUDHandler();
            HudRenderCallback.EVENT.register(hud);
            ClientTickEvents.START_CLIENT_TICK.register(hud);
        }));
    }
    public static void start(){
        TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(config.getBoolean("ENABLE_AB_INFORMATION", true) && (config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue() == 0 || config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue() >5)) update();
            }
        }, 20, 20);
    }
    public static void update(){
        try{
            Minecraft client = Minecraft.getInstance();
            if(client.level == null || client.player == null) return;
            client.player.displayClientMessage(Localization.toText(
                    localization.getLocalization("info")
            ), true);

            if(lastException != null) lastException = null;
        } catch (Exception ex){
            if(lastException == null || !lastException.equals(ex.getMessage())){
                log(ex.getMessage(), Level.ERROR);
                lastException = ex.getMessage();
            }
        }
    }
}