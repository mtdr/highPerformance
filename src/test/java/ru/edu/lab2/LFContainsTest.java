package ru.edu.lab2;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LLL_Result;

@JCStressTest
@Description("Contains test")
@Outcome(id = "true, true, false", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "true, true, true", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "true, false, false", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "true, false, true", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "false, false, false", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "false, false, true", expect = Expect.ACCEPTABLE, desc = "Contains operation is correct")
@Outcome(id = "false, true, true", expect = Expect.FORBIDDEN, desc = "Contains operation is incorrect")
@Outcome(id = "false, true, false", expect = Expect.FORBIDDEN, desc = "Contains operation is incorrect")
@State
public class LFContainsTest {
    private Set<Integer> set = new SetImpl<>();

    @Actor
    public void actor_1() {
        set.add(1);
        set.add(2);
    }

    @Actor
    public void actor_2() {
        set.add(3);
    }

    @Actor
    public void actor_3(LLL_Result result) {
        result.r1 = set.contains(1);
        result.r2 = set.contains(2);
        result.r3 = set.contains(3);
    }
}
