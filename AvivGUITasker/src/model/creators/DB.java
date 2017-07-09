package model.creators;

import java.util.HashMap;

import model.process.IProcess;

public class DB implements DBCreator {

	ProcessLoader loader;
	ProcessSaver saver;
	public DB() {
		loader = new Ploader();
		saver = new Psaver();
	}
	public void setNewLoader(ProcessLoader p){
		loader=p;
	}
	public void setNewSaver(ProcessSaver p){
		saver=p;
	}
	@Override
	public HashMap<String, IProcess> loadProcessDataXML(String path) {
		return loader.loadProcessDataXML(path);
	}

	@Override
	public void saveProcessDataXML(String path, HashMap<String, IProcess> process_list) {
		saver.saveProcessDataXML(path, process_list);

	}

}
