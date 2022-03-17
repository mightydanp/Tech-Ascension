package mightydanp.industrialtech.api.common.jsonconfig.generation.orevein;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class OreVeinServer extends JsonConfigServer<OreVeinGenFeatureConfig> {

    @Override
    public Map<String, OreVeinGenFeatureConfig> getServerMapFromList(List<OreVeinGenFeatureConfig> oreVeinsIn) {
        Map<String, OreVeinGenFeatureConfig> OreVeinsList = new LinkedHashMap<>();
        oreVeinsIn.forEach(oreVein -> OreVeinsList.put(oreVein.name, oreVein));

        return OreVeinsList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getOreVeins().size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        getServerMap().forEach((name, OreVein) -> {
            sync.set(message.getOreVeins().stream().anyMatch(o -> o.name.equals(name)));

            if(sync.get()) {
                Optional<OreVeinGenFeatureConfig> optional = message.getOreVeins().stream().filter(o -> o.name.equals(name)).findFirst();

                if(optional.isPresent()) {
                    OreVeinGenFeatureConfig serverOreVein = optional.get();
                    JsonObject jsonMaterial = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).toJsonObject(OreVein);
                    JsonObject materialJson = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).toJsonObject(serverOreVein);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, OreVeinGenFeatureConfig> clientOreVeins = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/ore_vein");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("ore_vein", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).getJsonObject(file.getName());
                OreVeinGenFeatureConfig oreVein = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).getFromJsonObject(jsonObject);
                clientOreVeins.put(oreVein.name, oreVein);
            }

            getServerMap().values().forEach(serverOreVein -> {
                sync.set(clientOreVeins.containsKey(serverOreVein.name));

                if(sync.get()) {
                    OreVeinGenFeatureConfig clientOreVein = getServerMap().get(serverOreVein.name);
                    JsonObject jsonMaterial = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).toJsonObject(serverOreVein);
                    JsonObject materialJson = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).toJsonObject(clientOreVein);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/generation"+ "/ore_vein");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (OreVeinGenFeatureConfig oreVein : getServerMap().values()) {
            String name = oreVein.name;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).toJsonObject(oreVein);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/generation" + "/ore_vein");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation"  + "/ore_vein");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.oreVein.getFirst().getJsonObject(file.getName());
                    OreVeinGenFeatureConfig oreVein = ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).getFromJsonObject(jsonObject);

                    String name = oreVein.name;

                    Path materialFile = Paths.get(singlePlayerSaveConfigFolder + "/" + name + ".json");
                    if (!Files.exists(materialFile)) {
                        Files.createDirectories(materialFile.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                            String s = GSON.toJson(jsonObject);
                            bufferedwriter.write(s);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void loadFromServer(SyncMessage message) {
        Map<String, OreVeinGenFeatureConfig> OreVeins = message.getOreVeins().stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(OreVeins);

        IndustrialTech.LOGGER.info("Loaded {} ore veins from the server", OreVeins.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, OreVeinGenFeatureConfig oreVein) {//friendlybotbuff
        buffer.writeUtf(oreVein.name);
        buffer.writeInt(oreVein.rarity);
        buffer.writeInt(oreVein.minHeight);
        buffer.writeInt(oreVein.maxHeight);
        buffer.writeInt(oreVein.minRadius);
        buffer.writeInt(oreVein.minNumberOfSmallOreLayers);
        buffer.writeInt(oreVein.biomes.size());
        for(String biome : oreVein.biomes){
            buffer.writeUtf(biome);
        }

        buffer.writeInt(oreVein.blocksAndChances.size());

        for(Pair<String, Integer> veinBlockAndChance : oreVein.blocksAndChances){
            buffer.writeUtf(veinBlockAndChance.getFirst());
            buffer.writeInt(veinBlockAndChance.getSecond());
        }
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getOreVeins().size());

        message.getOreVeins().forEach((oreVein) -> {
            singleToBuffer(buffer, oreVein);
        });
    }

    @Override
    public OreVeinGenFeatureConfig singleFromBuffer(PacketBuffer buffer) {
        String veinName = buffer.readUtf();
        int rarity = buffer.readInt();
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
        int minRadius = buffer.readInt();
        int minNumberOfSmallOreLayers = buffer.readInt();
        int biomesSize = buffer.readInt();
        List<String> biomes = new ArrayList<>();
        for(int i = 0; i < biomesSize; i++){
            String biome = buffer.readUtf();
            biomes.add(biome);
        }

        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();

        int veinBlocksAndChancesSize = buffer.readInt();
        for(int i = 0; i < veinBlocksAndChancesSize; i++){
            String block = buffer.readUtf();
            int chance = buffer.readInt();
            veinBlocksAndChances.add(new Pair<>(block, chance));
        }
        return new OreVeinGenFeatureConfig(veinName, rarity, minHeight, maxHeight, minRadius, minNumberOfSmallOreLayers, biomes, veinBlocksAndChances);
    }

    @Override
    public List<OreVeinGenFeatureConfig> multipleFromBuffer(PacketBuffer buffer) {
        List<OreVeinGenFeatureConfig> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            OreVeinGenFeatureConfig oreVein = singleFromBuffer(buffer);

            oreVeins.add(oreVein);
        }

        return oreVeins;
    }

}