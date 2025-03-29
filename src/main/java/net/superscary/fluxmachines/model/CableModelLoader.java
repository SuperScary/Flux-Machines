package net.superscary.fluxmachines.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CableModelLoader implements IGeometryLoader<CableModelLoader.CableModelGeometry> {

    public static final ResourceLocation GENERATOR_LOADER = FluxMachines.getResource("cableloader");

    public static void register (ModelEvent.RegisterGeometryLoaders event) {
        event.register(GENERATOR_LOADER, new CableModelLoader());
    }

    @Override
    public @NotNull CableModelGeometry read (JsonObject jsonObject, @NotNull JsonDeserializationContext deserializationContext) throws JsonParseException {
        boolean facade = jsonObject.has("facade") && jsonObject.get("facade").getAsBoolean();
        return new CableModelGeometry(facade);
    }

    public static class CableModelGeometry implements IUnbakedGeometry<CableModelGeometry> {

        private final boolean facade;

        public CableModelGeometry(boolean facade) {
            this.facade = facade;
        }

        @Override
        public @NotNull BakedModel bake(@NotNull IGeometryBakingContext context, @NotNull ModelBaker baker, @NotNull Function<Material, TextureAtlasSprite> spriteGetter, @NotNull ModelState modelState, @NotNull ItemOverrides overrides) {
            return new CableBakedModel(context, facade);
        }

    }

}
