package ru.kelcuprum.abi;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.info.Entity;
import ru.kelcuprum.abi.info.block.NoteBlock;
import ru.kelcuprum.abi.localization.Localization;

import java.util.List;

public class HUDHandler implements HudRenderCallback, ClientTickEvents.StartTick {
    private final List<Component> textList = new ObjectArrayList<>();

    private final Minecraft client = Minecraft.getInstance();

    @Override
    public void onStartTick(Minecraft client) {
        NoteBlock.update();
        Entity.update();
        this.textList.clear();
        if (!ActionBarInfo.config.getBoolean("ENABLE_AB_INFORMATION", true)) return;
        if(ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue()  < 1 || ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue() > 5) return;
        String[] args = Localization.getLocalization("info", true).split("\\\\n");
        for (String arg : args) {
            this.textList.add(Localization.toText(arg));
        }
    }
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        if (ActionBarInfo.config.getBoolean("ENABLE_AB_INFORMATION", true)) {
            int l = 0;
            int x;
            int y;
            for (Component text : this.textList) {
                switch (ActionBarInfo.config.getNumber("TYPE_RENDER_ACTION_BAR", 0).intValue()) {
                    case 1 -> {
                        x = (this.client.getWindow().getGuiScaledWidth() / 2);
                        y = this.client.getWindow().getGuiScaledHeight() + this.client.font.lineHeight - (((textList.size()-l)*this.client.font.lineHeight)-2) - 85;
                        this.drawString(drawContext, text, x, y, true);
                    }
                    case 2 -> {
                        x = ActionBarInfo.config.getNumber("INDENT_X", 20).intValue();
                        y = this.client.getWindow().getGuiScaledHeight() - (((textList.size()-l)*this.client.font.lineHeight)-2) - this.client.font.lineHeight - ActionBarInfo.config.getNumber("INDENT_Y", 20).intValue();
                        this.drawString(drawContext, text, x, y);
                    }
                    case 3 -> {
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - ActionBarInfo.config.getNumber("INDENT_X", 20).intValue();
                        y = this.client.getWindow().getGuiScaledHeight() - (((textList.size()-l)*this.client.font.lineHeight)-2) - ActionBarInfo.config.getNumber("INDENT_Y", 20).intValue();
                        this.drawString(drawContext, text, x, y);
                    }
                    case 4 -> {
                        x = ActionBarInfo.config.getNumber("INDENT_X", 20).intValue();
                        y = ActionBarInfo.config.getNumber("INDENT_Y", 20).intValue() + ((l*this.client.font.lineHeight)+2);
                        this.drawString(drawContext, text, x, y);
                    }
                    case 5 -> {
                        x = this.client.getWindow().getGuiScaledWidth() - this.client.font.width(text) - ActionBarInfo.config.getNumber("INDENT_X", 20).intValue();
                        y = ActionBarInfo.config.getNumber("INDENT_Y", 20).intValue() + ((l*this.client.font.lineHeight)+2);
                        this.drawString(drawContext, text, x, y);
                    }
                }
                l++;
            }
        }
    }

    private void drawString(GuiGraphics guiGraphics, Component text, int x, int y) {
        this.drawString(guiGraphics, text, x, y, false);
    }

    private void drawString(GuiGraphics guiGraphics, Component text, int x, int y, boolean isCenter) {
        if(isCenter) guiGraphics.drawCenteredString(this.client.font, text, x, y, 16777215);
        else guiGraphics.drawString(this.client.font, text, x, y, 16777215);
    }
}
