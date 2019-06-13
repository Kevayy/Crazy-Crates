package me.badbones69.crazycrates.multisupport.nms.v1_13_R2;

import me.badbones69.crazycrates.multisupport.nms.NMSSupport;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.TileEntityChest;
import net.minecraft.server.v1_13_R2.TileEntityEnderChest;
import net.minecraft.server.v1_13_R2.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NMS_v1_13_R2 implements NMSSupport {
	
	@Override
	public void openChest(Block block, Boolean open) {
		Material type = block.getType();
		if(type == Material.CHEST || type == Material.TRAPPED_CHEST || type == Material.ENDER_CHEST) {
			World world = ((CraftWorld) block.getWorld()).getHandle();
			BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
			if(block.getType() == Material.ENDER_CHEST) {
				TileEntityEnderChest tileChest = (TileEntityEnderChest) world.getTileEntity(position);
				world.playBlockAction(position, tileChest.getBlock().getBlock(), 1, open ? 1 : 0);
			}else {
				TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
				world.playBlockAction(position, tileChest.getBlock().getBlock(), 1, open ? 1 : 0);
			}
		}
	}
	
	//https://www.spigotmc.org/threads/pasting-schematics-in-1-13.333643/#post-3312204
	@Override
	public void pasteSchematic(File f, Location loc) {
		try {
			Location[] locations = StructureService.normalizeEdges(loc, StructureService.getOtherEdge(f, loc));
			int[] dimensions = StructureService.getDimensions(locations);
			//Math.floor allows it to round a double to the lowest whole number
			StructureService.loadAndInsertAny(f, loc.subtract(Math.floor(dimensions[0] / 2), 1, Math.floor(dimensions[2] / 2)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Location> getLocations(File f, Location loc) {
		try {
			Location[] locations = StructureService.normalizeEdges(loc, StructureService.getOtherEdge(f, loc));
			int[] dimensions = StructureService.getDimensions(locations);
			return StructureService.getSingleStructureLocations(f, loc.subtract(Math.floor(dimensions[0] / 2), 1, Math.floor(dimensions[2] / 2)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Material> getQuadCrateBlacklistBlocks() {
		List<Material> blockList = new ArrayList<>();
		blockList.add(Material.SIGN);
		blockList.add(Material.WALL_SIGN);
		blockList.add(Material.STONE_BUTTON);
		blockList.add(Material.BIRCH_BUTTON);
		blockList.add(Material.ACACIA_BUTTON);
		blockList.add(Material.DARK_OAK_BUTTON);
		blockList.add(Material.JUNGLE_BUTTON);
		blockList.add(Material.OAK_BUTTON);
		blockList.add(Material.SPRUCE_BUTTON);
		blockList.add(Material.STONE_BUTTON);
		return blockList;
	}
	
}