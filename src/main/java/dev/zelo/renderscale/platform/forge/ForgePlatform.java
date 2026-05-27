package dev.zelo.renderscale.platform.forge;

//? forge {

import dev.zelo.renderscale.platform.Platform;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatform implements Platform {

	// @Override   // 已删除
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	// @Override   // 已删除
	public ModLoader loader() {
		return ModLoader.FORGE;
	}

	// @Override   // 已删除
	public String mcVersion() {
		return "";
	}

	// @Override   // 已删除
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}
}
//?}
