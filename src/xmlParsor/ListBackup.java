package xmlParsor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import model.ListT;
import model.Task;
import modelException.ListTException;
import modelException.TaskException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;

import controleur.ListManager;


/**
 * 
 * @author guillaume
 *
 *	XMLfile pattern :
 *	<backup>
 *		<list name="listName" open="true|false">
 *			</task name="taskName" checked="true|false"/>
 *		</list>
 *	</backup>
 *
 *
 */
public class ListBackup {

	private ListBackup() {}
	
	private static class ListBackupHolder {
		
		private static final ListBackup instance = new ListBackup();
		
	}
	
	public static ListBackup getInstance() {
		return ListBackupHolder.instance;
	}
	
	public List<ListT> getListFromFile(String fileName) throws TaskException, ListTException {
		List<ListT> listOfList = ListManager.getInstance().getLists();
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document document = builder.build(fileName);
			Element rootNode = document.getRootElement();
			IteratorIterable<Element> listIterator = rootNode.getDescendants(new ElementFilter("list"));
			Element currentElement;
			while(listIterator.hasNext()) {
				currentElement = listIterator.next();
				runTroughtListTag(listOfList, currentElement);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfList;
	}
	
	private void runTroughtListTag(List<ListT> listOfList, Element currentElement) throws ListTException, TaskException {
		if(currentElement.getName() == XMLComponent.XMLCOMPONENT_LIST) {
			ListT lt = new ListT(currentElement.getAttributeValue(ListT.LISTT_ATTRIBUT_NAME),
								  Boolean.parseBoolean(currentElement.getAttributeValue(ListT.LISTT_ATTRIBUT_OPEN)));
			IteratorIterable<Element> taskIterator = currentElement.getDescendants(new ElementFilter("task"));
			Element currentTask;
			while(taskIterator.hasNext()) {
				currentTask = taskIterator.next();
				runThroughtTaskTag(lt, currentTask);
			}
			listOfList.add(lt);
		} else {
			//TODO: throw Exception
			throw new ListTException("the xml file had bad format : " + currentElement.getName() + " tag get, list tag expected.");
		}
	}
	
	private void runThroughtTaskTag(ListT lt, Element currentTask) throws TaskException {
		if(currentTask.getName() == XMLComponent.XMLCOMPONENT_TASK) {
			Task t = new Task(currentTask.getAttributeValue(Task.TASK_ATTRIBUT_NAME),
							  Boolean.parseBoolean(currentTask.getAttributeValue(Task.TASK_ATTRIBUT_CHECKED)));
			lt.addTask(t);
		} else {
			//TODO: throw Exception
			throw new TaskException("The xml file had bad format : " + currentTask.getName() + " tag get, task tag expected.");
		}
	}
	
	public void saveListToFile(String fileName) {
		Element root = new Element("backup");
		Document doc = new Document(root);
		
		ListIterator<ListT> ltit = ListManager.getInstance().iterator();
		ListT currentList;
		while(ltit.hasNext()) {
			currentList = ltit.next();
			Element newList = new Element(XMLComponent.XMLCOMPONENT_LIST);
			newList.setAttribute(ListT.LISTT_ATTRIBUT_NAME, currentList.getListName());
			newList.setAttribute(ListT.LISTT_ATTRIBUT_OPEN, String.valueOf(currentList.isOpen()));
			
			ListIterator<Task> ltk = currentList.iterator();
			Task currentTask;
			while(ltk.hasNext()) {
				currentTask = ltk.next();
				Element newTask = new Element(XMLComponent.XMLCOMPONENT_TASK);
				newTask.setAttribute(Task.TASK_ATTRIBUT_NAME, currentTask.getTaskName());
				newTask.setAttribute(Task.TASK_ATTRIBUT_CHECKED, String.valueOf(currentTask.isChecked()));
				newList.addContent(newTask);
			}
			
			root.addContent(newList);
		}
		try {
            XMLOutputter xmlop = new XMLOutputter (Format.getPrettyFormat());
            xmlop.output(doc, new FileOutputStream(fileName));
        } catch (IOException e) {}
	}
	
	public static void main(String[] args) {
		ListBackup lb = ListBackup.getInstance();
		ListManager manager = ListManager.getInstance();
		try {
			manager.setListOfList(lb.getListFromFile("test.xml"));
		} catch (TaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ListTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println(manager);*/
		lb.saveListToFile("save.xml");
	}
	
}
