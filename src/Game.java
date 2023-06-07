import unibs.MenuManager;

import java.util.*;

public class Game {
    private int points;
    private int life;
    private ArrayList<Node> baseMap = new ArrayList<>();
    private Random random = new Random();
    private MenuManager menuLink;
    private HashMap<String, Boolean> wins = new HashMap<>();
    public static HashMap<String, String> cities = new HashMap<>();

    public Game() {
        points = 0;
        life = 10;
        createBaseMap();
        wins.put("base", false);
        wins.put("tamaGolem", false);
        wins.put("paperPlease", false);
        wins.put("extreme", false);
    }

    public void gameBase() {
        Mob user = new Mob("User");
        ArrayList<Node> map;
        int currentNode = 0;

        if (random.nextInt(0, 2) == 0) {
            map = baseMap;
        } else {
            map = XmlUtils.readerMaps();
        }

        do {
            System.out.println("Current node: " + (currentNode + 1));
            menuLink = new MenuManager("Possible links: ", UserInterface.convertIntToString(map.get(currentNode).getLinks()));
            currentNode = Integer.parseInt(menuLink.chooseStringNoExit()) - 1;

            if(map.get(currentNode).getType().equals("FINE")) {
                Mob cammo = new Mob("Cammo");
                user = basicFight(user, cammo);
            } else if(!map.get(currentNode).getType().equals("INIZIO")) {
                if(random.nextInt(0, 2) == 0) { //SPAWN MOB
                    Mob monster = new Mob("normalMob");
                    user = basicFight(user, monster);
                } else {   // SPAWN BONUS
                    if(random.nextInt(0, 2) == 0) { // BONUS = HP
                        int bonus = random.nextInt(-5, 11);
                        System.out.println("User get " + bonus + " in hp");
                        user.setHp(user.getHp());

                    } else {    // BONUS = DAMAGE
                        int bonus = random.nextInt(-3, 4);
                        System.out.println("User get " + bonus + " in attack");
                        user.setBaseDamage(user.getBaseDamage());
                    }
                }
            }
        } while (user.getHp() > 0 && !map.get(currentNode).getType().equals("FINE"));

        if(user.getHp() > 0) {
            System.out.println("You have beaten Cammo!");

            if(!wins.get("base")) {
                wins.put("base", true);
                points += 10;
            }
        } else {
            System.out.println("You lost!");
            life --;
        }
    }

    public void gameTamagolem() {

    }

    public void gamePaperPlease() {
        int currentNode = 0;
        int money = 1100;
        ArrayList<Node> map;
        ArrayList<Person> people;

        cities = XmlUtils.readCities();
        people = XmlUtils.readerPeople();

        GregorianCalendar currentDay = new GregorianCalendar(2023, Calendar.JUNE, 7);

        if (random.nextInt(0, 2) == 0) {
            map = baseMap;
        } else {
            map = XmlUtils.readerMaps();
        }

        do {
            System.out.println("Current node: " + (currentNode + 1));
            System.out.println("Current money: " + money);
            menuLink = new MenuManager("Possible links: ", UserInterface.convertIntToString(map.get(currentNode).getLinks()));
            currentNode = Integer.parseInt(menuLink.chooseStringNoExit()) - 1;

            if(!map.get(currentNode).getType().equals("INIZIO") && !map.get(currentNode).getType().equals("FINE")) {
                Person personToCheck = people.get(random.nextInt(0, people.size() - 1));
                System.out.println("Current date: " + currentDay.getTime());
                System.out.println(personToCheck.toStringCompact());
                int choice = UserInterface.paperMenu();

                if(!personToCheck.compareID() && choice == 0) {
                    money -= 300;
                    System.out.println("You wasn't right, -300 sbleuri, current money: " + money + " sbleuri");
                } else if (!personToCheck.compareID() && choice == 1) {
                    int corruptionMoney = random.nextInt(250, 501);
                    int choiceCorruption = UserInterface.corruptionMenu(corruptionMoney);

                    if(choiceCorruption == 0) {
                        if(random.nextInt(0, 5) == 2) {
                            money = -1000;
                            System.out.println("You got caught");
                        } else {
                            money += corruptionMoney;
                            System.out.println( "+" + corruptionMoney + " sbleuri, current money: " + money + " sbleuri");
                        }
                    }
                } else if (personToCheck.compareID() && choice == 1)  {
                    money -= 300;
                    System.out.println("You wasn't right, -300 sbleuri, current money: " + money + " sbleuri");
                }
            }

            currentDay.add(Calendar.DATE, 1);
        } while(!map.get(currentNode).getType().equals("FINE") && money >= 0);

        if(money > 2200) {
            System.out.println("You have beaten Cammo!");

            if(!wins.get("paperPlease")) {
                wins.put("paperPlease", true);
                points += 10;
            }
        } else {
            System.out.println("You lost!");
            life --;
        }
    }

