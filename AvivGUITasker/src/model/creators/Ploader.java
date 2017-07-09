package model.creators;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import model.process.IProcess;

public class Ploader implements ProcessLoader {

	@Override
	public HashMap<String, IProcess> loadProcessDataXML(String path) {
		try {
			return (HashMap<String, IProcess>)decodeXML(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object decodeXML(String path) throws FileNotFoundException{
		XMLDecoder d=new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
		Object obj=d.readObject();
		d.close();
		return obj;
	}
	public static Object decodeXML(InputStream in) throws FileNotFoundException{
		XMLDecoder d=new XMLDecoder(new BufferedInputStream(in));
		Object obj=d.readObject();
		d.close();
		return obj;
	}

}
