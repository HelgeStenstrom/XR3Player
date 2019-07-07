package com.goxr3plus.xr3player.controllers.xplayer;

import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Oscilloscope;

public class GadgetFactory {
    public Oscilloscope oscilloscope(VisualizerDrawer drawer) {
        return new Oscilloscope(drawer);
    }
}
