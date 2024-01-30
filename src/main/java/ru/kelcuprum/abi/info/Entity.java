package ru.kelcuprum.abi.info;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.config.Localization;

public class Entity {
    public static boolean isTargetEntity = false;
    public static String name = "";
    public static String customName = "";
    public static float healthPercent = 0;
    public static float healthMax = 0;
    public static float health = 0;

    public static String x = "";

    public static String y = "";

    public static String z = "";

    private static void clear(){
        isTargetEntity = false;
        name = "";
        customName = "";
        healthPercent = 0;
        healthMax = 0;
        health = 0;
        x = "";
        y = "";
        z = "";
    }
    public static void update(){
        if(Minecraft.getInstance().getCameraEntity() == null){ clear(); return;}
        if(Minecraft.getInstance().player == null && Minecraft.getInstance().crosshairPickEntity == null){ clear(); return;}

        net.minecraft.world.entity.Entity entity = Minecraft.getInstance().crosshairPickEntity == null ? Minecraft.getInstance().cameraEntity : Minecraft.getInstance().crosshairPickEntity;
        if(entity instanceof LivingEntity livingEntity){
            isTargetEntity = true;
            health = livingEntity.getHealth();
            healthMax = livingEntity.getMaxHealth();
            healthPercent = livingEntity.getHealth() / livingEntity.getMaxHealth();

            x = Localization.getRounding(livingEntity.getX(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
            y = Localization.getRounding(livingEntity.getY(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));
            z = Localization.getRounding(livingEntity.getZ(), !ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false));

            name = Localization.toString(livingEntity.getName());
            customName = livingEntity.getCustomName() == null ? name : Localization.toString(livingEntity.getCustomName());
        } else {clear(); return;}
    }
}
