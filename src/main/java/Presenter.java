import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Presenter {
    int doorAuto;

    public void setDoorAuto(Integer doorAuto) {
        this.doorAuto = doorAuto;
    }

    // ведущий открывает одну из трех дверей
    public Door openOneOfThree(Door[] doors, Door choiceDoor) {
        List<Door> filterDoors = Arrays.stream(doors).filter(door -> door.getNumber() != doorAuto).toList();
        if (choiceDoor.getNumber() == doorAuto) {
            return filterDoors.get(new Random().nextInt(filterDoors.size()));
        } else {
            filterDoors = filterDoors.stream().filter(door -> !door.equals(choiceDoor)).toList();
            return filterDoors.get(0);
        }
    }

    public void offerToGamer(Gamer gamer) {
        gamer.setTriggerChoice(true);
    }
}
