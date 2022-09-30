package pl.icedev.mcshift;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.entity.Villager.Profession;

public class MessageGenerator {

	private static final Map<EntityType, String> mappings = new HashMap<>();
	
	static {
		mappings.put(EntityType.ARROW, "strzałę");
		mappings.put(EntityType.BLAZE, "blejza");
		mappings.put(EntityType.BOAT, "łódkę");
		mappings.put(EntityType.CAVE_SPIDER, "trującego pająka");
		mappings.put(EntityType.CHICKEN, "kurczaka");
		mappings.put(EntityType.SHEEP, "owcę");
		mappings.put(EntityType.COW, "krowę");
		mappings.put(EntityType.ENDERMAN, "endermana");
		mappings.put(EntityType.ENDERMITE, "endermita");
		mappings.put(EntityType.SILVERFISH, "rybika");
		mappings.put(EntityType.SKELETON, "szkieleta");
		mappings.put(EntityType.GHAST, "ghasta");
		mappings.put(EntityType.CREEPER, "creepera");
		mappings.put(EntityType.RABBIT, "króliczka");
		mappings.put(EntityType.ZOMBIE, "zombiaka");
		mappings.put(EntityType.WOLF, "wilka");
		mappings.put(EntityType.WITCH, "wiedźmę");
		mappings.put(EntityType.VILLAGER, "osadnika");
		mappings.put(EntityType.ZOMBIE_VILLAGER, "osadnika zombie");
		mappings.put(EntityType.PANDA, "pandę");
		mappings.put(EntityType.PARROT, "papugę");
		mappings.put(EntityType.TURTLE, "żółwia");
		mappings.put(EntityType.FOX, "lisa");
		mappings.put(EntityType.SQUID, "kałamarnicę");
		mappings.put(EntityType.SPIDER, "pająka");
		mappings.put(EntityType.SNOWMAN, "bałwana");
		mappings.put(EntityType.IRON_GOLEM, "żelaznego golema");
		mappings.put(EntityType.BAT, "nietoperza");
		mappings.put(EntityType.PIGLIN, "piglina");
		mappings.put(EntityType.PIGLIN_BRUTE, "piglina brutala");
		mappings.put(EntityType.ZOMBIFIED_PIGLIN, "zombiaka piglina");
		mappings.put(EntityType.STRIDER, "magmołaza");
		mappings.put(EntityType.HOGLIN, "hoglina");
		mappings.put(EntityType.ZOGLIN, "zoglina");
		mappings.put(EntityType.SLIME, "slajma");
		mappings.put(EntityType.PIG, "świnię");
		mappings.put(EntityType.MAGMA_CUBE, "magmę");
		mappings.put(EntityType.HORSE, "konia");
		mappings.put(EntityType.SKELETON_HORSE, "konia szkieleta");
		mappings.put(EntityType.ZOMBIE_HORSE, "konia zombie");
		mappings.put(EntityType.MUSHROOM_COW, "muchomora");
		mappings.put(EntityType.OCELOT, "ocelota");
		mappings.put(EntityType.ARMOR_STAND, "stojak na zbroje");
		mappings.put(EntityType.MINECART, "gunwonik");
		mappings.put(EntityType.LLAMA, "lamę");
		mappings.put(EntityType.FISHING_HOOK, "spławik");
		mappings.put(EntityType.VINDICATOR, "windykatora");
		mappings.put(EntityType.VEX, "dręczyciela");
		mappings.put(EntityType.SHULKER, "szulkera");
		mappings.put(EntityType.RAVAGER, "bestię szabrowniczą");
		mappings.put(EntityType.MULE, "muła");
		mappings.put(EntityType.DONKEY, "osła");
		mappings.put(EntityType.TRADER_LLAMA, "lamę kupiecką");
		mappings.put(EntityType.WANDERING_TRADER, "wędrownego kupca");
		mappings.put(EntityType.PHANTOM, "fantoma");
		mappings.put(EntityType.POLAR_BEAR, "niedźwiedzia polarnego");
		mappings.put(EntityType.DROWNED, "utopca");
		mappings.put(EntityType.HUSK, "posucha");
		mappings.put(EntityType.CAT, "kota");
		mappings.put(EntityType.PILLAGER, "rozbójnika");
		mappings.put(EntityType.VINDICATOR, "rzecznika");
		mappings.put(EntityType.TROPICAL_FISH, "rybę tropikalną");
		mappings.put(EntityType.SALMON, "łososia");
		mappings.put(EntityType.COD, "dorsza");
		mappings.put(EntityType.EVOKER, "przywoływacza");
		mappings.put(EntityType.DOLPHIN, "delfina");
		mappings.put(EntityType.STRAY, "tułacza");
		mappings.put(EntityType.WITHER_SKELETON, "mrocznego szkieleta");
		mappings.put(EntityType.WITHER, "withera");
		mappings.put(EntityType.GUARDIAN, "strażnika");
		mappings.put(EntityType.ELDER_GUARDIAN, "starszego strażnika");
		mappings.put(EntityType.ENDER_DRAGON, "smoka");
		mappings.put(EntityType.ILLUSIONER, "iluzjonistę");
		
		// 1.18
		mappings.put(EntityType.BEE, "pszczołę");
		mappings.put(EntityType.AXOLOTL, "axolota");
		mappings.put(EntityType.GOAT, "kozę");
		mappings.put(EntityType.GLOW_SQUID, "świecącą kałamarnicę");
		
		// 1.19
		mappings.put(EntityType.WARDEN, "nadzorcę");
		mappings.put(EntityType.FROG, "żabę");
		mappings.put(EntityType.TADPOLE, "kijankę");
		mappings.put(EntityType.ALLAY, "otuszka");
	}
	
	private String profesja(Profession prof) {
		switch(prof) {
		case ARMORER:
			return "kowala";
		case BUTCHER:
			return "rzeźnika";
		case CARTOGRAPHER:
			return "kartografa";
		case CLERIC:
			return "kapłana";
		case FARMER:
			return "rolnika";
		case FISHERMAN:
			return "rybaka";
		case FLETCHER:
			return "łuczarza";
		case LEATHERWORKER:
			return "rymarza";
		case LIBRARIAN:
			return "bibliotekarza";
		case MASON:
			return "kamieniarza";
		case SHEPHERD:
			return "pasterza";
		case TOOLSMITH:
			return "narządziarza";
		case WEAPONSMITH:
			return "zbrojmistrza";
		case NITWIT:
			return "głupca";
		case NONE:
			break;
		default:
			break;
		}
		return null;
	}
	
	private String getEntityNamed(Entity ent) {
		
		String text = mappings.get(ent.getType());
		
		
		if(text == null) {
			if(ent.getType() == EntityType.PLAYER) {
				return "gracza " + ((Player)ent).getDisplayName();
			} else {
				text = ent.getName();
			}
		}

		if(ent instanceof Ageable) {
			if(!((Ageable) ent).isAdult()) {
				text = "dziecko " + text;
			}
		}

		if(ent.getType() == EntityType.VILLAGER) {
			Villager v = (Villager) ent;
			Profession prof = v.getProfession();
			if(prof != Profession.NONE) {
				text += " " + profesja(prof);
			}
		}
		
		if(ent.getCustomName() != null) {
			text = text + " " + ent.getCustomName();
		}
		
		
		return text;
	}

	public String createMessage(Player player, Entity buttfucced) {
		String nazwa = getEntityNamed(buttfucced);
		
		return "Gracz " + player.getName() + " wyruchał " + nazwa + ".";
	}
	
}
