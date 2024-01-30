package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxLocalization;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.alinlib.config.Localization;

public class LocalizationConfigsScreen {
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    private static final Component infoText = Localization.getText("abi.localization.info");
    private static final Component dateTimeText = Localization.getText("abi.localization.date.time");
    private static final Component northText = Localization.getText("abi.localization.north");
    private static final Component southText = Localization.getText("abi.localization.south");
    private static final Component westText = Localization.getText("abi.localization.west");
    private static final Component eastText = Localization.getText("abi.localization.east");
    private static final Component unknownText = Localization.getText("abi.localization.unknown");
    private static final InterfaceUtils.DesignType designType = InterfaceUtils.DesignType.FLAT;
    public Screen build(Screen parent){
        return new ConfigScreenBuilder(parent, Component.translatable("abi.name"), designType)
                .addPanelWidget(new Button(10, 40, 110, 20, designType, MainConfigCategory, (OnPress) -> {
                    Minecraft.getInstance().setScreen(new MainConfigsScreen().build(parent));
                }))
                .addPanelWidget(new Button(10, 65, 110, 20, designType, LocalizationConfigCategory, (OnPress) -> {
                    Minecraft.getInstance().setScreen(new LocalizationConfigsScreen().build(parent));
                }))
                .addWidget(new TextBox(140, 15, LocalizationConfigCategory, true))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "info", infoText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "date.time", dateTimeText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "north", northText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "south", southText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "west", westText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "east", eastText))
                .addWidget(new EditBoxLocalization(140, 40, designType, ActionBarInfo.localization, "unknown", unknownText))
                .build();
    }
}
