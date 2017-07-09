package tests;

import java.util.HashMap;

import model.process.CommonProcessManager;
import model.process.IProcess;
import model.process.IProcessManager;
import model.process.Process;
import model.task.ITask;
import model.task.Task;

public class TestMain {

	public static void main(String[] args) {
		IProcessManager model=new CommonProcessManager();
		IProcessManager model2=new CommonProcessManager();
		IProcess p1=new Process();
		IProcess p2=new Process();
		IProcess p3=new Process();
		IProcess p4=new Process();
		IProcess p5=new Process();
		IProcess p6=new Process();
		IProcess p7=new Process();
		IProcess p8=new Process();
		IProcess p9=new Process();
		IProcess p10=new Process();
		ITask t1=new Task();
		ITask t2=new Task();
		ITask t3=new Task();
		ITask t4=new Task();
		ITask t5=new Task();
		ITask t6=new Task();
		ITask t7=new Task();
		ITask t8=new Task();
		ITask t9=new Task();
		ITask t10=new Task();
		
		t1.setTitle("task1");
		t1.addComment("just task 1");
		t2.setTitle("task2");
		t2.addComment("just task 2");
		t3.setTitle("task3");
		t3.addComment("just task 3");
		t3.setTitle("task4");
		t3.addComment("just task 4");
		t3.setTitle("task5");
		t3.addComment("just task 5");
		t3.setTitle("task6");
		t3.addComment("just task 6");
		t3.setTitle("task7");
		t3.addComment("just task 7");
		t3.setTitle("task8");
		t3.addComment("just task 8");
		t3.setTitle("task9");
		t3.setDescription("just task 9");
		t3.setTitle("task10");
		t3.addComment("just task 10");
		//process
		p1.setName("queuedProcess");
		p1.setDesc("i am queded process");
		p1.addTask(t1);
		p1.addComment("תגובה 1");
		p1.addComment("תגובה 2");
		p1.addComment("תגובה 3");	
		p2.setName("currentProcess");
		p2.addComment("i am current process");
		p2.start();
		p2.addTask(t2);
		p2.addComment("תגובה 1");
		p2.addComment("תגובה 2");
		p2.addComment("תגובה 3");
		p3.setName("finishedProcess");
		p3.addComment("i am finished process");
		p3.finishProcess();
		p3.addTask(t3);
		p3.addComment("תגובה 1");
		p3.addComment("תגובה 2");
		p3.addComment("תגובה 3");
		p4.setName("תהליך קפוא 4");
		p4.addComment("i am queded process");
		p4.addTask(t1);
		p4.addComment("תגובה 1");
		p4.addComment("תגובה 2");
		p4.addComment("תגובה 3");	
		p5.setName("תהליך רץ 5");
		p5.addComment("i am running process");
		p5.addTask(t1);
		p5.start();
		p5.addComment("תגובה 1");
		p5.addComment("תגובה 2");
		p5.addComment("תגובה 3");	
		p6.setName("תהליך רץ 6");
		p6.addComment("i am running process");
		p6.addTask(t1);
		p6.start();
		p6.addComment("תגובה 1");
		p6.addComment("תגובה 2");
		p6.addComment("תגובה 3");	
		p7.setName("תהליך מת 7");
		p7.addComment("i am dead process");
		p7.addTask(t1);
		p7.addComment("תגובה 1");
		p7.addComment("תגובה 2");
		p7.addComment("תגובה 3");	
		p7.finishProcess();
		p8.setName("תהליך קפוא 8");
		p8.addComment("i am queded process");
		p8.addTask(t1);
		p8.addComment("תגובה 1");
		p8.addComment("תגובה 2");
		p8.addComment("תגובה 3");	
		p9.setName("תהליך רץ 9");
		p9.addComment("i am running process");
		p9.addTask(t1);
		p9.start();
		p9.addComment("תגובה 1");
		p9.addComment("תגובה 2");
		p9.addComment("תגובה 3");	
		p10.setName("תהליך רץ 10");
		p10.addComment("i am running process");
		p10.addTask(t1);
		p10.start();
		p10.addComment("תגובה 1");
		p10.addComment("תגובה 2");
		p10.addComment("תגובה 3");	

		model.addProcess(p1);
		model.addProcess(p2);
		model.addProcess(p3);

		model.addProcess(p4);
		model.addProcess(p5);
		model.addProcess(p6);

		model.addProcess(p7);
		model.addProcess(p8);
		model.addProcess(p9);
		model.addProcess(p10);
		
		//System.out.println("1 TEST");
		model.saveData("taskConfig/current.xml", "taskConfig/queded.xml", "taskConfig/finished.xml");
		//System.out.println("2 TEST");
		//model2.loadData("taskConfig/current.xml", "taskConfig/queded.xml", "taskConfig/finished.xml");
		//System.out.println("model 2 is initialized");
		
	//	HashMap<String, IProcess> p= model2.getCurrentProcess();

		//System.out.println(p.get("currentProcess").getProcessName());
		System.out.println("Finito");
		model.exit();
		//model2.exit();

	}

}
