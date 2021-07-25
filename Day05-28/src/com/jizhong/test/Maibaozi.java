package com.jizhong.test;

public class Maibaozi {
	public static void main(String[] args) {
		/**
		 * 生产者（包子铺）：生产包子
		 * 消费者（顾客）：吃包子
		 * 
		 * 1. 顾客告诉包子铺，我要吃包子
		 * 2. 包子铺做包子（顾客等待）
		 * 3. 包子铺做完包子后，告诉顾客包子做完了，可以吃了
		 * 4. 顾客吃包子
		 */
		/**
		 * 匿名内部类
		 */
		Maibaozi maibaozi = new Maibaozi();
		//顾客线程
		new Thread(){
			@Override
			public void run() {
				while(true){
					synchronized(maibaozi){
						//1. 顾客告诉包子铺，我要吃包子
						System.out.println("顾客要吃包子~~~");
						//2. 包子铺做包子（顾客等待）
						try{
							maibaozi.wait();
						}catch(Exception e){
							e.printStackTrace();
						}
						//4. 顾客吃包子
						System.out.println("顾客吃包子~~~");
						System.out.println("-------------------------------------------------");
						//顾客吃完包子唤醒包子铺
						maibaozi.notify();
					}
				}
			}
		}.start();
		
		//包子铺线程
		new Thread(){
			@Override
			public void run() {
				while(true){
					synchronized(maibaozi){
						//2. 包子铺做包子（顾客等待）
						System.out.println("包子铺正在做包子，请等待5秒~~~");
						try{
							Thread.sleep(5000);
						}catch(Exception e){
							e.printStackTrace();
						}
						//包子铺等待
						try{
							//3. 包子铺做完包子后，告诉顾客包子做完了，可以吃了（唤醒顾客线程）
							System.out.println("包子做完了，提醒顾客吃包子~~~");
							maibaozi.notify();
							maibaozi.wait();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
		
	}
}
