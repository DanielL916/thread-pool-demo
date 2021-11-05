package club.nazarick.threadpool;

import club.nazarick.threadpool.domain.Input;
import club.nazarick.threadpool.domain.Output;
import club.nazarick.threadpool.service.Input2OutputService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
@Slf4j
class ThreadPoolApplicationTests {

    private final Input2OutputService input2OutputService;

    @Autowired
    ThreadPoolApplicationTests(Input2OutputService input2OutputService) {
        this.input2OutputService = input2OutputService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void multiProcess() {
        log.info("Multi process start");
//        List<Input> inputList = Arrays.asList(new Input(1), new Input(2), new Input(3));
        List<Input> inputList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            inputList.add(new Input(i));
        }
        long startTime = System.currentTimeMillis();
        System.out.println(input2OutputService.multiProcess(inputList));
        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        log.error("======= 多线程运行时间：" + Float.toString(seconds) + " seconds. ======= ");

        long startTimeProcess = System.currentTimeMillis();
        System.out.println(input2OutputService.singleProcess(inputList));
        long endTimeProcess = System.currentTimeMillis();
        float secondsProcess = (endTimeProcess - startTimeProcess) / 1000F;
        log.error("======= 单线程运行时间：" + Float.toString(secondsProcess) + " seconds. ======= ");

        log.info("Multi process end");
    }

    @Test
    void asyncProcess() throws InterruptedException, ExecutionException {
        log.info("Async process start");
        Future<Output> future = input2OutputService.asyncProcess(new Input(1));
        while (true) {
            if (future.isDone()) {
                System.out.println(future.get());
                log.info("Async process end");
                break;
            }
            log.info("Continue doing something else.");
            Thread.sleep(100);
        }
    }
}
