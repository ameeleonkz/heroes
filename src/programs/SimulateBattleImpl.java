package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog; // Позволяет логировать. Использовать после каждой атаки юнита

    /**
     * 1. Внешний цикл while выполняется O(n) раз в худшем случае;
     * 2. На каждой итерации:
     *   - Сортировка O(n log n);
     *   - Проход по списку юнитов O(n);
     * Итого: O(n * (n log n + n)) = O(n² * log n).
     */
    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        System.out.println("simulate");
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerArmy.getUnits());
        allUnits.addAll(computerArmy.getUnits());

        while (hasAliveUnits(playerArmy) && hasAliveUnits(computerArmy)) {
            // Сортировка юнитов по атаке O(n log n)
            allUnits.sort((u1, u2) -> Integer.compare(u2.getBaseAttack(), u1.getBaseAttack()));

            // Один раунд боя O(n)
            Iterator<Unit> iterator = allUnits.iterator();
            while (iterator.hasNext()) {
                Unit unit = iterator.next();

                if (!unit.isAlive()) {
                    iterator.remove();
                    continue;
                }

                Unit target = unit.getProgram().attack(); // O(1)
                if (target != null) {
                    printBattleLog.printBattleLog(unit, target);
                    if (!target.isAlive()) {
                        allUnits.remove(target);
                    }
                }
            }
        }
        System.out.println("simulateEnd");
    }

    private boolean hasAliveUnits(Army army) {
        return army.getUnits().stream().anyMatch(Unit::isAlive);
    }
}