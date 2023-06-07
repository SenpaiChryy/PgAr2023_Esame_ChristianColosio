import java.util.ArrayList;
import java.util.Random;

public class Mob {
    private int hp;
    private int baseDamage;
    private float physicalDamage;
    private float magicalDamage;
    private float physicalResistance;
    private float magicalResistance;
    private ArrayList<Tool> bag = new ArrayList<>();
    private ArrayList<Move> moves = new ArrayList<>();
    Random random = new Random();


    public Mob(String type) {
        switch (type) {
            case "User" -> {
                this.hp = 20;
                this.baseDamage = 5;
            }
            case "Cammo" -> {
                this.hp = 18 + random.nextInt(-5, 6);
                this.baseDamage = 4 + random.nextInt(-2, 3);
            }
            case "normalMob" -> {
                this.hp = 12 + random.nextInt(-5, 6);
                this.baseDamage = 3 + random.nextInt(-2, 3);
            }
        }
    }

    public Mob(String type, String world) {
        this.moves.add(new Move());
        this.moves.add(new Move());

        if(type.equals("normalMob")) {
            this.hp = 90 + random.nextInt(-10, 11);
            this.physicalDamage = (float) Math.max(5 + random.nextInt(-2, 3), 0.5);
            this.magicalDamage = (float) Math.max(5 + random.nextInt(-2, 3), 0.5);
            this.physicalResistance = (float) Math.max(2 + random.nextInt(-2, 3), 0.5);
            this.magicalResistance = (float) Math.max(2 + random.nextInt(-2, 3), 0.5);
            this.bag.add(new Tool());
        } else {
            this.hp = 110;
            this.physicalDamage = (float) Math.max(10 + random.nextInt(-5, 6), 0.5);
            this.magicalDamage = (float) Math.max(10 + random.nextInt(-5, 6), 0.5);
            this.physicalResistance = (float) Math.max(5 + random.nextInt(-3, 4), 0.5);
            this.magicalResistance = (float) Math.max(5 + random.nextInt(-3, 4), 0.5);
            this.moves.add(new Move());
            this.moves.add(new Move());
        }

        if(type.equals("cammo")) {
            this.bag.add(new Tool());
            this.bag.add(new Tool());
            this.bag.add(new Tool());
        }
    }

    public int getHp() {
        return hp;
    }

    public int getHpExtreme() {
        int hpExtreme = hp;

        for (Tool tool : bag) {
            hpExtreme += tool.getModifierByType(ModifierType.HP);
        }

        return hpExtreme;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public ArrayList<Tool> getBag() {
        return bag;
    }

    public void setBag(int index, Tool tool) {
        if(this.bag.size() < 4) {
            bag.add(tool);
        } else {
            this.bag.set(index, tool);
        }
    }

    public Move getMove(int index) {
        return this.moves.get(index);
    }

    public float getDamage(MoveType type) {

        float damage;

        if(type == MoveType.PHYSICAL) {
            damage = physicalDamage;

            for (Tool tool : bag) {
                damage += tool.getModifierByType(ModifierType.PHYSICALDAMAGE);
            }
        } else {
            damage = magicalDamage;

            for (Tool tool : bag) {
                damage += tool.getModifierByType(ModifierType.MAGICALDAMAGE);
            }

        }

        return damage;
    }

    public float getResistance(MoveType type) {

        float resistance;

        if(type == MoveType.PHYSICAL) {
            resistance = physicalResistance;

            for (Tool tool : bag) {
                resistance += tool.getModifierByType(ModifierType.PHYSICALRESISTANCE);
            }
        } else {
            resistance = magicalResistance;

            for (Tool tool : bag) {
                resistance += tool.getModifierByType(ModifierType.MAGICALRESISTANCE);
            }

        }

        return resistance;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public int calcDamageTaken(Mob mob, int indexMove) {

        int rand = random.nextInt(85, 101);
        Move moveUser = mob.getMove(indexMove);
        float damage = ((mob.getDamage(moveUser.getType()) * moveUser.getDamage())/(200 * this.getResistance(moveUser.getType()))) * (rand);

        //System.out.println("Received " + (int)damage + " damage, current hp " + Math.max(this.getHp(), 0));
        //System.out.println("sopra: "+ (mob.getDamage(moveUser.getType()) * moveUser.getDamage()));
        //System.out.println("sotto: "+ (200 * this.getResistance(moveUser.getType())));
        //System.out.println("rand: " + rand);

        return (int)damage;
    }

    @Override
    public String toString() {
        return "hp: " + hp + ", physicalDamage: " + physicalDamage + ", magicalDamage: " + magicalDamage + ", physicalResistance: "
                + physicalResistance + ", magicalResistance: " + magicalResistance + ", bag: " + bag + ", moves: " + moves;
    }
}
