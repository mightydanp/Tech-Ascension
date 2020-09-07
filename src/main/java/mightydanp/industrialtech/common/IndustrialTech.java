package mightydanp.industrialtech.common;

import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.data.*;
import mightydanp.industrialtech.common.datagen.IndustrialTechBlockTagProvider;
import mightydanp.industrialtech.common.datagen.IndustrialTechRecipes;
import mightydanp.industrialtech.common.datagen.ProgressionAdvancements;
import mightydanp.industrialtech.common.inventory.container.ModContainers;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.lib.References;
import mightydanp.industrialtech.common.loader.MachineRecipeLoader;
import mightydanp.industrialtech.common.loader.MaterialRecipeLoader;
import mightydanp.industrialtech.common.loader.WorldGenLoader;
import mightydanp.industrialtech.common.tileentity.ModTileEntity;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterMod;
import muramasa.antimatter.datagen.providers.*;
import muramasa.antimatter.registration.RegistrationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(References.ID)
public class IndustrialTech extends AntimatterMod {

    public static IndustrialTech INSTANCE;
    public static Logger LOGGER = LogManager.getLogger(References.ID);
    public static IEventBus MOD_EVENT_BUS;

    public IndustrialTech() {
        super();
        INSTANCE = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        AntimatterAPI.addProvider(References.ID, g -> new AntimatterBlockStateProvider(References.ID, References.NAME + " BlockStates", g));
        AntimatterAPI.addProvider(References.ID, g -> new AntimatterItemModelProvider(References.ID, References.NAME + " Item Models", g));
        AntimatterAPI.addProvider(References.ID, g -> new IndustrialTechBlockTagProvider(References.ID, References.NAME.concat(" Block Tags"), false, g));
        AntimatterAPI.addProvider(References.ID, g -> new AntimatterItemTagProvider(References.ID, References.NAME.concat(" Item Tags"), false, g));
        AntimatterAPI.addProvider(References.ID, g -> new AntimatterFluidTagProvider(References.ID, References.NAME.concat(" Fluid Tags"), false, g));
        AntimatterAPI.addProvider(References.ID, g -> new IndustrialTechRecipes(References.ID, References.NAME.concat(" Recipes"), g));
        AntimatterAPI.addProvider(References.ID, g -> new AntimatterAdvancementProvider(References.ID, References.NAME.concat(" Advancements"), g, new ProgressionAdvancements()));
        AntimatterAPI.addProvider(References.ID, IndustrialTechLocalizations.en_US::new);
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModTileEntity.TILE_ENTITYS.register(modEventBus);

    }

    @SubscribeEvent
    public void setupCommon(final FMLCommonSetupEvent e) {
    }

    @SubscribeEvent
    public void setupClient(final FMLClientSetupEvent e) {

    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event) {
        switch (event) {
            case DATA_INIT:
                Materials.init();
                IndustrialTechData.init();
                Machines.init();
                Guis.init();
                ModBlocks.commonInit();
                ModItems.commonInit();
                ModContainers.commonInit();
                ModTileEntity.commonInit();
                //Models.init();
                break;
            case DATA_READY:
                //Structures.init();
                ModBlocks.clientInit();
                ModItems.clientInit();
                ModContainers.clientInit();
                ModTileEntity.clientInit();
                MaterialRecipeLoader.init();
                MachineRecipeLoader.init();
                //GregTechAPI.registerFluidCell(Data.CellTin.get(1));
                //GregTechAPI.registerFluidCell(Data.CellSteel.get(1));
                //GregTechAPI.registerFluidCell(Data.CellTungstensteel.get(1));

//                AntimatterAPI.registerCover(Data.COVER_PLATE);
//                AntimatterAPI.registerCover(Data.COVER_CONVEYOR);
//                AntimatterAPI.registerCover(Data.COVER_PUMP);

//                AntimatterAPI.registerCoverStack(Data.ConveyorLV.get(1), new CoverConveyor(Tier.LV));
//                AntimatterAPI.registerCoverStack(Data.ConveyorMV.get(1), new CoverConveyor(Tier.MV));
//                AntimatterAPI.registerCoverStack(Data.ConveyorHV.get(1), new CoverConveyor(Tier.HV));
//                AntimatterAPI.registerCoverStack(Data.ConveyorEV.get(1), new CoverConveyor(Tier.EV));
//                AntimatterAPI.registerCoverStack(Data.ConveyorIV.get(1), new CoverConveyor(Tier.IV));
//                AntimatterAPI.registerCoverStack(Data.PumpLV.get(1), new CoverPump(Tier.LV));
//                AntimatterAPI.registerCoverStack(Data.PumpMV.get(1), new CoverPump(Tier.MV));
//                AntimatterAPI.registerCoverStack(Data.PumpHV.get(1), new CoverPump(Tier.HV));
//                AntimatterAPI.registerCoverStack(Data.PumpEV.get(1), new CoverPump(Tier.EV));
//                AntimatterAPI.registerCoverStack(Data.PumpIV.get(1), new CoverPump(Tier.IV));
//                MaterialType.PLATE.all().forEach(m -> AntimatterAPI.registerCoverStack(MaterialType.PLATE.get(m, 1), Data.COVER_PLATE));
                break;
            case WORLDGEN_INIT:
                WorldGenLoader.init();
                break;
        }
    }
}
