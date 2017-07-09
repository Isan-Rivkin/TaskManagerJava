package controller;


import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import controller.commands.ICommand;



public class GeneralController {
	
	private ArrayBlockingQueue<ICommand> queue;
	private boolean keepRunning;
	private ArrayList<Thread> threads_history;
	public GeneralController() {
		this.queue=new ArrayBlockingQueue<ICommand>(300);
		this.threads_history=new ArrayList<Thread>();
		keepRunning=true;
	}
	
	public void start(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (keepRunning) {
					try {
						ICommand cmd = queue.take();
						if (cmd != null) {
							cmd.execute();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});
		t.start();
		threads_history.add(t);

	}
	public void stop(){
		this.keepRunning=false;
	}
	public void insertCommand(ICommand cmd){
		try {
			queue.put(cmd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
