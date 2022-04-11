package com.imall.note.test;

/**
 * @className: TestThreadPool
 * @descripe: 自己造一个并发的例子
 * @author: zpj
 * @date: 2019/7/8
 * @version: 1.0
 */
public class TestConcurrentError{
	public static void main(String[] args){
		Student stu = new Student("张曼玉","女士");
		PrintThread pt = new PrintThread(stu);
		ChangeThread ct = new ChangeThread(stu);
		pt.start();
		ct.start();
	}
}

class Student{
	String name;
	String gender;
	public Student(String name,String gender){
		this.name = name;
		this.gender = gender;
	}
}

class PrintThread extends Thread{
	Student stu;
	public PrintThread(Student stu){
		this.stu = stu;
	}
	@Override
	public void run(){
		while(true){
			//synchronized(stu){
			{
				System.out.println(stu.name + " : " + stu.gender);
			}
		}
	}
}
class ChangeThread extends Thread{
	Student stu;
	public ChangeThread(Student stu){
		this.stu = stu;
	}
	@Override
	public void run(){
		boolean isOkay = true;
		while(true){
			//synchronized(stu){
			{
				if(isOkay){
					stu.name = "梁朝伟";//梁朝伟 女士
					stu.gender = "先生";//梁朝伟 先生
				}else{
					stu.name = "张曼玉";//张曼玉 先生
					stu.gender = "女士";//张曼玉 女士
				}
				isOkay = !isOkay;
			}
		}
	}
}
