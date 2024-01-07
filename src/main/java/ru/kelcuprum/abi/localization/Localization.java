package ru.kelcuprum.abi.localization;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.json.JSONObject;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.info.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Localization {
    /**
     * Получение кода локализации игры который выбрал игрок<br>
     * При запуске может быть null, поэтому иногда en_us;
     */
    public static String getCodeLocalization(){
        Minecraft CLIENT = Minecraft.getInstance();
        try{
//            return CLIENT.getGame().getSelectedLanguage().getCode();
            return CLIENT.options.languageCode;
        } catch (Exception e){
            return "en_us";
        }
    }

    /**
     * Получение JSON файл локализации
     * @return JSONObject
     */
    public static JSONObject getJSONFile() throws IOException {
        Minecraft CLIENT = Minecraft.getInstance();
        File localizationFile = new File(CLIENT.gameDirectory + "/config/ActionBarInfo/lang/"+getCodeLocalization()+".json");
        if(localizationFile.exists()){
            return new JSONObject(Files.readString(localizationFile.toPath()));
        } else {
            return new JSONObject();
        }
    }
    public static String getRounding(double number){
        String text = String.format("%.3f", number);
        if(!ActionBarInfo.config.getBoolean("USE_EXTENDED_COORDINATES", false)) text = text.substring(0, text.length()-4);
        return text;
    }

    /**
     * Получение текста локализации
     * @return String
     */
    public static String getLocalization(String type, boolean parse){
        return getLocalization(type, parse, false);
    }
    public static String getLocalization(String type, boolean parse, boolean clearColor){
        String text;
        try {
            JSONObject JSONLocalization = getJSONFile();
            if(JSONLocalization.isNull(type)) text = getText("abi." + type).getString();
            else text = JSONLocalization.getString(type);
        } catch (Exception e){
            e.printStackTrace();
            text = getText("abi." + type).getString();
        }
        if(parse) return getParsedText(text, clearColor);
        else return text;
    }
    public static String getLcnDefault(String type){
        return getText("abi." + type).getString();
    }
    /**
     * Задать значение локализации на определённый текст в JSON файле
     */
    public static void setLocalization(String type, String text){
        try {
            JSONObject JSONLocalization = getJSONFile();
            JSONLocalization.put(type, text);
            Minecraft CLIENT = Minecraft.getInstance();
            File localizationFile = new File(CLIENT.gameDirectory + "/config/ActionBarInfo/lang/"+getCodeLocalization()+".json");
            Files.createDirectories(localizationFile.toPath().getParent());
            Files.writeString(localizationFile.toPath(), JSONLocalization.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Хуета которая может быть спасёт от Mojang которые сука постоянно меняют либо название класса либо еще что-то
     * @return MutableText
     */
    public static Component getText(String key){
        return Component.translatable(key);
    }

    /**
     * Перевод String в MutableText
     * @return MutableText
     */
    public static Component toText(String text){
        return Component.literal(text);
    }

    /**
     * Перевод Text в String
     * @return MutableText
     */
    public static String toString(Component text){
        return text.getString();
    }


    /**
     * Парс текста
     * @return String
     */
    public static String getParsedText(String text, boolean clearColor){
        String parsedText = StarScript.run(StarScript.compile(text));
        if(clearColor) parsedText = Utils.clearFormatCodes(parsedText);
        else parsedText = Utils.fixFormatCodes(parsedText);
        return parsedText;
    }
}
