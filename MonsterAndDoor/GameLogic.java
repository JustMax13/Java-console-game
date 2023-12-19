package MonsterAndDoor;

import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    private static int _stepCount;
    private static int _numberOfMathExamplesPerStep;
    private static long _averageTimeToCompleteAStep;
    private static Scanner _scanner;
    private static Random _random;
    private static Monster _monster;
    private static SafetyDoor _safetyDoor;
    public static void startGame() throws InterruptedException {
        initializeGameParameters();

        printStartMessage();
        int currentStep = 1;
        while (currentStep <= _stepCount){
            long stepTime = stepLogic();
            boolean useMonsterUltimate = Chance.tryYourLuck(_monster.getChanceToUseUltimate());
            boolean useDoorUltimate = Chance.tryYourLuck(_monster.getChanceToUseUltimate());

            if (useMonsterUltimate){
                _monster.useUltimate();
                System.out.println("Монстр використав спец прийом!");
            }
            if (useDoorUltimate){
                _safetyDoor.useUltimate();
                System.out.println("Двері використали спец прийом!");
            }

            float resultDamage = damageConversion(stepTime);
            _safetyDoor.setHitPoint(_safetyDoor.getHitPoint() - resultDamage);

            if(_safetyDoor.getHitPoint() > 0){
                printPassStepResult(currentStep);
            } else {
                gameOver();
                return;
            }
            currentStep++;
        }

        System.out.println("Монстр здався, ми перемогли! Гарно рахуєш :)");
    }
    private static void initializeGameParameters(){
        // Загальне
        _scanner = new Scanner(System.in);
        _random = new Random();

        // Параметри складності та протяжності рівня
        /*Скільки буде хвиль*/_stepCount = 10;
        /*Скільки буде в хвилі прикладів*/_numberOfMathExamplesPerStep = 5;
        /*Орієнтовний час на проходження хвилі. Чим менше - тим складніше*/_averageTimeToCompleteAStep = 15000; // в мілісекундах

        /*Налаштування самих персонажів*/createCharacters(7,20, 100, 20);
    }
    private static void createCharacters(float monsterDamage, float monsterUltimateChance,
                                         float doorHitPoint, float doorUltimateChance){
        _monster = new Monster(monsterDamage,monsterUltimateChance);
        _safetyDoor = new SafetyDoor(doorHitPoint, doorUltimateChance);
    }
    private static void printStartMessage() throws InterruptedException {
        System.out.println("Характеристики монстра:\n\tУрон: " + _monster.getDamageDealt() + " HP/крок;"
                + "\n\tШанс використання спец уміння: " + _monster.getChanceToUseUltimate() + "%.");
        System.out.println("Характеристики захисних дверей:\n\tЗдоров'я: " + _safetyDoor.getHitPoint() + "HP;"
                + "\n\tШанс використання спец уміння: " + _safetyDoor.getChanceToUseUltimate() + "%.");

        System.out.println("Приготуйся! Почнемо гру як натиснеш Enter!");
        _scanner.nextLine();
        System.out.println("3");
        Thread.sleep(1000);
        System.out.println("2");
        Thread.sleep(1000);
        System.out.println("1");
        Thread.sleep(1000);
    }
    // Метод повертає значення, скільки мілісекунд пройшло за хід
    private static long stepLogic() throws InterruptedException {
        long stepStartTime = System.currentTimeMillis();

        int currentNumberOfCompletedExamples = 0;
        while (currentNumberOfCompletedExamples < _numberOfMathExamplesPerStep){
            int firstNumber = _random.nextInt(100);
            int secondNumber = _random.nextInt(100);
            int operation = _random.nextInt(2);
            int result = 0;
            boolean hasInt = false;

            switch (operation){
                case 0: {
                    result = firstNumber + secondNumber;
                    System.out.print(firstNumber + " + " + secondNumber + " = ");

                    hasInt = _scanner.hasNextInt();
                    break;
                }
                case 1: {
                    result = firstNumber - secondNumber;
                    System.out.print(firstNumber + " - " + secondNumber + " = ");

                    hasInt = _scanner.hasNextInt();
                    break;
                }
            }

            if (hasInt){
                if (result == _scanner.nextInt()){
                    System.out.println("✔");
                    currentNumberOfCompletedExamples++;

                    continue;
                } else {
                    System.out.println("×");
                    continue;
                }
            }else {
                System.out.println("×");
                continue;
            }
        }

        long stepEndTime = System.currentTimeMillis();
        long stepDuration = stepEndTime - stepStartTime;

        return stepDuration;
    }
    private static float damageConversion(long stepTime){
        float resultDamage = _monster.getDamageDealt();

        double damageCoefficient = (double)stepTime / (double)_averageTimeToCompleteAStep;
        resultDamage *= damageCoefficient;

        return resultDamage;
    }
    private static void printPassStepResult(int currentStep) throws InterruptedException {
        switch (_random.nextInt(3)){
            case 0:
            {
                System.out.println("Плюс пройдена хвиля!");
                break;
            }
            case 1:
            {
                System.out.println("Цю хвилю ми пройшли.");
                break;
            }
            case 2:
            {
                System.out.println("Хвиля була пройдена!");
                break;
            }
        }
        System.out.println("Пройдено хвиль: " + currentStep + "/" + _stepCount);
        _scanner.nextLine();
        Thread.sleep(1000);
        System.out.println("Залишок hit point захисних дверей: " + _safetyDoor.getHitPoint());
        System.out.println("Продовжимо? Натискай Enter!");
        _scanner.nextLine();
        System.out.println("3");
        Thread.sleep(1000);
        System.out.println("2");
        Thread.sleep(1000);
        System.out.println("1");
        Thread.sleep(1000);
    }
    private static void gameOver(){
        System.out.println("О ні... Двері не витримали!\nМонстр переміг");
    }
}
