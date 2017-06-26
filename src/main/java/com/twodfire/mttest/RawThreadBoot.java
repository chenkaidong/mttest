package com.twodfire.mttest;

/**
 * Created by chenkaidong on 2017/6/22.
 */
public class RawThreadBoot {

    public static void main(String[] args) {



        Thread t1 = new Thread(new Runnable() {
            public void run() {

                for(;;)

                    System.out.println("thread111111");


            }
        });


        Thread t2 = new Thread(new Runnable() {
            public void run() {

                for(;;)

                    System.out.println("thread22222");


            }
        });

        t1.start();
        t2.start();



    }
}
