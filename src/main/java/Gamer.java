import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Gamer {
    private boolean triggerChoice;

    public void setTriggerChoice(boolean triggerChoice) {
        this.triggerChoice = triggerChoice;
    }

    // игрок случайно выбирает 1 из 3 дверей
    public Door choiceOneOfThree(Door[] doors) {
        return doors[new Random().nextInt(doors.length)];
    }

    // игрок выбирает 1 из 2 дверей
    public Door finalChoice(Door[] doors, Door firstChoiceGamer, Door choicePresenter) {
        List<Door> finalDoor = new ArrayList<>();
        if (triggerChoice) {
            finalDoor = Arrays.stream(doors).filter(
                    door -> door != firstChoiceGamer && door != choicePresenter).toList();
        }
        return finalDoor.get(0);
    }
}

