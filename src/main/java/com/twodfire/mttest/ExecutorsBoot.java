package com.twodfire.mttest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by chenkaidong on 2017/6/22.
 */

class MyRunable implements  Runnable {

    int ini = 0;

    public MyRunable(int outi) {

        ini = outi;

    }


    public void run() {


        for(;;)
            System.out.println("=========="+ini);

    }


}



public class ExecutorsBoot {

    public void testPool(){

        ExecutorService es = Executors.newFixedThreadPool(1000);

        int i = 1;

        // 为什么线程池队列没爆掉
        for(;;) {

            es.submit(new MyRunable(i) );

            i++;

        }

    }

    public void testCallable() throws InterruptedException, ExecutionException {

        Callable<Integer> callable = new Callable<Integer>() {

            public Integer call() {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new Random().nextInt(100);

            }

        };




        // futureTask 可用于Thread启动

        FutureTask<Integer>  future = new FutureTask<Integer>(callable);

        new Thread(future).start();


        //Thread.sleep(5000);

        System.out.println("do my things");

        System.out.println(future.get()); // 阻塞等待callable完成



        // future 只能用于es的返回值

        ExecutorService es = Executors.newFixedThreadPool(100);

        Future<Integer> f= es.submit(new Callable<Integer>() {
            public Integer call() {


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new Random().nextInt(100);
            }

        });


        System.out.println("do my things2");

        System.out.println("..."+f.get());


        // 多个返回值 用ExecutorCompletionService

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executorService);

        for (int i = 0; i< 5;i++) {

            final Integer r  = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(1000);
                    return r;
                }
            });
        }


        System.out.println("do my things3");


        for(int i =0;i<5;i++) {

            System.out.println(cs.take().get());



        }


        // 多个返回值 用list

        List<Future<Integer>> l = new ArrayList<Future<Integer>>();

        ExecutorService ess = Executors.newFixedThreadPool(100);

        for(int i=0;i<5;i++) {

            Future<Integer> ff = ess.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return new Random().nextInt(100);
                }
            });

            l.add(ff);

        }


        ess.shutdown();

        for(Future ff:l) {
            System.out.println(ff.get());
        }


    }




    public static void main(String[] args) {

        ExecutorsBoot eb = new ExecutorsBoot();

        //eb.testPool();

        try {

            eb.testCallable();

        } catch(Exception e) {

            e.printStackTrace();

        }





    }



}
