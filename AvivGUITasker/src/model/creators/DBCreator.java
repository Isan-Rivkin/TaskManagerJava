package model.creators;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import model.process.IProcess;

public interface DBCreator {
	public HashMap<String, IProcess> loadProcessDataXML(String path);
	public void saveProcessDataXML(String path, HashMap<String, IProcess> process_list);
}
