package model.search;

import java.util.Collection;

import model.process.IProcess;

public interface Searcher
{
	Collection<IProcess> search(Collection<IProcess> list, String query); 
}
