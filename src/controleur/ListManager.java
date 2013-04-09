package controleur;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import model.ListT;

public class ListManager implements Iterable<ListT> {

	private List<ListT> listOfList;

	private ListManager() {
		listOfList = new LinkedList<ListT>();
	}

	@Override
	public ListIterator<ListT> iterator() {
		return listOfList.listIterator();
	}

	private static class ListManagerHolder {

		private static final ListManager instance = new ListManager();

	}

	public static ListManager getInstance() {

		return ListManagerHolder.instance;
	}

	public void addList(ListT listToAdd) {

		listOfList.add(listToAdd);
	}

	public void setListOfList(List<ListT> lists) {

		listOfList = lists;
	}

	public boolean removeList(ListT listToRemove) {
		return listOfList.remove(listToRemove);
	}

	public ListT removeListFromIndex(int pos) {

		return listOfList.remove(pos);
	}

	public List<ListT> getLists() {

		return listOfList;
	}

	public ListT getList(int pos) {

		return listOfList.get(pos);
	}

	public void removeAllList() {
		listOfList.clear();
	}

	@Override
	public String toString() {

		String s = "";
		for (ListT l : listOfList) {
			s += l + "\n";
		}
		return s;
	}
}
