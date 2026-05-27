package dev.zelo.renderscale.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.zelo.renderscale.RenderScale;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderTarget.class, priority = 99999)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public abstract class MixinRenderTarget {

    // 修复关键：为 @Redirect 添加 require = 0，使其在目标方法不存在时优雅跳过，避免崩溃
    @Redirect(method = "setFilterMode", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_texParameter(III)V"), require = 0)
    private void onSetTexFilter(int target, int pname, int param) {
        GlStateManager._texParameter(target, pname, RenderScale.getConfig().getFilter() ? GL11.GL_LINEAR : GL11.GL_NEAREST);
    }

    // Sodium 兼容性修复
    @ModifyVariable(method = "blitToScreen(IIZ)V", at = @At("HEAD"), index = 3)
    private boolean x(boolean y) {
        return false;
    }
}
