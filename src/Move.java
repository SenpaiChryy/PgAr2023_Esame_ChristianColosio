import java.util.Random;

public class Move {
    private final MoveType moveType;
    private int damage;
    private int cooldown;
    private int cooldownMax;

    public Move() {

        Random random = new Random();

        switch (random.nextInt(0, 4)) {
            case 0 -> {
                damage = 25;
                cooldownMax = 1;
            }
            case 1 -> {
                damage = 50;
                cooldownMax = 2;
            }
            case 2 -> {
                damage = 75;
                cooldownMax = 3;
            }
            case 3 -> {
                damage = 100;
                cooldownMax = 4;
            }
        }

        if(random.nextInt(0, 2) == 0) {
            this.moveType = MoveType.PHYSICAL;
        } else {
            this.moveType = MoveType.MAGICAL;
        }

        setCooldown();
    }

    public MoveType getType() {
        return moveType;
    }

    public int getDamage() {
        return damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void decreaseCooldown() {
        this.cooldown --;
    }

    public void setCooldown() {
        this.cooldown = this.cooldownMax;
    }

    @Override
    public String toString() {
        return "moveType: " + moveType + ", damage: " + damage + ", cooldown: " + cooldownMax;
    }
}
