package mightydanp.industrialtech.api.common.jsonconfig.generation.smallore;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
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

import static java.util.stream.Collectors.toList;

public class SmallOreVeinServer extends JsonConfigServer<SmallOreVeinGenFeatureConfig> {

    @Override
    public Map<String, SmallOreVeinGenFeatureConfig> getServerMapFromList(List<SmallOreVeinGenFeatureConfig> oreVeinsIn) {
        Map<String, SmallOreVeinGenFeatureConfig> SmallOreVeinsList = new LinkedHashMap<>();
        oreVeinsIn.forEach(oreVein -> SmallOreVeinsList.put(oreVein.name, oreVein));

        return SmallOreVeinsList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<SmallOreVeinGenFeatureConfig> list = message.getConfig(IndustrialTech.configSync.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureConfig.class::isInstance)
                .map(SmallOreVeinGenFeatureConfig.class::cast)
                .collect(toList());

        if(list.size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("ore_vein", sync.get());
            return false;
        }

        getServerMap().forEach((name, smallOreVein) -> {
            sync.set(list.stream().anyMatch(o -> o.name.equals(name)));

            if(sync.get()) {
                Optional<SmallOreVeinGenFeatureConfig> optional = list.stream().filter(o -> o.name.equals(name)).findFirst();

                if(optional.isPresent()) {
                    SmallOreVeinGenFeatureConfig serverSmallOreVein = optional.get();
                    JsonObject jsonMaterial = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).toJsonObject(smallOreVein);
                    JsonObject materialJson = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).toJsonObject(serverSmallOreVein);

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
        Map<String, SmallOreVeinGenFeatureConfig> clientSmallOreVeins = new HashMap<>();

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
                JsonObject jsonObject = IndustrialTech.configSync.smallOre.getFirst().getJsonObject(file.getName());
                SmallOreVeinGenFeatureConfig oreVein = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).getFromJsonObject(jsonObject);
                clientSmallOreVeins.put(oreVein.name, oreVein);
            }

            getServerMap().values().forEach(serverSmallOreVein -> {
                sync.set(clientSmallOreVeins.containsKey(serverSmallOreVein.name));

                if(sync.get()) {
                    SmallOreVeinGenFeatureConfig clientSmallOreVein = getServerMap().get(serverSmallOreVein.name);
                    JsonObject jsonMaterial = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).toJsonObject(serverSmallOreVein);
                    JsonObject materialJson = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).toJsonObject(clientSmallOreVein);

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

        for (SmallOreVeinGenFeatureConfig oreVein : getServerMap().values()) {
            String name = oreVein.name;
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).toJsonObject(oreVein);
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
                    JsonObject jsonObject = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).getJsonObject(file.getName());
                    SmallOreVeinGenFeatureConfig oreVein = ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).getFromJsonObject(jsonObject);

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
        List<SmallOreVeinGenFeatureConfig> list = message.getConfig(IndustrialTech.configSync.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureConfig.class::isInstance)
                .map(SmallOreVeinGenFeatureConfig.class::cast)
                .collect(toList());

        Map<String, SmallOreVeinGenFeatureConfig> SmallOreVeins = list.stream()
                .collect(Collectors.toMap(s -> s.name, s -> s));

        serverMap.clear();
        serverMap.putAll(SmallOreVeins);

        IndustrialTech.LOGGER.info("Loaded {} ore veins from the server", SmallOreVeins.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, SmallOreVeinGenFeatureConfig oreVein) {//friendlybotbuff
        buffer.writeUtf(oreVein.name);
        buffer.writeInt(oreVein.rarity);
        buffer.writeInt(oreVein.minHeight);
        buffer.writeInt(oreVein.maxHeight);
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
        List<SmallOreVeinGenFeatureConfig> list = message.getConfig(IndustrialTech.configSync.smallOreID).stream()
                .filter(SmallOreVeinGenFeatureConfig.class::isInstance)
                .map(SmallOreVeinGenFeatureConfig.class::cast)
                .collect(toList());

        buffer.writeVarInt(list.size());

        list.forEach((smallOreVein) -> {
            singleToBuffer(buffer, smallOreVein);
        });
    }

    @Override
    public SmallOreVeinGenFeatureConfig singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();
        int rarity = buffer.readInt();
        int minHeight = buffer.readInt();
        int maxHeight = buffer.readInt();
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
        return new SmallOreVeinGenFeatureConfig(name, rarity, minHeight, maxHeight, biomes, veinBlocksAndChances);
    }

    @Override
    public List<SmallOreVeinGenFeatureConfig> multipleFromBuffer(PacketBuffer buffer) {
        List<SmallOreVeinGenFeatureConfig> oreVeins = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            SmallOreVeinGenFeatureConfig oreVein = singleFromBuffer(buffer);

            oreVeins.add(oreVein);
        }

        return oreVeins;
    }

}