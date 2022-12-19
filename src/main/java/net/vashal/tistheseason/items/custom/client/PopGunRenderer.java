package net.vashal.tistheseason.items.custom.client;


import net.vashal.tistheseason.items.custom.PopGunItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PopGunRenderer extends GeoItemRenderer<PopGunItem> {
    public PopGunRenderer() {
        super(new PopGunModel());
    }

}
