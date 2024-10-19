import java.util.ArrayList;

public interface TaskManagement {
    void performTask();
}

public addTask implements TaskManagement {
    public void performTask(Task newTask){
        projectTasks.add(newTask);
    }
}

public removeTask implements TaskManagement {
    public void performTask(Task oldTask) {
        projectTask.remove(oldTask);
    }
}

public abstract class Task {
    protected String title;
    protected String description;
    protected String dueDate;
    protected String status;
    protected String priority;
    protected String assignedTo;
    protected String message;
    
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

    public void runTask() {
    }
}

//Generic class
class GenericTask extends Task {
    public GenericTask(String title, String description, String status) {
        setTitle(title);
        setDescription(description);
        setStatus(status);
    }
    @Override
        public void runTask() {
            System.out.println("This is a generic task!");
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
    @Override
    public void runTask() {
        System.out.println("This is a priority task!");
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
    @Override
    public void runTask() {
        System.out.println("This is a due date task!");
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
    @Override
    public void runTask() {
        System.out.println("This is a assigned task!");
    }
}
