package net.superscary.fluxmachines.core.registries;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.superscary.fluxmachines.core.FluxMachines;

import java.util.function.Supplier;

public class FMDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FluxMachines.MODID);

    public static final Supplier<AttachmentType<ItemStackHandler>> HANDLER = REGISTRY.register("handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build());

}
