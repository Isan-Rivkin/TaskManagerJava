package model.creators;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import model.process.IProcess;

public class Psaver implements ProcessSaver {

	@Override
	public void saveProcessDataXML(String path, HashMap<String, IProcess> process_list) {
		try {
			encodeXML(process_list, path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public static void encodeXML(Object obj, String path) throws FileNotFoundException{
		XMLEncoder e=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
		e.writeObject(obj);
		e.close();
		
	}
	
	public static void encodeXML(Object obj, OutputStream out) throws FileNotFoundException{
		XMLEncoder e=new XMLEncoder(new BufferedOutputStream(out));
		e.writeObject(obj);
		e.close();
		
	}

}
