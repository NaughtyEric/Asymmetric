package trioxwater.asymmetric;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class RouteTrackerItem extends Item {
    public RouteTrackerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (world.isClient()) return super.use(world, playerEntity, hand);
        List<? extends PlayerEntity> list;
        list = world.getPlayers();
        PlayerEntity closestPlayer = null;
        double closestDis = 20000.0;
        Vec3d userPos = playerEntity.getPos();
        //查找最近玩家
        for (PlayerEntity player: list) {
            Vec3d curPos = player.getPos();
            if (player != playerEntity) {
                double dis = curPos.distanceTo(userPos);
                if (dis < closestDis) {
                    closestPlayer = player;
                    closestDis = dis;
                }
            }
        }
        if (closestPlayer != null) {
            if (closestDis <= 75.00) {
                Vec3d v = closestPlayer.getPos().relativize(userPos).normalize();
                for (int i = 0; i < 12; ++i)
                    world.addParticle(ParticleTypes.PORTAL, playerEntity.getX(), playerEntity.getY(),
                            playerEntity.getZ(), v.getX() * 2, v.getY() * 2, v.getZ() * 2);
            }
            //赋予该玩家发光效果，持续30秒
            closestPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 1));
        }
        playerEntity.getItemCooldownManager().set(this, 1200);
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText
                ("item.asymmetric.route_tracker.tooltip1"));
        tooltip.add(new TranslatableText
                ("item.asymmetric.route_tracker.tooltip2").formatted(Formatting.RED));
    }
}
