package net.superscary.fluxmachines.core.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

import java.util.List;
import java.util.Set;

public class FMMixinConfig implements IMixinConfigPlugin {

	@Override
	public void onLoad (String mixinPackage) {
		InjectionInfo.register(CaptureOwnerInjectionInfo.class);
	}

	@Override
	public String getRefMapperConfig () {
		return null;
	}

	@Override
	public boolean shouldApplyMixin (String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void acceptTargets (Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins () {
		return null;
	}

	@Override
	public void preApply (String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply (String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

}
