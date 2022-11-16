package pl.icedev.mcshift;

import static org.bukkit.entity.EntityType.*;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.potion.*;

public class Posuwanie implements Listener, Runnable {
	private static final Map<EntityType, Sound> sounds = new HashMap<>();
	static {
		sounds.put(EntityType.BLAZE, Sound.ENTITY_BLAZE_DEATH);
		sounds.put(EntityType.BOAT, Sound.ENTITY_BOAT_PADDLE_LAND);
		sounds.put(EntityType.CAVE_SPIDER, Sound.ENTITY_SPIDER_DEATH);
		sounds.put(EntityType.CHICKEN, Sound.ENTITY_CHICKEN_DEATH);
		sounds.put(EntityType.SHEEP, Sound.ENTITY_SHEEP_DEATH);
		sounds.put(EntityType.COW, Sound.ENTITY_COW_DEATH);
		sounds.put(EntityType.ENDERMAN, Sound.ENTITY_ENDERMAN_DEATH);
		sounds.put(EntityType.PLAYER, Sound.ENTITY_DONKEY_AMBIENT);
		sounds.put(EntityType.BEE, Sound.ENTITY_BEE_LOOP_AGGRESSIVE);
		sounds.put(EntityType.WOLF, Sound.ENTITY_WOLF_HOWL);
		sounds.put(EntityType.VILLAGER, Sound.ENTITY_VILLAGER_DEATH);
		sounds.put(EntityType.HORSE, Sound.ENTITY_HORSE_DEATH);
		sounds.put(EntityType.DONKEY, Sound.ENTITY_DONKEY_DEATH);
		sounds.put(EntityType.CAT, Sound.ENTITY_CAT_DEATH);
		sounds.put(EntityType.PLAYER, Sound.ENTITY_DONKEY_ANGRY);
	}

	ValueMap<Entity> wyruchane = new ValueMap<>(300);

	public Posuwanie(LewdPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 120, 120);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		boolean sneaking = event.getPlayer().isSneaking();

