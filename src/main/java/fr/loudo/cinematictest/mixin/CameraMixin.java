package fr.loudo.cinematictest.mixin;

import fr.loudo.cinematictest.CinematicPos;
import fr.loudo.cinematictest.CinematicTest;
import fr.loudo.cinematictest.utils.Easing;
import fr.loudo.cinematictest.utils.Location;
import fr.loudo.cinematictest.utils.MathUtils;
import fr.loudo.cinematictest.utils.TpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Camera.class)
public abstract class CameraMixin {

    private double t;

    @Shadow protected abstract void setPos(double x, double y, double z);
    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        CinematicPos cinematicPos = CinematicTest.getCinematicPos();
        if (cinematicPos.isCinematic() && cinematicPos.getCurrentIndexKeyframe() < cinematicPos.getKeyFrames().size() - 1) {
            MinecraftClient mc = MinecraftClient.getInstance();
            //mc.options.setPerspective(Perspective.FIRST_PERSON);
            //mc.options.hudHidden = true;
            long currentTime = System.nanoTime();
            double elapsedTime = (currentTime - cinematicPos.getStartTime()) / 1_000_000_000.0;
            double duration = cinematicPos.getDuration();
            t = Math.min(elapsedTime / duration, 1.0);


            double smoothedT = Easing.easeOutSine(t);
            List<Location> keyframes = cinematicPos.getKeyFrames();
            Location currentLoc = MathUtils.getNextLocation(
                    keyframes.get(cinematicPos.getCurrentIndexKeyframe()),
                    keyframes.get(cinematicPos.getCurrentIndexKeyframe() + 1),
                    smoothedT
            );
            cinematicPos.setCurrentLoc(currentLoc);
            this.setPos(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());
            this.setRotation((float) currentLoc.getYaw(), (float) currentLoc.getPitch());

            if (t >= 1.0) {
                t = 0;
                cinematicPos.setStartTime(System.nanoTime());
                int newIndex = cinematicPos.getCurrentIndexKeyframe() + 1;
                cinematicPos.setCurrentIndexKeyframe(newIndex);
                if(newIndex == keyframes.size() - 1) {
                    cinematicPos.stop();
                    mc.options.hudHidden = false;
                }
            }

            ci.cancel();

        }
    }
}
