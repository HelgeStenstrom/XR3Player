package com.goxr3plus.xr3player.controllers.xplayer;

import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Oscilloscope;

public class GadgetOwner {
    private final Oscilloscope oscilloscope;

    public GadgetOwner(Oscilloscope oscilloscope) {
        this.oscilloscope = oscilloscope;
    }

    /**
     * Draws an Oscilloscope
     *
     * @param stereo The Oscilloscope with have 2 lines->stereo or 1 line->merge
     *               left and right audio
     */
    public void drawOscilloscope(boolean stereo) {
        oscilloscope.drawOscilloscope(stereo);
    }

    /**
     * Draws an Oscilloscope with up and down Lines
     */
    public void drawOscilloScopeLines() {
        oscilloscope.drawOscilloScopeLines();
    }
}
