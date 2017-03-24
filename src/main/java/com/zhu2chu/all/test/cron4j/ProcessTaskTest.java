package com.zhu2chu.all.test.cron4j;

import it.sauronsoftware.cron4j.ProcessTask;
import it.sauronsoftware.cron4j.Scheduler;

public class ProcessTaskTest {
    public static void main(String[] args) {
        ProcessTask task = new ProcessTask(new String[]{"C:\\Windows\\System32\\notepad.exe", "D:\\file.txt"});
        Scheduler scheduler = new Scheduler();
        scheduler.schedule("* * 3 * *", task);
        scheduler.start();
    }
}
