package controleur;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import model.ListT;
import model.Task;

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

	public static void main(String[] argv) {

		ListManager lm = new ListManager();

		ListT l1 = new ListT("test");
		l1.addTask(new Task("plop"));
		l1.addTask(new Task("bloup"));
		lm.addList(l1);
		l1.getTask(0).setChecked(true);

		ListT l2 = new ListT("youhou");
		l2.addTask(new Task("bonjour"));
		l2.addTask(new Task("au revoir"));
		lm.addList(l2);

		System.out.println(lm);
	}
}
