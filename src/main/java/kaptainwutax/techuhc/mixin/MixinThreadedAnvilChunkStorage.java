package kaptainwutax.techuhc.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;
import java.util.function.BooleanSupplier;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class MixinThreadedAnvilChunkStorage {

    @Shadow @Final private ServerWorld world;

    @Shadow @Final private LongSet unloadedChunks;

    @Shadow @Final private Long2ObjectLinkedOpenHashMap<ChunkHolder> currentChunkHolders;

    @Shadow @Final private Long2ObjectLinkedOpenHashMap<ChunkHolder> field_18807;

    @Shadow private boolean chunkHolderListDirty;

    @Shadow protected abstract void method_20458(long long_1, ChunkHolder chunkHolder_1);

    @Shadow @Final private Queue<Runnable> field_19343;

    @Inject(at = @At("HEAD"), method = "method_20605", cancellable = true)
    private void method_20605(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        if(this.world.getTime() % 900 == 0) {
            LongIterator longIterator_1 = this.unloadedChunks.iterator();

            for(int int_1 = 0; longIterator_1.hasNext() && (int_1 < 100 || this.unloadedChunks.size() > 2000); longIterator_1.remove()) {
                long long_1 = longIterator_1.nextLong();
                ChunkHolder chunkHolder_1 = (ChunkHolder) this.currentChunkHolders.remove(long_1);
                if(chunkHolder_1 != null) {
                    this.field_18807.put(long_1, chunkHolder_1);
                    this.chunkHolderListDirty = true;
                    ++int_1;
                    this.method_20458(long_1, chunkHolder_1);
                }
            }

            Runnable runnable_1;
            while(booleanSupplier.getAsBoolean() && (runnable_1 = (Runnable) this.field_19343.poll()) != null) {
                runnable_1.run();
            }
        }

        ci.cancel();
    }

}
