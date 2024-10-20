interface TaskManagement {
    void performTask(Project project, Task task);
}

class AddTask implements TaskManagement {
    public void performTask(Project project, Task newTask){
        project.getTaskList().add(newTask);
    }
}

class RemoveTask implements TaskManagement {
    public void performTask(Project project, Task oldTask) {
        project.getTaskList().remove(oldTask);
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

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
            System.out.println("Generic task is running!");
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
            System.out.println("Priority task is running!");
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
            System.out.println("Due date task is running!");
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
            System.out.println("Assigned task is running!");
        }
}
