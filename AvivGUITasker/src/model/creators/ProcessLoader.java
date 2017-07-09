package model.creators;

import java.util.HashMap;

import model.process.IProcess;

public interface ProcessLoader {
	public HashMap<String, IProcess> loadProcessDataXML(String path);
}
