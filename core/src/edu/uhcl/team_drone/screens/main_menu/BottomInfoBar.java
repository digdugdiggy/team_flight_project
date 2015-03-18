package edu.uhcl.team_drone.screens.main_menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class BottomInfoBar extends Label {

    public BottomInfoBar(CharSequence text, LabelStyle style) {
        super(text, style);
        this.setAlignment(Align.center);
        this.setFontScale(1.5f);
    }

    public void addToTable(Table tableIn) {
        tableIn.add(this).padTop(12).fill().width(802).expand();
    }

}
