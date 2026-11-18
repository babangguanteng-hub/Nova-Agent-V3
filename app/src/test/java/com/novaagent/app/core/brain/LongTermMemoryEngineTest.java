package com.novaagent.app.core.brain;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class LongTermMemoryEngineTest {

    @Test
    public void testScoredFactRanking() {
        List<LongTermMemoryEngine.ScoredFact> list = new ArrayList<>();
        list.add(new LongTermMemoryEngine.ScoredFact("User suka teh", "general", 2, 1000L));
        list.add(new LongTermMemoryEngine.ScoredFact("User suka kopi tubruk tanpa gula", "preference", 5, 1200L));
        list.add(new LongTermMemoryEngine.ScoredFact("User minum air putih", "general", 1, 900L));

        Collections.sort(list);

        // Highest score (5) must be first
        assertEquals("User suka kopi tubruk tanpa gula", list.get(0).fact);
        assertEquals(5, list.get(0).score);
        assertEquals("preference", list.get(0).category);

        // Second highest (2)
        assertEquals("User suka teh", list.get(1).fact);
        assertEquals(2, list.get(1).score);

        // Lowest (1)
        assertEquals("User minum air putih", list.get(2).fact);
        assertEquals(1, list.get(2).score);
    }
}
