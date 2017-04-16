package com.zhu2chu.all.taxonomy.jdk.concurrent.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 此类出至官方文档。
 * http://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class Safelock {

    /**
     * static nested class。
     * 朋友类
     * 
     * @author ThreeX
     * @link http://www.zhu2chu.com
     *
     */
    static class Friend {
        /**朋友的名字*/
        private final String name; //这里真好玩，如果没有下面构造方法给它赋值。eclipse会提示：The blank final field name may not have been initialized
        private final Lock lock = new ReentrantLock();//一个朋友有一个锁头

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        /**
         * 即将发生的鞠躬。这是本例的关键方法。
         * 当前线程的任务要获取到两个人的锁头，才能发生一次【我给你鞠躬，你回我鞠躬】的完整行为。有点类似事务，但又不是。这是同步
         * 因为myLock = lock.tryLock()并不是原子行为，当执行到一半的时候，可能就切换到另一个线程的任务执行了。
         * 如：线程1：Alphonse(主动)、Gaston
         *    线程2：Gaston(主动)、Alphonse
         *    线程1执行到myLock = lock.tryLock()，线程1拿到了this(Alphonse)的lock，
         *    然后切换到线程2，也执行到myLock = lock.tryLock()，这里的lock是this(Gaston)的，线程2拿到了Gaston的lock。
         *    现在切回到线程1：往下执行yourLock = bower.lock.tryLock()，bower(Gaston)的lock已被线程2拿到，所以yourLock是false。
         *    这样，线程1拿到Alphonse的lock,线程2拿到Gaston的lock，由于需要单个线程拿到两个人的lock才能完成任务。所以这两个线程都不能完成
         *    任务。
         *    所以需要在finally里释放锁头，以免两个线程紧紧地握着已拥有的锁头，各不相让，这就会造成死锁。
         * 
         * @param bower 主动鞠躬的对象(即要鞠躬的对象)。如我等你鞠躬，你就是这个bower
         */
        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;

            try {
                //如果此锁头能持有，当前线程持有，持锁计数器+1并返回true。否则返回false
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();
            } finally {
                if (!(myLock && yourLock)) {//如果myLock、yourLock中出现false，那么要进行锁的释放
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }

            return myLock&&yourLock;//只有两个人的锁都拿到，才能鞠躬
        }

        /**
         * 鞠躬
         * @param bower
         */
        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    //bower给this鞠躬
                    System.out.format("%s: %s has bowed to me!%n", this.name, bower.getName());
                    //this给bower鞠躬
                    bower.bowBack(this);
                } finally {
                    //释放锁
                    lock.unlock();
                    bower.lock.unlock();
                }
            }
        }

        /**
         * 回馈别人的鞠躬
         * @param bower
         */
        public void bowBack(Friend bower) {
            System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
        }
    }

    /**
     * static nested class。
     * 循环鞠躬的任务
     * 
     * @author ThreeX
     * @link http://www.zhu2chu.com
     *
     */
    static class BowLoop implements Runnable {
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        @Override
        public void run() {
            Random random = new Random();
            for (;;) {
                try {
                    Thread.sleep(random.nextInt(3000));
                } catch (InterruptedException e) {}
                bowee.bow(bower);
            }
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");

        new Thread(new BowLoop(alphonse, gaston)).start(); 
        new Thread(new BowLoop(gaston, alphonse)).start(); 
    }

}
