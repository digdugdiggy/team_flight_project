package edu.uhcl.team_drone.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class MenuSoundPlayer implements Disposable {

    private Sound menuClickSound;
    private Sound menuRolloverSound;

    private float globalVolume = 0.5f;

    public MenuSoundPlayer() {
        menuClickSound = Gdx.audio.newSound(Gdx.files.internal("sound/click.wav"));
        menuRolloverSound = Gdx.audio.newSound(Gdx.files.internal("sound/rollover.wav"));
    }

    public void playClickSound() {
        menuClickSound.play(globalVolume);
    }

    public void playRolloverSound() {
        menuRolloverSound.play(globalVolume);
    }

    public void setVolume(float volumeIn) {
        if ((volumeIn >= 0) && (volumeIn <= 1)) {
            this.globalVolume = volumeIn;
        }
    }

    @Override
    public void dispose() {
        menuClickSound.dispose();
    }

}
