package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    /**
     * Внешний цикл по рядам выполняется 3 раза (m = 3)
     * Внутренний цикл проходит по юнитам в ряду - O(n)
     * Все операции внутри циклов - O(1)
     * Итоговая сложность: O(m*n), где m = 3 , поэтому фактически O(n)
     */
    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        System.out.println("get " + isLeftArmyTarget);
        List<Unit> suitableUnits = new ArrayList<>();

        // Для каждого ряда находим подходящие юниты
        for (List<Unit> row : unitsByRow) {
            if (row.isEmpty()) continue;

            if (isLeftArmyTarget) {
                // Для атакующей армии игрока ищем самого левого юнита в каждом ряду
                Unit leftmostUnit = null;
                for (Unit unit : row) {
                    if (unit.isAlive()) {
                        leftmostUnit = unit;
                        break;
                    }
                }
                if (leftmostUnit != null) {
                    suitableUnits.add(leftmostUnit);
                }
            } else {
                // Для атакующей армии компьютера ищем самого правого юнита в каждом ряду
                Unit rightmostUnit = null;
                for (int i = row.size() - 1; i >= 0; i--) {
                    Unit unit = row.get(i);
                    if (unit.isAlive()) {
                        rightmostUnit = unit;
                        break;
                    }
                }
                if (rightmostUnit != null) {
                    suitableUnits.add(rightmostUnit);
                }
            }
        }
//        for (Unit u : suitableUnits) {
//            System.out.println("coord to attack: " + u.getxCoordinate() + " " + u.getyCoordinate());
//        }
        return suitableUnits;
    }
}