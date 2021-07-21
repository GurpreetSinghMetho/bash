package com.orem.bashhub.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class PaletteColor {

    public static String getEyeBrowColor(String colorName) {
        if (colorName.equals(Const.HAIR_RED) || colorName.equals(Const.HAIR_BROWN))
            return "#693D20";
        else if (colorName.equals(Const.HAIR_BLOND) || colorName.equals(Const.HAIR_GRAY))
            return "#B18B3A";
        else return "#040001";
    }

    private static List<PaletteColorInner> getColors(String type) {
        List<PaletteColorInner> colors = new ArrayList<>();
        if (type.equals(Const.COLOR_EYE)) {
            colors.add(new PaletteColorInner(Color.parseColor("#3768A3")));
            colors.add(new PaletteColorInner(Color.parseColor("#5D371C")));
        } else if (type.equals(Const.COLOR_EYE_BROW)) {
            colors.add(new PaletteColorInner(Color.parseColor("#040001")));
            colors.add(new PaletteColorInner(Color.parseColor("#B18B3A")));
            colors.add(new PaletteColorInner(Color.parseColor("#693D20")));
        } else {
            colors.add(new PaletteColorInner(Color.parseColor("#7B4D2B")));
            colors.add(new PaletteColorInner(Color.parseColor("#C58851")));
            colors.add(new PaletteColorInner(Color.parseColor("#FFCB99")));
        }
        return colors;
    }

    public static String findClosestPaletteColor(final int color, String type) {
        int closestColor = -1;
        int closestDistance = Integer.MAX_VALUE;
        for (final PaletteColorInner paletteColor : getColors(type)) {
            final int distance = paletteColor.distanceTo(color);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestColor = paletteColor.asInt();
            }
        }
        return String.format("#%06X", (0xFFFFFF & closestColor));
    }

    private static final class PaletteColorInner {
        private final int r;
        private final int g;
        private final int b;
        private final int color;

        PaletteColorInner(final int color) {
            this.r = ((color & 0xff000000) >>> 24);
            this.g = ((color & 0x00ff0000) >>> 16);
            this.b = ((color & 0x0000ff00) >>> 8);
            this.color = color;
        }

        int distanceTo(final int color) {
            final int deltaR = this.r - ((color & 0xff000000) >>> 24);
            final int deltaG = this.g - ((color & 0x00ff0000) >>> 16);
            final int deltaB = this.b - ((color & 0x0000ff00) >>> 8);
            return (deltaR * deltaR) + (deltaG * deltaG) + (deltaB * deltaB);
        }

        int asInt() {
            return this.color;
        }
    }
}