		if (!sneaking) {
			Player actor = event.getPlayer();

			if (actor.getGameMode() == GameMode.SPECTATOR)
				return;
			if (actor.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
				return;

			Location loc = actor.getLocation();

			double myLookAngle = Magic.normalizeAngleDegrees(loc.getYaw());

			List<Entity> entities = actor.getNearbyEntities(1, 0.5f, 1);

			for (Entity ent : entities) {

				if (ent.isDead())
					continue;
				if (ent.isInsideVehicle())
					continue;
				if (ent.isInvulnerable())
					continue;

				if (!(ent instanceof LivingEntity))
					continue;

				LivingEntity living = (LivingEntity) ent;

				if (living.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
					continue;
				if (living.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
					continue;
				if (living.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
					continue;
				if (living.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
					continue;

				Location eloc = ent.getLocation();

//	    		Bukkit.broadcastMessage("target: " + eloc.getYaw() + " or " + Magic.getLastYaw(ent));

				float dx = (float) (eloc.getX() - loc.getX());
				float dz = (float) (eloc.getZ() - loc.getZ());

				double targetAngle = Magic.normalizeAngleDegrees(Math.toDegrees(Math.atan2(-dx, dz)));
				double targetLookAngle = Magic.normalizeAngleDegrees(Magic.getLastYaw(ent));

				if (Math.abs(Magic.normalizeAngleDegrees(targetAngle - myLookAngle)) > 30)
					continue;
				if (Math.abs(Magic.normalizeAngleDegrees(targetLookAngle - myLookAngle)) > 30)
					continue;

				int nval = living.hasPotionEffect(PotionEffectType.WEAKNESS) ? 10 : wyruchane.increment(ent, 1);

				if (ent instanceof Creeper && nval >= 5) {
					Bukkit.broadcastMessage(ChatColor.GOLD + "Gracz " + actor.getDisplayName() + " wyruchał creepera.");
					Location el = ent.getLocation();
					ent.remove();
					ent.getWorld().createExplosion(el.getX(), el.getY() + 1, el.getZ(), 5, true, true);
					continue;
				}

				if (nval >= 10) {

					EntityType type = living.getType();

					boolean addSlowDigging = true;

					if (type == WITHER_SKELETON) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 30, 1, true, false, true));
					}

					if (type == COD || type == TROPICAL_FISH || type == SALMON) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 30, 0, true, false, true));
					}

					if (type == BAT) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 30, 0, true, false, true));
					}

					if (type == BEE) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 30, 0, true, false, true));
					}

					if (type == RABBIT) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 1, true, false, true));
					}

					if (type == BLAZE) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 30, 1, true, false, true));
					}

					if (type == DONKEY) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 20 * 30, 1, true, false, true));
					}

					if (type == PARROT) {
						addSlowDigging = false;
						actor.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 30, 1, true, false, true));
						// living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 30, 0,
						// true, false, true));
					}

					if (type == CAVE_SPIDER || type == SPIDER) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 30, 1, true, false, true));
					}

					if (addSlowDigging) {
						actor.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 1, true, false, true));
					}

					if (type == CHICKEN) {
						if (Math.random() < 0.1) {
							living.setCustomName("baphomet");
							living.setCustomNameVisible(true);
							living.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 300, 0, true, false, true));
							living.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 300, 2, true, false, true));
							actor.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 300, 2, true, false, true));
							actor.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 300, 0, true, false, true));
						}
					}

					broadcastShifted(actor, living);

					Sound ds = sounds.get(living.getType());

					if (ds != null) {
//        				Bukkit.getOnlinePlayers().forEach(p->{
//        					p.playSound(p.getLocation(), ds, 16, 0.7f);
//        				});
						actor.getWorld().playSound(actor.getLocation(), ds, 16, 0.5f);
					}

					double x = loc.getX() / 2 + eloc.getX() / 2;
					double z = loc.getZ() / 2 + eloc.getZ() / 2;
					double y = loc.getY() + 1;

					actor.getWorld().spawnParticle(Particle.CLOUD, x, y, z, 2048, 0.1, 0.1, 0.1, 0.15);
					actor.getWorld().playSound(actor.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 16, 0.5f);
					actor.getWorld().playSound(actor.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 16, 0.5f);

					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 30, 0, true, false, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 30, 0, true, false, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 30, 0, true, false, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 30, 0, true, true, true));
					if (Math.random() < 0.1)
						living.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0, true, true, true));
					// living.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 60,
					// 1, true, true));
					living.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 1, true, true, true));

					if (living instanceof Creature) {
						((Creature) living).setTarget(actor);
					}

					if (living instanceof Villager || living instanceof Animals) {
						List<Entity> nearbyEntities = living.getNearbyEntities(32, 20, 32);

						for (var entity : nearbyEntities) {
							if (entity instanceof IronGolem) {
								if (((IronGolem) entity).getTarget() == null) {
									((IronGolem) entity).setTarget(actor);
								}
							}
						}
					}

					if (living instanceof Player) {

						if (Math.random() < 0.05) {
							Zombie gunwielek = (Zombie) living.getWorld().spawnEntity(living.getLocation(), EntityType.HUSK);
							gunwielek.setBaby();
							gunwielek.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 300, 3, true, true, true));
							gunwielek.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 300, 3, true, true, true));
							gunwielek.setCustomName("gunwielek");
							// drowned.setShouldBurnInDay(false);
							gunwielek.setRemoveWhenFarAway(false);
//    	    				drowned.setCustomNameVisible(true);
						}

						String title = "masz ból dupy";
						String sub = "wyruchał cię " + actor.getName();
						((Player) living).sendTitle(title, sub, 20, 20 * 15, 20);
					}

					wyruchane.remove(ent, 0);
				} else {
					actor.getWorld().playSound(actor.getLocation(), nval % 2 == 0 ? Sound.BLOCK_PISTON_EXTEND : Sound.BLOCK_PISTON_CONTRACT, 0.7f, 1.5f);
				}
			}
		}
	}

	private MessageGenerator messages = new MessageGenerator();

	private void broadcastShifted(Player actor, Entity target) {
		String msg = messages.createMessage(actor, target);
		Bukkit.broadcastMessage(msg);
	}

	@Override
	public void run() {
		wyruchane.subtractAll();
	}
}
