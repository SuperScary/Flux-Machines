package net.superscary.fluxmachines.impl.jei.util;

import net.minecraft.resources.ResourceLocation;
import net.superscary.fluxmachines.core.FluxMachines;

public class JTexture {

    private ResourceLocation location;
    private String modid;
    private String path;

    public JTexture (String path) {
        this(FluxMachines.getResource(path));
    }

    public JTexture (String id, String path) {
        this(FluxMachines.custom(id, path));
    }

    public JTexture (ResourceLocation location) {
        this.location = location;
        this.modid = location.getNamespace();
        this.path = location.getPath();
    }

    public ResourceLocation getLocation () {
        return location;
    }

    public String getModid () {
        return modid;
    }

    public String getPath () {
        return path;
    }

    public static class Appended extends JTexture {

        public Appended (String path) {
            super(FluxMachines.MODID, path);
        }

        public Appended (String id, String path) {
            super(FluxMachines.custom(id, "textures/gui/" + path + "_gui.png"));
        }

        public Appended (ResourceLocation location) {
            super(location);
        }

    }

}
