import java.util.ArrayList;

//task interface
public abstract class taskManagement { //make into an abstract class
    protected String title;
    protected String description;
    protected String dueDate;
    protected String status;
    protected String priority;
    protected String assignedTo;

    //assign all vars via super to reduce repetition
    public taskManagement(String title, String description, String dueDate,
                   String status, String priority, String assignedTo) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.assignedTo = assignedTo;
    }

    protected abstract void performTask();
}

//add a task
public class addTask extends taskManagement {

    //QUESTION: Are duplicates allowed??

    //create the task
    public addTask(String title, String description, String dueDate,
                   String status, String priority, String assignedTo) {
        super(title, description, dueDate, status, priority, assignedTo); //create task
    }

    //add task to project arrayList
    @Override
    public void performTask() {
        projectTasks.add(this); //project tasks arraylist should be named this
    }

}

//complete the task
public class runTask extends taskManagement {

    public runTask(String title, String description, String dueDate,
                   String status, String priority, String assignedTo) {
        super(title, description, dueDate, status, priority, assignedTo);

    //mark task as complete
    @Override
    public void performTask() {
        this.status = "Complete";
        //should I remove from arrayList?
    }
}

//remove task
public class removeTask extends TaskManagement {
    public runTask(String title, String description, String dueDate,
                   String status, String priority, String assignedTo) {
        super(title, description, dueDate, status, priority, assignedTo);
    }

    @Override
    public void performTask() {
        projectTasks.remove(this);
    }
}

//__________________________________________________________________
//cosider adding perform task in here (TA Talk about in meeting)
interface TaskInterface {
    String getTitle();
    String getDescription();
    String getDueDate();
    String getPriority();
    String getStatus();
    String getAssignedTo();

    void setTitle(String title);
    void setDescription(String description);
    void setDueDate(String dueDate);
    void setPriority(String priority);
    void setStatus(String status);
    void setAssignedTo(String assignedTo);
    void setStatusComplete();
}

public abstract class Task implements TaskInterface {
    protected String title;
    protected String description;
    protected String dueDate;
    protected String status;
    protected String priority;
    protected String assignedTo;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getDueDate() {
        return dueDate;
    }

    @Override
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String getPriority() {
        return priority;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String getAssignedTo() {
        return assignedTo;
    }

    @Override
    public void setStatusComplete() {
        this.status = "Complete";
    }
}

//an example special task that uses parent Task
class PriorityTask extends Task {
    public PriorityTask(String title, String description, String priority, String status) {
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setStatus(status);
    }
}
//another example
class DueDateTask extends Task {
    public DueDateTask(String title, String description, String dueDate, String status) {
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        setStatus(status);
    }
}
//ANOTHER example I think you get it by now
class AssignedTask extends Task {
    public AssignedTask(String title, String description, String assignedTo, String status) {
        setTitle(title);
        setDescription(description);
        setAssignedTo(assignedTo);
        setStatus(status);
    }
}