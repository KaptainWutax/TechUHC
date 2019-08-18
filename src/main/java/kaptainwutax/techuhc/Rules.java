package kaptainwutax.techuhc;

import net.minecraft.entity.EntityCategory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Rules {

    public static final Map<String, Boolean> BOOLEAN_RULES = new HashMap<>();
    public static final Map<String, Integer> INTEGER_RULES = new HashMap<>();

    static {
        BOOLEAN_RULES.put("animal_ai", true);

        for(EntityCategory ec: EntityCategory.values()) {
            BOOLEAN_RULES.put(ec.getName() + "_spawn", true);
        }

        BOOLEAN_RULES.put("random_ticks", true);
        BOOLEAN_RULES.put("tick_unloaded_chunks", false);
    }

}
