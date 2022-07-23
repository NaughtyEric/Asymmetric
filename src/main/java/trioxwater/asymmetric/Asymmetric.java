package trioxwater.asymmetric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Asymmetric implements ModInitializer {

    public static final RouteTrackerItem TRACK_TRACER = new RouteTrackerItem(new FabricItemSettings().group(ItemGroup.REDSTONE).fireproof());

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("asymmetric", "track_tracer"), TRACK_TRACER);
    }
}
