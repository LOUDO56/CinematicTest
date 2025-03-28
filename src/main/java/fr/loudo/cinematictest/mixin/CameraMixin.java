package fr.loudo.cinematictest.mixin;

import fr.loudo.cinematictest.CinematicPos;
import fr.loudo.cinematictest.CinematicTest;
import fr.loudo.cinematictest.utils.Location;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow protected abstract void setPos(double x, double y, double z);
    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        CinematicPos cinematicPos = CinematicTest.getCinematicPos();
        if(cinematicPos.isCinematic()) {
            Location currentLoc = cinematicPos.getCurrentPos();
            this.setPos(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());
            this.setRotation((float) currentLoc.getPitch(),(float) currentLoc.getYaw());
            ci.cancel();
        }
    }

}
