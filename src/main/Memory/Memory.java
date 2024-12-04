package main.Memory;

import java.util.HashMap;

public class Memory {

    HashMap<String, Integer> memory;

    public Memory() {
        memory = new HashMap<>();
    }

    public void store(String id, int value) {
        memory.put(id.toLowerCase(), value);
    }

    public int retrieve(String id) {
        return memory.getOrDefault(id.toLowerCase(), 0);

    }

    public boolean contains(String id) {
        return memory.containsValue(id);
    }
}
