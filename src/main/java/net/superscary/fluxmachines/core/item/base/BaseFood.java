package net.superscary.fluxmachines.core.item.base;

import net.superscary.fluxmachines.core.item.properties.FoodProperties;

public class BaseFood extends BaseItem {

    public BaseFood (Properties properties) {
        super(properties);
    }

    public static class HoneyBun extends BaseFood {
        public HoneyBun (Properties properties) {
            super(new Properties().food(FoodProperties.HONEY_BUN));
        }
    }

    public static class HardBoiledEgg extends BaseFood {
        public HardBoiledEgg (Properties properties) {
            super(new Properties().food(FoodProperties.HARD_BOILED_EGG));
        }
    }

}
