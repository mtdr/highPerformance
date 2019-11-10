package ru.edu.lab2;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.L_Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@JCStressTest
@Description("Remove test")
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "3-actors remove is correct")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "3-actors remove is incorrect")
@State
public class LFRemoveTest {
    private final int[] RANGE_START = {0, 500, 1000};
    private final int[] RANGE_END = {1000, 1500, 2000};
    private final int TOTAL_NUMBERS = 2000;

    private Set<Integer> set = new LockFreeSet<>();
    private AtomicInteger totalRemoved = new AtomicInteger(0);

    public LFRemoveTest() {
        IntStream.range(0, TOTAL_NUMBERS).forEach(i -> set.add(i));
    }

    private void add(int i) {
        ArrayList<Integer> values = IntStream.range(RANGE_START[i], RANGE_END[i])
                .boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(values);

        for (int value : values) {
            if (set.remove(value)) {
                totalRemoved.incrementAndGet();
            }
        }
    }

    @Actor
    public void actor_0() {
        add(0);
    }

    @Actor
    public void actor_1() {
        add(1);
    }

    @Actor
    public void actor_2() {
        add(2);
    }

    @Arbiter
    public void checkResult(L_Result result) {
        result.r1 = totalRemoved.get() == TOTAL_NUMBERS;
    }

}
