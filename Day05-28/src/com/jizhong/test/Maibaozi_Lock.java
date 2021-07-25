package com.jizhong.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *	Lock接口：Lock 实现提供了比使用 synchronized 方法和语句可获得的更广泛的锁定操作
 *
 *	 void lock() ：获取锁。 
 *	 void unlock() ：释放锁。
 *
 *	使用步骤：
 *		1. 在成员位置创建Lock接口的实现类ReentrantLock对象（多态方法创建）
 *		2. 有可能会出现多线程安全问题的代码前调用Lock接口中的方法lock获取锁
 *		3. 有可能会出现多线程安全问题的代码后调用Lock接口中的方法unlock释放锁
 *
 *	注意：Lock锁方法必须写在try catch代码块中
 */
public class Maibaozi_Lock {
	public static void main(String[] args) {
		Maibaozi_Lock maibaozi = new Maibaozi_Lock();
		//创建锁对象
		Lock lock = new ReentrantLock();
		new Thread(){
			@Override
			public void run() {
				try{
					//获取锁（相当于同步代码块开始）
					lock.lock();
					while(true){
						System.out.println("顾客要吃包子~~~");
						try{
							maibaozi.wait();
						}catch(Exception e){
							e.printStackTrace();
						}
						System.out.println("顾客吃包子~~~");
						System.out.println("-------------------------------------------------");
						maibaozi.notify();
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					//释放锁（相当于同步代码块结束）
					lock.unlock();
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				while(true){
					synchronized(maibaozi){
						System.out.println("包子铺正在做包子，请等待5秒~~~");
						try{
							Thread.sleep(5000);
						}catch(Exception e){
							e.printStackTrace();
						}
						try{
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
