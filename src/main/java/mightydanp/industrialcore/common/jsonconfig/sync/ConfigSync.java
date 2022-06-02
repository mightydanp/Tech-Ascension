package mightydanp.industrialcore.common.jsonconfig.sync;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.handler.NetworkHandler;
import mightydanp.industrialcore.common.jsonconfig.flag.MaterialFlagRegistry;
import mightydanp.industrialcore.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.FluidStateServer;
import mightydanp.industrialcore.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.industrialcore.common.jsonconfig.generation.blocksinwater.BlocksInWaterServer;
import mightydanp.industrialcore.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialcore.common.jsonconfig.generation.orevein.OreVeinServer;
import mightydanp.industrialcore.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.industrialcore.common.jsonconfig.generation.randomsurface.RandomSurfaceServer;
import mightydanp.industrialcore.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.industrialcore.common.jsonconfig.generation.smallore.SmallOreVeinServer;
import mightydanp.industrialcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialcore.common.jsonconfig.icons.TextureIconServer;
import mightydanp.industrialcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialcore.common.jsonconfig.material.data.MaterialServer;
import mightydanp.industrialcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.industrialcore.common.jsonconfig.material.ore.OreTypeServer;
import mightydanp.industrialcore.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialcore.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.industrialcore.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.industrialcore.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialcore.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialcore.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.industrialcore.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialcore.common.jsonconfig.tool.type.ToolTypeServer;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * Created by MightyDanp on 1/4/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ConfigSync {
    public Map<String, Boolean> syncedJson = new HashMap<>();
    public Map<Integer, Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>> configs = new HashMap<>();
    public int materialFlagID = 0;
    public int fluidStateID = 1;
    public int oreTypeID = 2;
    public int textureIconID = 3;
    public int toolPartID = 4;
    public int toolTypeID = 5;
    public int stoneLayerID = 6;
    public int materialID = 7;
    public int oreVeinID = 8;
    public int smallOreID = 9;
    public int blocksInWaterID = 10;
    public int randomSurfaceID = 11;


    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> materialFlag = configs.getOrDefault(materialFlagID, new Pair<>(new MaterialFlagRegistry(),  new MaterialFlagServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> fluidState = configs.getOrDefault(fluidStateID, new Pair<>(new FluidStateRegistry(),  new FluidStateServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreType = configs.getOrDefault(oreTypeID, new Pair<>(new OreTypeRegistry(),  new OreTypeServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  textureIcon = configs.getOrDefault(textureIconID, new Pair<>(new TextureIconRegistry(),  new TextureIconServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolPart = configs.getOrDefault(toolPartID, new Pair<>(new ToolPartRegistry(),  new ToolPartServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolType = configs.getOrDefault(toolTypeID, new Pair<>(new ToolTypeRegistry(),  new ToolTypeServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  stoneLayer = configs.getOrDefault(stoneLayerID, new Pair<>(new StoneLayerRegistry(),  new StoneLayerServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  material = configs.getOrDefault(materialID, new Pair<>(new MaterialRegistry(),  new MaterialServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreVein = configs.getOrDefault(oreVeinID, new Pair<>(new OreVeinRegistry(),  new OreVeinServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  smallOre = configs.getOrDefault(smallOreID, new Pair<>(new SmallOreVeinRegistry(),  new SmallOreVeinServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  blocksInWater = configs.getOrDefault(blocksInWaterID, new Pair<>(new BlocksInWaterRegistry(),  new BlocksInWaterServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  randomSurface = configs.getOrDefault(randomSurfaceID, new Pair<>(new RandomSurfaceRegistry(),  new RandomSurfaceServer()));

    public boolean isSinglePlayer;
    public String singlePlayerWorldName;
    public static String serverIP;

    public void init(){
        configs.put(materialFlagID, materialFlag);
        configs.put(fluidStateID, fluidState);
        configs.put(oreTypeID, oreType);
        configs.put(textureIconID, textureIcon);
        configs.put(toolPartID, toolPart);
        configs.put(toolTypeID, toolType);
        configs.put(stoneLayerID, stoneLayer);
        configs.put(materialID, material);
        configs.put(oreVeinID, oreVein);
        configs.put(smallOreID, smallOre);
        configs.put(blocksInWaterID, blocksInWater);
        configs.put(randomSurfaceID, randomSurface);


        for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
            Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);

            config.getFirst().initiate();
            configs.put(i, config);
        }
    }

    public void initClient(){
        configs.forEach((s, pair) -> {
            pair.getFirst().initiateClient();
            configs.put(s, pair);
        });
    }


    @SubscribeEvent
    public static void serverSyncBlocks(RegistryEvent.MissingMappings<Block> event) {
        Map<ResourceLocation, Block> missing = new HashMap<>();
        event.getMappings(Ref.mod_id).forEach(o -> missing.put(o.key, o.getTarget()));

    }

    @SubscribeEvent
    public static void serverSyncItems(RegistryEvent.MissingMappings<Item> event) {
        Map<ResourceLocation, Item> missing = new HashMap<>();
        event.getMappings(Ref.mod_id).forEach(o -> missing.put(o.key, o.getTarget()));
    }

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer().getServer() != null) {
            boolean isSinglePlayer = event.getPlayer().getServer().isSingleplayer();
            String singlePlayerWorldName = isSinglePlayer ? event.getPlayer().getServer().getWorldPath(LevelResource.ROOT).getParent().getFileName().toString(): "";/////////

            SyncMessage syncMessage = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

            for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                syncMessage.setConfig(i, config.getFirst().getAllValues());
            }

            ServerPlayer player = (ServerPlayer) event.getPlayer();

            if (player != null) {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), syncMessage);
            } else {
                NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), syncMessage);
            }
            /*
            if(IndustrialTech.configSync.syncedJson.containsValue(false)){
                NetworkScreenHandler.openClientGui((ServerPlayerEntity)event.getPlayer(), RefScreenIDs.syncScreenId);
            }
            */

        }
    }

    @SubscribeEvent
    public static void tagUpdate(TagsUpdatedEvent event) {

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerJoinServer(ClientPlayerNetworkEvent.LoggedInEvent event) {
        if (event.getPlayer() != null) {
            if (IndustrialTech.configSync.syncedJson.containsValue(false)) {
                if (Minecraft.getInstance().level != null && Minecraft.getInstance().getCurrentServer() != null && Minecraft.getInstance().screen != null) {
                    serverIP = Minecraft.getInstance().getCurrentServer().ip;
                    Minecraft.getInstance().level.disconnect();
                }
            }
        }
    }

    /*
    @SubscribeEvent
    public static void onLoadChunk(ChunkEvent.Load event){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server != null){
            if(server.isSingleplayer()) {
                if (!IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id  + "/" + Ref.mod_id)) {
                    server.close();
                }
            }
        }
    }

     */

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onDisconnect(ScreenOpenEvent event) {
        SyncScreen screen = new SyncScreen();

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if(event.getScreen() != null) {
            IndustrialTech.LOGGER.debug(event.getScreen());
            IndustrialTech.LOGGER.debug(event.getScreen().getTitle().getString());

            if (server != null) {
                if (server.isSingleplayer() && Minecraft.getInstance().getSingleplayerServer() != null) {
                        IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();

                    if (event.getScreen() instanceof ProgressScreen) {
                        if (!(new File("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id).exists()) && !IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id)) {
                            for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                                try {
                                    config.getSecond().syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                            IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                            IndustrialTech.mainJsonConfig.reloadMainConfigJson();
                        }
                    }

                    if (event.getScreen() instanceof LevelLoadingScreen) {
                        if((new File("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id).exists())) {
                            if (!IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id)) {
                                IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                                IndustrialTech.mainJsonConfig.reloadMainConfigJson();
                                Minecraft.getInstance().close();
                            }
                        }
                    }
                }
            }

            if (event.getScreen() instanceof DisconnectedScreen) {
                if (IndustrialTech.configSync.syncedJson.containsValue(false)) {
                    event.setScreen(screen);
                }
            }
        }
    }






}
