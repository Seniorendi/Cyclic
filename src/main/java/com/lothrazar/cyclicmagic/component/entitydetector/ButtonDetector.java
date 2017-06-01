package com.lothrazar.cyclicmagic.component.entitydetector;
import com.lothrazar.cyclicmagic.ModCyclic;
import com.lothrazar.cyclicmagic.component.entitydetector.TileEntityDetector.Fields;
import com.lothrazar.cyclicmagic.gui.GuiButtonTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ButtonDetector extends GuiButtonTooltip {
  private final BlockPos tilePos;
  private Fields type;
  boolean goUp;
  public ButtonDetector(BlockPos current, int buttonId, int x, int y, boolean up, TileEntityDetector.Fields t, int w, int h) {
    super(buttonId, x, y, w, h, "");
    tilePos = current;
    type = t;
    goUp = up;
    String ud = "";
    if (type != Fields.ENTITYTYPE && type != Fields.GREATERTHAN) {
      ud = (up) ? "up" : "down";
    }
    setTooltip("tile.entity_detector." + t.name().toLowerCase() + ud);
  }
  @SideOnly(Side.CLIENT)
  @Override
  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    if (pressed) {
      ModCyclic.network.sendToServer(new PacketTileDetector(tilePos, goUp, type));
    }
    return pressed;
  }
}
