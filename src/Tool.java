import java.util.HashMap;
import java.util.Random;

public class Tool {
    private final int id;
    private HashMap<ModifierType, Integer> modifier = new HashMap<>();

    public Tool() {
        Random random = new Random();
        this.id = random.nextInt(0, Integer.MAX_VALUE);
        int modifierIndex = -1;

        for (int i = 0; i < random.nextInt(1,3); i++) {

            if (modifierIndex == -1) {
                modifierIndex = random.nextInt(0 , 5);
            } else {
                int newModifierIndex;

                do {
                    newModifierIndex = random.nextInt(0, 5);
                } while (modifierIndex == newModifierIndex);

                modifierIndex = newModifierIndex;
            }

            switch(modifierIndex) {
                case 0 -> this.modifier.put(ModifierType.HP, random.nextInt(-20, 121));
                case 1 -> this.modifier.put(ModifierType.PHYSICALDAMAGE, random.nextInt(-2, 13));
                case 2 -> this.modifier.put(ModifierType.MAGICALDAMAGE, random.nextInt(-2, 13));
                case 3 -> this.modifier.put(ModifierType.PHYSICALRESISTANCE, random.nextInt(-1, 9));
                case 4 -> this.modifier.put(ModifierType.MAGICALRESISTANCE, random.nextInt(-1, 9));
            }
        }
    }

    public int getModifierByType(ModifierType type) {
        return modifier.getOrDefault(type, 0);
    }

    @Override
    public String toString() {
        return "id: " + id + ", modifier: " + modifier;
    }
}
