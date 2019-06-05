package ru.otus.homework;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.swing.Timer;

/*
Запуск всех GC проводился с параметрами памяти
-Xms512m -Xmx512m

Никаких ограничений на время задержки сборщиков мусора я не ставил.

1. -XX:+UseSerialGC
Starting pid: 7340@DESKTOP-LL18RAV
GC name:Copy
GC name:MarkSweepCompact
После 1 минуты... Young сборок: 4 (stop на 934 ms), Old сборок: 1 (stop на 934 ms)
После 2 минуты... Young сборок: 2 (stop на 0 ms), Old сборок: 2 (stop на 2586 ms)
После 3 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 2 (stop на 3147 ms)
После 4 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 31 (stop на 44197 ms)
После 5 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 28 (stop на 39870 ms)
Exception in thread "main"
java.lang.OutOfMemoryError: Java heap space
	at ru.otus.homework.Benchmark.run(Benchmark.java:24)
	at ru.otus.homework.TesterGC.main(TesterGC.java:66)
	
Самый стопорящий программу тип сборщика.
	
2. -XX:+UseParallelGC
Starting pid: 6632@DESKTOP-LL18RAV
GC name:PS MarkSweep
GC name:PS Scavenge
После 1 минуты... Young сборок: 4 (stop на 1079 ms), Old сборок: 2 (stop на 2119 ms)
После 2 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 1 (stop на 1071 ms)
После 3 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 2 (stop на 2685 ms)
После 4 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 12 (stop на 22851 ms)
После 5 минуты... Young сборок: 0 (stop на 0 ms), Old сборок: 1 (stop на 2013 ms)
Exception in thread "main"
java.lang.OutOfMemoryError: GC overhead limit exceeded

По сравнению с Serial значительное уменьшение пауз в работе программы.

3. -XX:+UseG1GC
Starting pid: 14836@DESKTOP-LL18RAV
GC name:G1 Young Generation
GC name:G1 Old Generation
После 1 минуты... Young сборок: 14 (stop на 1170 ms), Old сборок: 0 (stop на 0 ms)
После 2 минуты... Young сборок: 6 (stop на 497 ms), Old сборок: 0 (stop на 0 ms)
После 3 минуты... Young сборок: 6 (stop на 351 ms), Old сборок: 0 (stop на 0 ms)
После 4 минуты... Young сборок: 6 (stop на 362 ms), Old сборок: 1 (stop на 1091 ms)
После 5 минуты... Young сборок: 15 (stop на 492 ms), Old сборок: 19 (stop на 21744 ms)
Exception in thread "main" start:295161 Name:G1 Old Generation, action:end of major GC, gcCause:G1 Evacuation Pause(1200 ms)
java.lang.OutOfMemoryError: Java heap space
	at ru.otus.homework.Benchmark.run(Benchmark.java:24)
	at ru.otus.homework.TesterGC.main(TesterGC.java:85)


На этом сборщике очередная пропасть в сторону уменьшения пауз в работе программы,
при этом, проглядывая более подробные логи, бросается в глаза, что он в отличие от других сборщиков
делает задержки чаще остальных, но при этом крайне короткие. Однозначно фаворит.

4. -XX:+UnlockExperimentalVMOptions -XX:+UseZGC 
Error occurred during initialization of VM
Option -XX:+UseZGC not supported

Стоит 11 java, брал с сайта Oracle, 

 */

public class TesterGC {

	public static int youngCountInMinute;
	public static long youngDuration;
	
	public static int oldCountInMinute;
	public static long oldDuration;
	
	public static int currentMinute = 1;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus.homework:type=Benchmark");
        Benchmark mbean = new Benchmark(0, 16, 10);
        mbs.registerMBean(mbean, name);
        
        Timer timer = new Timer(60000, x -> {
        	System.out.printf("После %s минуты... Young сборок: %d (stop на %d ms), Old сборок: %d (stop на %d ms)\n",
        			currentMinute,
	    			youngCountInMinute,
	    			youngDuration,
	    			oldCountInMinute,
	    			oldDuration);
        	currentMinute++;
        	youngCountInMinute = 0;
        	youngDuration = 0;
        	oldCountInMinute = 0;
        	oldDuration = 0;
        });
        timer.start();
        mbean.run();
	}
	
	private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    
                    //String gcName = info.getGcName();
                    long duration = info.getGcInfo().getDuration();
                    
                    String gcAction = info.getGcAction();
     

                    if (gcAction.equals("end of minor GC")) {
                    	youngCountInMinute++;
                    	youngDuration += duration;
                    }
                    if (gcAction.equals("end of major GC")) {
                    	oldCountInMinute++;
                    	oldDuration += duration;
                    }
                    
                    String gcName = info.getGcName();
                    String gcCause  = info.getGcCause();

                    long start = info.getGcInfo().getStartTime();

                    System.out.println("start:" + start + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