    public void gameExtreme() {
        Mob user = new Mob("User", "EXTREME");
        ArrayList<Node> map;
        int currentNode = 0;

        if (random.nextInt(0, 2) == 0) {
            System.out.println("Base map");
            map = baseMap;
        } else {
            System.out.println("XML map");
            map = XmlUtils.readerMaps();
        }

        do {
            System.out.println("Current node: " + (currentNode + 1));
            menuLink = new MenuManager("Possible links: ", UserInterface.convertIntToString(map.get(currentNode).getLinks()));
            currentNode = Integer.parseInt(menuLink.chooseStringNoExit()) - 1;

            if(map.get(currentNode).getType().equals("FINE")) {
                Mob cammo = new Mob("Cammo", "EXTREME");
                user = extremeFightCammo(user, cammo);
            } else if(!map.get(currentNode).getType().equals("INIZIO")) {

                int rand = random.nextInt(0, 3);

                if(rand == 0) { //SPAWN MOB

                    Mob monster = new Mob("normalMob", "EXTREME");
                    user = extremeFight(user, monster);

                } else if(rand == 1){   // SPAWN HEALTH

                    int bonus = random.nextInt(-5, 11);
                    System.out.println("User get " + bonus + " in hp");
                    user.setHp(user.getHpExtreme());

                } else {    // SPAWN CHEST

                    Tool tool = new Tool();
                    System.out.println("You find this item in a chest: " + tool);
                    int choice = UserInterface.toolMenu(user.getBag());

                    if(choice != 4) {
                        user.setBag(choice, tool);
                    }
                }
            }
        } while (user.getHpExtreme() > 0 && !map.get(currentNode).getType().equals("FINE"));

        if(user.getHpExtreme() > 0) {
            System.out.println("You have beaten Cammo!");

            if(!wins.get("extreme")) {
                wins.put("extreme", true);
                points += 10;
            }
        } else {
            System.out.println("You lost!");
            life --;
        }
    }

    public void createBaseMap() {
        baseMap.add(new Node(1, new ArrayList<>(List.of(2)), "INIZIO"));
        baseMap.add(new Node(2, new ArrayList<>(Arrays.asList(3, 4)), "INTERMEDIO"));
        baseMap.add(new Node(3, new ArrayList<>(List.of(5)), "INTERMEDIO"));
        baseMap.add(new Node(4, new ArrayList<>(List.of(5)), "INTERMEDIO"));
        baseMap.add(new Node(5, new ArrayList<>(List.of(6)), "INTERMEDIO"));
        baseMap.add(new Node(6, new ArrayList<>(List.of(7)), "INTERMEDIO"));
        baseMap.add(new Node(7, new ArrayList<>(List.of(0)), "FINE"));
    }

    public Mob basicFight(Mob user, Mob mob) {
        System.out.println("User stats: " + user.getHp() + " hp, " + user.getBaseDamage() + " attack");
        System.out.println("Mob stats: " + mob.getHp() + " hp, " + mob.getBaseDamage() + " attack");

        do {
            mob.setHp(mob.getHp() - user.getBaseDamage());
            System.out.println("Mob got " + user.getBaseDamage() + " damage, current hp " + Math.max(mob.getHp(), 0));

            if(mob.getHp() > 0) {
                user.setHp(user.getHp() - mob.getBaseDamage());
                System.out.println("User got " + mob.getBaseDamage() + " damage, current hp " + Math.max(user.getHp(), 0));
            }

        } while (user.getHp() > 0 && mob.getHp() > 0);

        if(user.getHp() > 0) {
            System.out.println("User win with " + Math.max(user.getHp(), 0) + " hp");
        } else {
            System.out.println("User lose");
        }

        return user;
    }

    public Mob extremeFight(Mob user, Mob mob) {
        System.out.println("User stats: " + user.toString());
        System.out.println("Mob stats: " + mob.toString());

        do {

            int choice = UserInterface.moveMenu(user.getMoves());
            int damage = mob.calcDamageTaken(user, choice);
            mob.setHp(mob.getHpExtreme() - damage);
            System.out.println("Mob got " + damage + " damage, current hp " + Math.max(mob.getHpExtreme(), 0));

            if(mob.getHpExtreme() > 0) {
                damage = user.calcDamageTaken(mob, random.nextInt(0, 2));
                user.setHp(user.getHpExtreme() - damage);
                System.out.println("User got " + damage + " damage, current hp " + Math.max(user.getHpExtreme(), 0));
            }

        } while (user.getHpExtreme() > 0 && mob.getHpExtreme() > 0);

        if(user.getHpExtreme() > 0) {
            System.out.println("User win with " + Math.max(user.getHpExtreme(), 0) + " hp");
        } else {
            System.out.println("User lose");
        }

        return user;
    }

    public Mob extremeFightCammo(Mob user, Mob mob) {
        System.out.println("User stats: " + user.toString());
        System.out.println("Mob stats: " + mob.toString());

        do {

            int choice = UserInterface.moveMenu(user.getMoves());
            int damage = mob.calcDamageTaken(user, choice);
            mob.setHp(mob.getHpExtreme() - damage);
            System.out.println("Mob got " + damage + " damage, current hp " + Math.max(mob.getHpExtreme(), 0));

            if(mob.getHpExtreme() > 0) {

                damage = user.calcDamageTaken(mob, 0);

                for (int i = 1; i < 4; i++) {
                    if(damage < user.calcDamageTaken(mob, i)) {
                        damage = user.calcDamageTaken(mob, i);
                    }
                }

                user.setHp(user.getHpExtreme() - damage);
                System.out.println("User got " + damage + " damage, current hp " + Math.max(user.getHpExtreme(), 0));
            }

        } while (user.getHpExtreme() > 0 && mob.getHpExtreme() > 0);

        if(user.getHpExtreme() > 0) {
            System.out.println("User win with " + Math.max(user.getHpExtreme(), 0) + " hp");
        } else {
            System.out.println("User lose");
        }

        return user;
    }

    public int getLife() {
        return life;
    }

    public boolean isBeatCammo() {
        return wins.get("base");
    }
}
