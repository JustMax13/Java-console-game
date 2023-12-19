package MonsterAndDoor;

import java.util.Random;

public class Chance {
    private static Random _random;
    static {
        _random= new Random();
    }

    public static boolean tryYourLuck(float chanceOfSuccess){
        float randomFloat = _random.nextFloat() * 100; // від 0 до 100

        if(chanceOfSuccess < randomFloat){
            return false;
        } else {
            return  true;
        }
    }
}
