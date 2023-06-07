import unibs.MenuManager;

public class Main {
    static MenuManager menuWorlds = new MenuManager("Choose a world: ", new String[]{"Base", "Tamagolem", "PaperPlease", "Extreme"});

    public static void main(String[] args) {
        int choise = 0;
        Game game = new Game();

        do {
            if(!game.isBeatCammo()) {
                game.gamePaperPlease();
            } else {
                choise = menuWorlds.choose();
                switch(choise) {
                    case 0: {
                        game.gameBase();
                    }
                    case 1: {
                        game.gameTamagolem();
                    }
                    case 2: {
                        game.gamePaperPlease();
                    }
                    case 3: {
                        game.gameExtreme();
                    }
                }
            }
        } while (game.getLife() > 0 && choise != -1);

        System.out.println("bye");
    }

    public static String getCitiesByName(String name) {
        return Game.cities.getOrDefault(name, null);
    }
}