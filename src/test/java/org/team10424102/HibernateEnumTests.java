package org.team10424102;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.team10424102.games.Dota2MatchResult;
import org.team10424102.games.Dota2MatchResultRepo;
import org.team10424102.games.Dota2MatchType;
import org.team10424102.games.MatchResult;

public class HibernateEnumTests extends BaseTests {

    @Autowired
    Dota2MatchResultRepo resultRepo;

    /**
     * @Enumerated(EnumType.STRING)
     * @Column(columnDefinition = "enum")
     */
    @Test
    @Rollback(false)
    public void save() {
        Dota2MatchResult result = new Dota2MatchResult();
        result.setEvaluation("A+");
        result.setResult(MatchResult.DRAW);
        result.setType(Dota2MatchType.LADDER);
        resultRepo.save(result);
    }


    @Test
    @Rollback(false)
    public void load() {
        Iterable<Dota2MatchResult> result = resultRepo.findAll();
        for (Dota2MatchResult aResult : result) System.out.println(aResult.getResult().toString());
    }








}
