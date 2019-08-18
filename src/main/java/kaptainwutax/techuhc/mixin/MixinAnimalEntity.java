package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.Rules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class MixinAnimalEntity extends PassiveEntity {

    protected MixinAnimalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author KaptainWutax
     *
     * Compat issues.
     */
    @Overwrite
    public boolean isAiDisabled(CallbackInfo ci) {
        return super.isAiDisabled() || !Rules.BOOLEAN_RULES.get("animal_ai");
    }

}
