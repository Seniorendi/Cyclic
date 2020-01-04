package com.lothrazar.cyclic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.cyclic.event.EventHandler;
import com.lothrazar.cyclic.registry.PacketRegistry;
import com.lothrazar.cyclic.setup.ClientProxy;
import com.lothrazar.cyclic.setup.ConfigHandler;
import com.lothrazar.cyclic.setup.IProxy;
import com.lothrazar.cyclic.setup.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModCyclic.MODID)
public class ModCyclic {

  public static final String certificateFingerprint = "@FINGERPRINT@";
  public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
  public static final String MODID = "cyclic";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModCyclic() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    //only for server starting
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new EventHandler());
    ConfigHandler.loadConfig(ConfigHandler.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + ".toml"));
  }

  private void setup(final FMLCommonSetupEvent event) {
    //now all blocks/items exist
    PacketRegistry.init();
    proxy.init();
    //TODO: LOOP 
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.item_scaffold_fragile);
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.soundproofing);
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.excavate);//y
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.experience_boost);//y
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.life_leech);//y
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.magnet);//y
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.multishot);//y
    //    MinecraftForge.EVENT_BUS.register(CyclicRegistry.smelting);//  ?
    MinecraftForge.EVENT_BUS.register(CyclicRegistry.Enchants.venom);//y
  }

  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    //you probably will not need this
  }

  @SubscribeEvent
  public static void onFingerprintViolation(FMLFingerprintViolationEvent event) {
    // https://tutorials.darkhax.net/tutorials/jar_signing/
    String source = (event.getSource() == null) ? "" : event.getSource().getName() + " ";
    String msg = MODID + "Invalid fingerprint detected! The file " + source + "may have been tampered with. This version will NOT be supported by the author!";
    //    System.out.println(msg);
  }
}