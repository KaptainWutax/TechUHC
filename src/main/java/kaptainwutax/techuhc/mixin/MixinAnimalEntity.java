package kaptainwutax.techuhc.mixin;

import kaptainwutax.techuhc.Rules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnimalEntity.class)
public abstract class MixinAnimalEntity extends MobEntity {

    protected MixinAnimalEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isAiDisabled() {
        return !Rules.BOOLEAN_RULES.get("animal_ai");
    }

}
