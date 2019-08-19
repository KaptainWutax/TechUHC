package kaptainwutax.techuhc.mixin;

import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TaskPriority;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ScheduledTick;
import net.minecraft.world.TickScheduler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

@Mixin(ServerTickScheduler.class)
public abstract class MixinServerTickScheduler<T> implements TickScheduler<T> {

    @Shadow @Final private Set<ScheduledTick<T>> scheduledTickActions;

    //@Inject(at = @At("HEAD"), method = "tick")
    //public void tick(CallbackInfo ci) {
    //    System.out.println(this.scheduledTickActions.size());
    //}

    @Shadow @Final private TreeSet<ScheduledTick<T>> scheduledTickActionsInOrder;

    @Shadow @Final private ServerWorld world;

    @Shadow @Final private Queue<ScheduledTick<T>> currentTickActions;

    @Shadow @Final private List<ScheduledTick<T>> consumedTickActions;

    @Shadow @Final private Consumer<ScheduledTick<T>> tickConsumer;

    @Shadow public abstract void schedule(BlockPos blockPos_1, T object_1, int int_1, TaskPriority taskPriority_1);

    /**
    @Overwrite
    public void tick() {
        int int_1 = this.scheduledTickActionsInOrder.size();
        if(this.world.dimension.getType().getRawId() == 0)System.out.println(int_1);

        if (int_1 != this.scheduledTickActions.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        } else {
            if (int_1 > 65536) {
                int_1 = 65536;
            }

            ServerChunkManager serverChunkManager_1 = this.world.method_14178();
            Iterator<ScheduledTick<T>> iterator_1 = this.scheduledTickActionsInOrder.iterator();
            this.world.getProfiler().push("cleaning");

            List<ScheduledTick<T>> newList = new ArrayList<>();

            while(iterator_1.hasNext()) {
                ScheduledTick<T> t = iterator_1.next();
                newList.add(t);
                this.scheduledTickActions.remove(t);
                iterator_1.remove();
            }

            for(ScheduledTick<T> t: newList) {
                this.tickConsumer.accept(t);
            }

            this.world.getProfiler().swap("ticking");

            this.world.getProfiler().pop();
            this.consumedTickActions.clear();
            this.currentTickActions.clear();
        }
    }
     */

}
