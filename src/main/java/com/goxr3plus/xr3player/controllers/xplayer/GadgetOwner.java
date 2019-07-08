package com.goxr3plus.xr3player.controllers.xplayer;

import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.JuliaSet;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Oscilloscope;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Polyspiral;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Sierpinski;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Sprites3D;

/**
 * The purpose of this class is to own instances of {@link Oscilloscope}, {@link Polyspiral} , {@link Sierpinski},
 * {@link JuliaSet} and  {@link Sprites3D}, so that {@link VisualizerDrawer} doesn't have to do it.
 * This leads to the possibility to create instances of these classes independently, without having to create
 * instances of all them (as a side effect of creating an instance of {@link VisualizerDrawer})
 */
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
