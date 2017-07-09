package model.creators;

import java.util.HashMap;

import model.process.IProcess;

public interface ProcessSaver {
	public void saveProcessDataXML(String path, HashMap<String, IProcess> process_list);
}
