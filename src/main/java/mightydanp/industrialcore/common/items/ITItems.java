package mightydanp.industrialcore.common.items;

import mightydanp.industrialcore.common.blocks.ITBlocks;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialtech.common.libs.ItemRef;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class ITItems {
    public static RegistryObject<Item> hole_block;

    public static void init() {
    }

    public static void initBlockItems() {
        hole_block = RegistryHandler.BLOCK_ITEMS.register(ItemRef.hole_block_name, () -> new BlockItem(ITBlocks.hole_block.get(), new Item.Properties().tab(ModItemGroups.item_tab).stacksTo(1)));
    }
}