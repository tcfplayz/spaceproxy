package pfg.party;

import java.util.HashMap;
import java.util.UUID;

public class CacheParties {

    private static HashMap<UUID, PartyInstance> cache = new HashMap<>();

    public static void addParty(PartyInstance party, UUID uuid) {
        cache.put(uuid, party);
    }

    public static PartyInstance getParty(UUID uuid) {
        return cache.get(uuid);
    }

}
