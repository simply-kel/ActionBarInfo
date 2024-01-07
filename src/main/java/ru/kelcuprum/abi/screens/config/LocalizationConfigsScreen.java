package ru.kelcuprum.abi.screens.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.abi.localization.Localization;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.Button;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxString;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;

public class LocalizationConfigsScreen extends Screen {
    private final Screen parent;
    private static final Component TITLE = Localization.getText("abi.name");
    // CATEGORYES
    private static final Component MainConfigCategory = Localization.getText("abi.config");
    private Button MainConfigCategoryButton;
    private static final Component LocalizationConfigCategory = Localization.getText("abi.localization");
    private Button LocalizationConfigCategoryButton;
    // CATEGORY CONTENT
    private AbstractWidget titleBox;

    private EditBox info;
    private static final Component infoText = Localization.getText("abi.localization.info");
    private EditBox dateTime;
    private static final Component dateTimeText = Localization.getText("abi.localization.date.time");
    //
    private EditBox north;
    private static final Component northText = Localization.getText("abi.localization.north");
    private EditBox south;
    private static final Component southText = Localization.getText("abi.localization.south");
    private EditBox west;
    private static final Component westText = Localization.getText("abi.localization.west");
    private EditBox east;
    private static final Component eastText = Localization.getText("abi.localization.east");
    private EditBox unknown;
    private static final Component unknownText = Localization.getText("abi.localization.unknown");
    //
    private static final Component EXIT = Localization.getText("abi.config.exit");
    private int scrolled = 0;

    public LocalizationConfigsScreen(Screen parent) {
        super(LocalizationConfigCategory);
        this.parent = parent;
    }

    public void tick() {
        this.titleBox.setY(15 - this.scrolled);

        this.info.setY(40-scrolled);
        this.dateTime.setY(65-scrolled);

        this.north.setY(90-scrolled);
        this.south.setY(115-scrolled);
        this.west.setY(140-scrolled);
        this.east.setY(165-scrolled);
        this.unknown.setY(190-scrolled);
        super.tick();
    }

    public void init() {
        this.scrolled = 0;
        this.initButton();
        this.initButtonsCategory();
    }

    private void initButtonsCategory() {
        int x = this.width - 150;
        this.titleBox = this.addRenderableWidget(new TextBox(140, 15, x, 9, this.title, true));
        //
        this.info = new EditBoxString(140, 40, x, 20, infoText);
        this.info.setValue(Localization.getLocalization("info", false));
        this.info.setResponder(s->{
            Localization.setLocalization("info", s);
        });

        this.addRenderableWidget(info);
        //
        this.dateTime = new EditBoxString(140, 40, x, 20, dateTimeText);
        this.dateTime.setValue(Localization.getLocalization("date.time", false));
        this.dateTime.setResponder(s->{
            Localization.setLocalization("date.time", s);
        });
        this.addRenderableWidget(dateTime);
        ///////
        //
        this.north = new EditBoxString(140, 40, x, 20, northText);
        this.north.setValue(Localization.getLocalization("north", false));
        this.north.setResponder(s->{
            Localization.setLocalization("north", s);
        });
        this.addRenderableWidget(north);
        //
        this.south = new EditBoxString(140, 40, x, 20, southText);
        this.south.setValue(Localization.getLocalization("south", false));
        this.south.setResponder(s->{
            Localization.setLocalization("south", s);
        });
        this.addRenderableWidget(south);
        //
        this.west = new EditBoxString(140, 40, x, 20, westText);
        this.west.setValue(Localization.getLocalization("west", false));
        this.west.setResponder(s->{
            Localization.setLocalization("west", s);
        });
        this.addRenderableWidget(west);
        //
        this.east = new EditBoxString(140, 40, x, 20, eastText);
        this.east.setValue(Localization.getLocalization("east", false));
        this.east.setResponder(s->{
            Localization.setLocalization("east", s);
        });
        this.addRenderableWidget(east);
        //
        this.unknown = new EditBoxString(140, 40, x, 20, unknownText);
        this.unknown.setValue(Localization.getLocalization("unknown", false));
        this.unknown.setResponder(s->{
            Localization.setLocalization("unknown", s);
        });
        this.addRenderableWidget(unknown);
    }

    private void initButton() {
        this.addRenderableWidget(new TextBox(10, 15, 110, 9, TITLE, true));
        this.MainConfigCategoryButton = this.addRenderableWidget(new Button(10, 40, 110, 20, InterfaceUtils.DesignType.VANILLA, MainConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new MainConfigsScreen(this.parent));
        }));
        this.LocalizationConfigCategoryButton = this.addRenderableWidget(new Button(10, 65, 110, 20, InterfaceUtils.DesignType.VANILLA, LocalizationConfigCategory, (OnPress) -> {
            this.minecraft.setScreen(new LocalizationConfigsScreen(this.parent));
        }));
        this.LocalizationConfigCategoryButton.setActive(false);
        //
        this.addRenderableWidget(new Button(10, this.height - 30, 110, 20, InterfaceUtils.DesignType.VANILLA, EXIT, (OnPress) -> {
            this.minecraft.setScreen(this.parent);
        }));
    }

    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        InterfaceUtils.renderBackground(guiGraphics, this.minecraft);
        InterfaceUtils.renderLeftPanel(guiGraphics, 130, this.height, 0x7F000000);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public boolean mouseScrolled(double d, double e, double f, double g) {
        int scrolled = (int)((double)this.scrolled + g * 10.0 * -1.0);
        int size = 215;
        if (scrolled <= 0 || size <= this.height) {
            this.scrolled = 0;
        } else this.scrolled = Math.min(scrolled, size - this.height);

        return super.mouseScrolled(d, e, f, g);
    }
}
