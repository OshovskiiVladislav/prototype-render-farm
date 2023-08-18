package com.oshovskii.task.manager.services;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;
import com.oshovskii.task.manager.aspect.annotations.InjectRandomInt;
import com.oshovskii.task.manager.kafka.KafkaProducer;
import com.oshovskii.task.manager.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleTaskImpl implements TaskService {
    private final KafkaProducer kafkaProducer;

    @InjectRandomInt(min = 1, max = 5)
    private int taskTime;

    @Override
    public void workTask(TaskDto taskDto) {
        if (taskDto.getType() == TaskType.SIMPLE) {
            log.info("[SIMPLE Type | workTask()] TASK received {}", taskDto);
            executeTask(taskDto);
        }
    }

    public void executeTask(TaskDto taskDto) {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                log.info("[SimpleTaskImpl] [executeTask()] Task performed on: " + new Date() +
                        " [SimpleTaskImpl] [executeTask()] Thread's name: " + Thread.currentThread().getName() +
                        " [SimpleTaskImpl] [executeTask()] Task title: " + taskDto.getTitle());
                kafkaProducer.send("done", "key", taskDto);
            }
        };

        log.info(" [SimpleTaskImpl] [executeTask()] Task time: " + taskTime);
        Timer timer = new Timer("Timer");
        long delay = taskTime * 10_000L;
        timer.schedule(timerTask, delay);
    }
}
