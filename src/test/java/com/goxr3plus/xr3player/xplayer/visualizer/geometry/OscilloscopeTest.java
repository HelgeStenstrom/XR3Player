package com.goxr3plus.xr3player.xplayer.visualizer.geometry;

import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OscilloscopeTest {

    @Test
     void createOscilloscope() {

        final VisualizerDrawer visualizerDrawer = new VisualizerDrawer();
        final Oscilloscope oscilloscope = new Oscilloscope(visualizerDrawer);
        oscilloscope.drawOscilloscope(false);
    }

}