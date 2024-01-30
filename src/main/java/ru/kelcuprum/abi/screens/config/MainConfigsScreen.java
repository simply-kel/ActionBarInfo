package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonConfigBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxLocalization;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorIntegerButton;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderConfigInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;

public class MainConfigsScreen {
    private static final Component TITLE = Localization.getText("abi.name");
    // CATEGORYES
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    // CATEGORY CONTENT
    private static final Component enableABIText = Localization.getText("abi.config.enable_ab_information");
    private static final Component viewItemOffHandText = Localization.getText("abi.config.view_item_off_hand");
    private static final Component useExtendedCoordinatesText = Localization.getText("abi.config.use_extended_coordinates");
    private static final Component typeRenderABIText = Localization.getText("abi.config.type_render_action_bar");
    private static final Component indentYText = Localization.getText("abi.config.intend_y");
    private static final Component indentXText = Localization.getText("abi.config.intend_x");
    private static final Component renderInDebugScreenText = Localization.getText("abi.config.render_in_debug_screen");
    private static final InterfaceUtils.DesignType designType = InterfaceUtils.DesignType.FLAT;
    //
    public Screen build(Screen parent){
        String[] types = {
                "Default",
                "ABI Render",
                "Bottom left",
                "Bottom right",
                "Top left",
                "Top right"
        };
        return new ConfigScreenBuilder(parent, Component.translatable("abi.name"), designType)
                .addPanelWidget(new Button(10, 40, 110, 20, designType, MainConfigCategory, (OnPress) -> {
                    Minecraft.getInstance().setScreen(new MainConfigsScreen().build(parent));
                }))
                .addPanelWidget(new Button(10, 65, 110, 20, designType, LocalizationConfigCategory, (OnPress) -> {
                    Minecraft.getInstance().setScreen(new LocalizationConfigsScreen().build(parent));
                }))
                .addWidget(new TextBox(140, 15, MainConfigCategory, true))
                .addWidget(new ButtonConfigBoolean(140, 30, designType, ActionBarInfo.config, "ENABLE_AB_INFORMATION", true, enableABIText))
                .addWidget(new ButtonConfigBoolean(140, 55, designType, ActionBarInfo.config, "VIEW_ITEM_OFF_HAND", false, viewItemOffHandText))
                .addWidget(new ButtonConfigBoolean(140, 80, designType, ActionBarInfo.config, "USE_EXTENDED_COORDINATES", false, useExtendedCoordinatesText))
                .addWidget(new SelectorIntegerButton(140, 105, designType, types, ActionBarInfo.config, "TYPE_RENDER_ACTION_BAR", 0, typeRenderABIText))
                .addWidget(new SliderConfigInteger(140, 130, designType, ActionBarInfo.config, "INDENT_X", 20, 5, 100, indentXText))
                .addWidget(new SliderConfigInteger(140, 155, designType, ActionBarInfo.config, "INDENT_Y", 20, 5, 100, indentYText))
                .addWidget(new ButtonConfigBoolean(140, 40, designType, ActionBarInfo.config, "RENDER_IN_DEBUG_SCREEN", false, renderInDebugScreenText))
                .build();
    }
}
