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
    private final Polyspiral polyspiral;
    private final Sierpinski sierpinski;
    private final Sprites3D sprites3D;
    private final JuliaSet juliaSet;


    GadgetOwner(Builder builder) {
        this.oscilloscope = builder.oscilloscope;
        this.polyspiral = builder.polyspiral;
        this.sierpinski = builder.sierpinski;
        this.sprites3D = builder.sprites3D;
        this.juliaSet = builder.juliaSet;
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

    public void drawPolySpiral() {
        polyspiral.drawPolySpiral();
    }

    /**
     * Draws the Sierpinski Triangles.
     */
    public void drawSierpinski() {
        sierpinski.drawSierpinski();
    }

    /**
     * Draws a 3D Sprite
     */
    public void drawSprite3D() {
        sprites3D.draw();
    }

    /**
     * Draws the Julia Set
     */
    public void drawJuliaSet() {
        juliaSet.drawJuliaSet();
    }

    /**
     * Draws an Oscilloscope with up and down Lines
     */
    public void drawOscilloScopeLines() {
        oscilloscope.drawOscilloScopeLines();
    }

    public static class Builder {
        private Oscilloscope oscilloscope;
        private Polyspiral polyspiral;
        private Sierpinski sierpinski;
        private Sprites3D sprites3D;

        private JuliaSet juliaSet;


        public GadgetOwner build() {
            return new GadgetOwner(this);
        }

        public Builder setOscilloscope(Oscilloscope oscilloscope) {
            this.oscilloscope = oscilloscope;
            return this;
        }

        public Builder setPolyspiral(Polyspiral polyspiral) {
            this.polyspiral = polyspiral;
            return this;
        }

        public Builder setSierpinski(Sierpinski sierpinski) {
            this.sierpinski = sierpinski;
            return this;
        }

        public Builder setSprites3D(Sprites3D sprites3D) {
            this.sprites3D = sprites3D;
            return this;
        }

        public Builder setJuliaSet(JuliaSet juliaSet) {
            this.juliaSet = juliaSet;
            return this;
        }


    }
}
