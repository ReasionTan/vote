package com.vote;

import com.vote.utils.IdWorker;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoteApplication.class)
@ActiveProfiles("dev")
public class VoteApplicationTest {

    @Autowired
    private IdWorker idWorker;

}
