package dev.zelo.renderscale.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.zelo.renderscale.RenderScale;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderTarget.class, priority = 99999)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class MixinRenderTarget {

    @Shadow
    public abstract int getColorTextureId();

    @Inject(method = "bindWrite", at = @At("HEAD"))
    private void onBindWrite(boolean p_166098_, CallbackInfo ci) {
        if (RenderScale.getConfig() == null) return;
        // 当RenderTarget被绑定时，检查玩家配置并应用指定的纹理过滤
        int filter = RenderScale.getConfig().getFilter() ? GL11.GL_LINEAR : GL11.GL_NEAREST;
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
    }
}
