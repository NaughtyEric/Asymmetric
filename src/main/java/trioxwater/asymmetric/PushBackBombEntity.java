package trioxwater.asymmetric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import trioxwater.asymmetric.client.AsymmetricClient;
import trioxwater.asymmetric.client.EntitySpawnPacket;

public class PushBackBombEntity extends ThrownItemEntity {

    public PushBackBombEntity(World world, LivingEntity owner) {
        super(Asymmetric.PUSH_BACK_BOMB_ENTITY, owner, world);
    }

    public PushBackBombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public PushBackBombEntity(World world, double x, double y, double z) {
        super(Asymmetric.PUSH_BACK_BOMB_ENTITY, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return Asymmetric.PUSH_BACK_BOMB;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.EXPLOSION : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Vec3d curv = this.getVelocity().normalize();
        entityHitResult.getEntity().setVelocity(curv.getX()*3, curv.getY(), curv.getZ()*3);
    }

    protected void onCollision(HitResult hitResult) { // called on collision with a block
        super.onCollision(hitResult);
        var loc = this.getPos();
        if (!this.world.isClient) { // checks if the world is client
            this.world.sendEntityStatus(this, (byte)3); // particle?

            this.kill(); // kills the projectile
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, AsymmetricClient.PacketID);
    }
    @Nullable
    public Entity moveToWorld(ServerWorld destination) {
        Entity entity = this.getOwner();
        if (entity != null && entity.world.getRegistryKey() != destination.getRegistryKey()) {
            this.setOwner((Entity)null);
        }
        return super.moveToWorld(destination);
    }
}
