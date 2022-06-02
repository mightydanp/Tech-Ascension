package mightydanp.industrialcore.common;

import mightydanp.industrialcore.common.handler.NetworkHandler;
import mightydanp.industrialcore.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 4/7/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class CommonEvent {

    public static MenuType<ITToolItemContainer> itToolItemContainerContainerType;

    public static void init(FMLCommonSetupEvent event) {
        NetworkHandler.onCommonSetup();
    }
}