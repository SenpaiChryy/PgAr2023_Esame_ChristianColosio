import unibs.MenuManager;

import java.util.ArrayList;

public class UserInterface {

    public static String[] convertIntToString(ArrayList<Integer> arrayInt) {
        String[] arrayString = new String[arrayInt.size()];
        for(int i = 0;i < arrayInt.size();i++) {
            arrayString[i] = String.valueOf(arrayInt.get(i));
        }

        return arrayString;
    }

    public static int toolMenu(ArrayList<Tool> bag) {

        String[] strings = new String[5];

        for (int i = 0; i < bag.size(); i++) {
            strings[i] = bag.get(i).toString();
        }

        for (int i = bag.size(); i < 5; i++) {
            strings[i] = "empty";
        }

        strings[4] = "Don't take the tool";

        MenuManager menu = new MenuManager("Take/change/leave tool:", strings);
        return menu.chooseNoExit();
    }

    public static int moveMenu(ArrayList<Move> moves) {

        String[] strings = new String[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            strings[i] = moves.get(i).toString();
        }

        MenuManager menu = new MenuManager("Choose a move: ", strings);

        return menu.chooseNoExit();
    }

    public static int paperMenu() {
        MenuManager menu = new MenuManager("What do you want to do?",  new String[]{"Let him/her go", "Don't let him/her go"});
        return menu.chooseNoExit();
    }

    public static int corruptionMenu(int corruptionMoney) {
        MenuManager menu = new MenuManager("Do you want " + corruptionMoney + " to let me go?",  new String[]{"Accept", "Decline"});
        return menu.chooseNoExit();
    }
}

