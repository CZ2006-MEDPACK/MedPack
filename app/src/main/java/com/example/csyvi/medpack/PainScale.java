package com.example.csyvi.medpack;

public class PainScale extends VitalSigns {
    private static int painScale;

    public PainScale(int painScale){
        this.painScale = painScale;
    }
    /**
     * Gets pain scale.
     *
     * @return the pain scale
     */
    public static int getPainScale() {
        return painScale;
    }

    /**
     * Sets pain scale.
     *
     * @param painScale the pain scale
     */
    public static void setPainScale(int painScale) {
        PainScale.painScale = painScale;
    }

}
