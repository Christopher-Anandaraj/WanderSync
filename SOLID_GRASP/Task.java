//task interface
public interface taskManagement {
    public void performTask();
}

//in projectTasks
//in wantedTasks
public class addTask implements taskManagement {
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String priority;

    //create the task
    public addTask(String title, String description, String dueDate,
                   String status, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    //add task to project arrayList
    @Override
    public void performTask() {
        projectTasks.add(this); //project tasks arraylist should be named this
        wantedTask.add(this) //added to wantedtask array?
    }

}

public class runTask implements taskManagement {
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String priority;

    public runTask(String title, String description, String dueDate,
                   String status, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    //mark task as complete
    @Override
    public void performTask() {
        this.status = "Complete";
    }

}

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
