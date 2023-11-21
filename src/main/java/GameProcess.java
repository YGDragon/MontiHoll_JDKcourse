import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameProcess {
    static Presenter presenter = new Presenter();
    static Gamer gamer = new Gamer();
    private static final int QUANTITY_GAMES = 1000;
    private static final int QUANTITY_DOORS = 3;
    private static final String[] OUTSIDE_DOOR = {"АВТО", "КОЗА"};
    private static final String[] RESULT_VALUE = {"ПОБЕДА", "НЕУДАЧА"};
    Map<Integer, String> resultsOfGame;

    public GameProcess() {
        resultsOfGame = new HashMap<>();
    }

    // процесс игры
    public void gameProcess() {
        int step = 1;
        int quantityGames = QUANTITY_GAMES;
        while (quantityGames != 0) {
            String result = getGameResult(
                    interactionPresenterGamer());
            saveGameResult(step, result);
            step++;
            quantityGames--;
        }
        displayResultsOfGame();
    }

    // создание случайной комбинации содержимого за дверями
    private static Door[] createRandomDoors() {
        int countAuto = 0; // флаг наличия АВТО за одой из дверей
        Door[] doors = new Door[QUANTITY_DOORS];
        int i;
        for (i = 0; i < doors.length; i++) {
            doors[i] = new Door(
                    i + 1, OUTSIDE_DOOR[new Random().nextInt(OUTSIDE_DOOR.length)]
            );
            if (doors[i].getOutside().equals(OUTSIDE_DOOR[0])) {
                countAuto++;
                tellToPresenter(doors[i].getNumber()); // ведущий узнает о двери с АВТО
                break;
            }
        }
        for (int j = i + 1; j < doors.length; j++) {
            doors[j] = new Door(j + 1, OUTSIDE_DOOR[1]);
        }
        /* в случае отсутствия за дверями АВТО
           добавление АВТО за последнюю дверь */
        if (countAuto != 1) {
            doors[doors.length - 1].setOutside(OUTSIDE_DOOR[0]);
        }
        return doors;
    }

    // ведущий узнает номер двери с АВТО
    private static void tellToPresenter(int numberDoor) {
        presenter.setDoorAuto(numberDoor);
    }

    // взаимодействие ведущий - игрок
    private Door interactionPresenterGamer() {
        Door[] randomDoors = createRandomDoors();
        // игрок выбирает 1 из 3 дверей
        Door firstChoiceGamer = gamer.choiceOneOfThree(randomDoors);
        // ведущий открывает 1 из 3 дверей
        Door choicePresenter = presenter.openOneOfThree(randomDoors, firstChoiceGamer);
        // ведущий предлагает игроку сменить выбранную дверь
        presenter.offerToGamer(gamer);
        // игрок меняет свой выбор
        return gamer.finalChoice(randomDoors, firstChoiceGamer, choicePresenter);
    }

    // определение результата игры
    private String getGameResult(Door finalChoiceGamer) {
        if (finalChoiceGamer.getOutside().equals(OUTSIDE_DOOR[0])) {
            return RESULT_VALUE[0];
        } else {
            return RESULT_VALUE[1];
        }
    }

    // сохранение результата игры
    private void saveGameResult(int step, String result) {
        resultsOfGame.put(step, result);
    }

    private void displayResultsOfGame() {
        for (Map.Entry<Integer, String> map : resultsOfGame.entrySet()
        ) {
            System.out.printf("Шаг %d - %s\n", map.getKey(), map.getValue());
        }
    }

    public void displayStatistic() {
        int countVictory = 0;
        int countLoss = 0;
        for (Map.Entry<Integer, String> map : resultsOfGame.entrySet()
        ) {
            if (map.getValue().equals(RESULT_VALUE[0])) {
                countVictory++;
            } else {
                countLoss++;
            }
        }
        BigDecimal victory = new BigDecimal(countVictory / (double) QUANTITY_GAMES * 100);
        BigDecimal loss = new BigDecimal(countLoss / (double) QUANTITY_GAMES * 100);
        System.out.println(
                "ПОБЕД >> " + victory.setScale(2, RoundingMode.FLOOR) + "%, "
                        + "НЕУДАЧ >> " + loss.setScale(2, RoundingMode.FLOOR) + "%");
    }
}