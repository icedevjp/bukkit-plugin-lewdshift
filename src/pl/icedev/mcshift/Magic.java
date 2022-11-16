package pl.icedev.mcshift;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class Magic {

	public static double getYaw(Entity ent) {
		return ((CraftEntity) ent).getHandle().getBukkitYaw();
	}

	public static double getLastYaw(Entity ent) {
		return ((CraftEntity) ent).getHandle().lastYaw;
	}

	public static double normalizeAngleDegrees(double angle) {
		if (angle > 180)
			angle -= 360;
		if (angle < -180)
			angle += 360;
		return angle;
	}
}
