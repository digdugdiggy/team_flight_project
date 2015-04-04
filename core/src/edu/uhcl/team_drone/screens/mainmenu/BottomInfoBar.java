package edu.uhcl.team_drone.screens.mainmenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class BottomInfoBar extends Label {

    public BottomInfoBar(CharSequence text, LabelStyle style) {
        super(text, style);
        this.setAlignment(Align.center);        
    }

    public void addToTable(Table tableIn) {
        tableIn.add(this).padTop(8).height(40).fill().width(802).expand();
    }

}
