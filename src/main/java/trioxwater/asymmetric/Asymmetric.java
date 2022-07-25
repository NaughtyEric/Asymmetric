package trioxwater.asymmetric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Asymmetric implements ModInitializer {
    public static final String ModID = "asymmetric";
    public static final RouteTrackerItem ROUTE_TRACKER = new RouteTrackerItem
            (new FabricItemSettings().group(Asymmetric.HUNTER_GROUP).fireproof());

    public static final PushBackBombItem PUSH_BACK_BOMB = new PushBackBombItem
            (new FabricItemSettings().group(Asymmetric.SURVIVOR_GROUP));

    public static final EntityType<PushBackBombEntity> PUSH_BACK_BOMB_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "push_back_bomb_entity"),
            FabricEntityTypeBuilder.<PushBackBombEntity>create(SpawnGroup.MISC, PushBackBombEntity::new)
                    .dimensions(EntityDimensions.fixed(0.2F, 0.2F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final ItemGroup HUNTER_GROUP = FabricItemGroupBuilder.build(
                new Identifier(ModID, "hunter"),
                () -> new ItemStack(Items.DIAMOND_SWORD));

    public static final ItemGroup SURVIVOR_GROUP = FabricItemGroupBuilder.create(
                        new Identifier(ModID, "survivor"))
                .icon(() -> new ItemStack(Items.OAK_BOAT))
                .build();
    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("asymmetric", "route_tracker"), ROUTE_TRACKER);
        Registry.register(Registry.ITEM, new Identifier("asymmetric", "push_back_bomb"), PUSH_BACK_BOMB);
    }
}
