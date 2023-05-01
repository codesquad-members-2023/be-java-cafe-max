package kr.codesqaud.cafe;

import kr.codesqaud.cafe.board.repository.TestDataRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AppStartupRunner implements ApplicationRunner {

    private final TestDataRepository testDataRepository;

    public AppStartupRunner(TestDataRepository testDataRepository) {
        this.testDataRepository = testDataRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testDataRepository.createProcedure();
        testDataRepository.callInsertPostData();
    }
}
