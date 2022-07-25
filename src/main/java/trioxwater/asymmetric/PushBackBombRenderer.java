package trioxwater.asymmetric;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class PushBackBombRenderer extends EntityRenderer<PushBackBombEntity> {
    protected PushBackBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
//    public PushBackBombRenderer(EntityRenderDispatcher entityRenderDispatcher) {
//        super(entityRenderDispatcher, new PushBackBombEntityModel(), 0.5f);
//    }

    @Override
    public Identifier getTexture(PushBackBombEntity entity) {
        return new Identifier(Asymmetric.ModID, "push_back_bomb_entity");
    }
}
