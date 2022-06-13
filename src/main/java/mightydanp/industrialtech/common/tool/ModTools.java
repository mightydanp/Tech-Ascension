package mightydanp.industrialtech.common.tool;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialcore.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.tool.ITTool;
import mightydanp.industrialcore.common.material.tool.ITTools;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.tool.tools.ChiselToolItem;
import mightydanp.industrialtech.common.tool.tools.HammerToolItem;
import mightydanp.industrialtech.common.tool.tools.KnifeToolItem;
import mightydanp.industrialtech.common.tool.tools.PickaxeToolItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools extends ITTools {
    public static ITTool hammer, chisel, pickaxe, knife;
    public static RegistryObject<Item>  chiselTool, hammerTool, pickaxeTool, knifeTool;

    public static String chiselName = "chisel";
    public static Set<String> chiselEffectiveOn = ImmutableSet.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));
    public static Map<String, Integer> chiselAssembleItems = Map.ofEntries(Map.entry(new ResourceLocation(Ref.mod_id, "hammer").toString(), 1));
    public static Map<String, Integer> chiselParts = Map.ofEntries(Map.entry("chisel_head", 1), Map.entry("wedge_handle", 2));
    public static List<String> chiselDisassembleItems = List.of(new ResourceLocation(Ref.mod_id, "hammer").toString(), new ResourceLocation(Ref.mod_id, "chisel").toString());

    public static String hammerName = "hammer";
    public static Set<String> hammerEffectiveOn = ImmutableSet.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));
    public static Map<String, Integer> hammerAssembleItems = Map.ofEntries(Map.entry(new ResourceLocation(Ref.mod_id, "hammer").toString(), 1));
    public static Map<String, Integer> hammerParts = Map.ofEntries(Map.entry("hammer_head", 1), Map.entry("wedge_handle", 2), Map.entry("wedge", 3));
    public static List<String> hammerDisassembleItems = List.of(new ResourceLocation(Ref.mod_id, "chisel").toString(), new ResourceLocation(Ref.mod_id, "hammer").toString());

    public static String pickaxeName = "pickaxe";
    public static Set<String> pickaxeEffectiveOn = ImmutableSet.of(String.valueOf(Blocks.ACTIVATOR_RAIL.getRegistryName()), String.valueOf(Blocks.COAL_ORE.getRegistryName()), String.valueOf(Blocks.COBBLESTONE.getRegistryName()), String.valueOf(Blocks.DETECTOR_RAIL.getRegistryName()), String.valueOf(Blocks.DIAMOND_BLOCK.getRegistryName()), String.valueOf(Blocks.DIAMOND_ORE.getRegistryName()), String.valueOf(Blocks.POWERED_RAIL.getRegistryName()), String.valueOf(Blocks.GOLD_BLOCK.getRegistryName()), String.valueOf(Blocks.GOLD_ORE.getRegistryName()), String.valueOf(Blocks.NETHER_GOLD_ORE.getRegistryName()), String.valueOf(Blocks.ICE.getRegistryName()), String.valueOf(Blocks.IRON_BLOCK.getRegistryName()), String.valueOf(Blocks.IRON_ORE.getRegistryName()), String.valueOf(Blocks.LAPIS_BLOCK.getRegistryName()), String.valueOf(Blocks.LAPIS_ORE.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE.getRegistryName()), String.valueOf(Blocks.NETHERRACK.getRegistryName()), String.valueOf(Blocks.PACKED_ICE.getRegistryName()), String.valueOf(Blocks.BLUE_ICE.getRegistryName()), String.valueOf(Blocks.RAIL.getRegistryName()), String.valueOf(Blocks.REDSTONE_ORE.getRegistryName()), String.valueOf(Blocks.SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CHISELED_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.CUT_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.STONE.getRegistryName()), String.valueOf(Blocks.GRANITE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE.getRegistryName()), String.valueOf(Blocks.DIORITE.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE.getRegistryName()), String.valueOf(Blocks.ANDESITE.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE.getRegistryName()), String.valueOf(Blocks.STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE_SLAB.getRegistryName()), String.valueOf(Blocks.SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PETRIFIED_OAK_SLAB.getRegistryName()), String.valueOf(Blocks.COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.PURPUR_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE.getRegistryName()), String.valueOf(Blocks.SMOOTH_STONE.getRegistryName()), String.valueOf(Blocks.STONE_BUTTON.getRegistryName()), String.valueOf(Blocks.STONE_PRESSURE_PLATE.getRegistryName()), String.valueOf(Blocks.POLISHED_GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_RED_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.MOSSY_COBBLESTONE_SLAB.getRegistryName()), String.valueOf(Blocks.END_STONE_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_SANDSTONE_SLAB.getRegistryName()), String.valueOf(Blocks.SMOOTH_QUARTZ_SLAB.getRegistryName()), String.valueOf(Blocks.GRANITE_SLAB.getRegistryName()), String.valueOf(Blocks.ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.RED_NETHER_BRICK_SLAB.getRegistryName()), String.valueOf(Blocks.POLISHED_ANDESITE_SLAB.getRegistryName()), String.valueOf(Blocks.DIORITE_SLAB.getRegistryName()), String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));
    public static Map<String, Integer> pickaxeAssembleItems = Map.ofEntries(Map.entry(new ResourceLocation(Ref.mod_id, "hammer").toString(), 1));
    public static Map<String, Integer> pickaxeParts = Map.ofEntries(Map.entry("pickaxe_head", 1), Map.entry("wedge_handle", 2), Map.entry("wedge", 3));
    public static List<String> pickaxeDisassembleItems = List.of(new ResourceLocation(Ref.mod_id, "chisel").toString(), new ResourceLocation(Ref.mod_id, "hammer").toString());

    public static String knifeName = "knife";
    public static Set<String> knifeEffectiveOn = ImmutableSet.of(String.valueOf(Blocks.SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLACK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.BROWN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.CYAN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.GREEN_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_BLUE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIGHT_GRAY_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.LIME_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.MAGENTA_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.ORANGE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PINK_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PURPLE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.RED_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.WHITE_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.YELLOW_SHULKER_BOX.getRegistryName()), String.valueOf(Blocks.PISTON.getRegistryName()), String.valueOf(Blocks.STICKY_PISTON.getRegistryName()), String.valueOf(Blocks.PISTON_HEAD));
    public static Map<String, Integer> knifeAssembleItems = Map.ofEntries(Map.entry(new ResourceLocation(Ref.mod_id, "hammer").toString(), 1));
    public static Map<String, Integer> knifeParts = Map.ofEntries(Map.entry("knife_head", 1), Map.entry("wedge_handle", 2));
    public static List<String> knifeDisassembleItems = List.of(new ResourceLocation(Ref.mod_id, "hammer").toString(), new ResourceLocation(Ref.mod_id, "chisel").toString());

    public static void init(){
        //todo check to see if really needed. Head parts give toolType.

        tools.add(chisel = new ITTool(chiselName, 1, chiselEffectiveOn, chiselAssembleItems, chiselParts, chiselDisassembleItems,
                chiselTool = RegistryHandler.ITEMS.register(chiselName, ()-> new ChiselToolItem().setName(chiselName).setEffectiveBlocks(chiselEffectiveOn).setAssembleItems(chiselAssembleItems).setParts(chiselParts) .setDisassembleItems(chiselDisassembleItems))
        ));

        tools.add(hammer = new ITTool(hammerName, 1, hammerEffectiveOn, hammerAssembleItems, hammerParts, hammerDisassembleItems,
                hammerTool = RegistryHandler.ITEMS.register(hammerName, ()-> new HammerToolItem().setName(hammerName).setEffectiveBlocks(hammerEffectiveOn).setAssembleItems(hammerAssembleItems).setParts(hammerParts) .setDisassembleItems(hammerDisassembleItems))
        ));

        tools.add(pickaxe = new ITTool(pickaxeName, 1, pickaxeEffectiveOn, pickaxeAssembleItems, pickaxeParts, pickaxeDisassembleItems,
                pickaxeTool = RegistryHandler.ITEMS.register(pickaxeName, ()-> new PickaxeToolItem().setName(pickaxeName).setEffectiveBlocks(pickaxeEffectiveOn).setAssembleItems(pickaxeAssembleItems).setParts(pickaxeParts) .setDisassembleItems(pickaxeDisassembleItems))
        ));

        tools.add(knife = new ITTool(knifeName, 1, knifeEffectiveOn, knifeAssembleItems, knifeParts, knifeDisassembleItems,
                knifeTool = RegistryHandler.ITEMS.register(knifeName, ()-> new KnifeToolItem().setName(knifeName).setEffectiveBlocks(knifeEffectiveOn).setAssembleItems(knifeAssembleItems).setParts(knifeParts) .setDisassembleItems(knifeDisassembleItems))
        ));
    }

    public static void handCraftingInit(PlayerInteractEvent.RightClickItem event){
        for(ITTool tool : tools) {//redo crashes on rightclick anything
            //ITTool.handToolCrafting((ITToolItem)tool.toolItem.get(), event, tool.hitDamage, ((ITToolItem)tool.toolItem.get()).toolsNeeded);
        }
    }

    public static void clientInit(){
        for(ITTool material : tools) {
            material.registerColorForItem();
        }
    }
}
