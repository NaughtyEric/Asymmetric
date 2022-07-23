package trioxwater.asymmetric;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class TrackTracerItem extends Item {
    public TrackTracerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (world.isClient()) return super.use(world, playerEntity, hand);
        PlayerEntity closestPlayer = world.getClosestPlayer(playerEntity, 50.0);
        List<? extends PlayerEntity> list;
        list = world.getPlayers();
        Vec3d playerLoc = playerEntity.getPos();
        for (PlayerEntity player: list) {
            Vec3d curPos = player.getPos();
            Vec3d v = curPos.relativize(playerLoc); //获取一个指向玩家的向量
            Vec3d fv = v.normalize();
            //ParticleEffect ParticleEffect = new ParticleType<>();
            //world.addParticle(ParticleEffect, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
            //        fv.getX(), fv.getY(), fv.getZ());
        }
        if(closestPlayer != null) {
            //赋予该玩家发光效果，持续20秒
            closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 1));
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("获取其他玩家的大致方位信息，并使50m内最近的玩家发光30秒。" +
                "\n指示有一定偏差，距离越近，方位越准确。").formatted(Formatting.RED) );
    }
}
