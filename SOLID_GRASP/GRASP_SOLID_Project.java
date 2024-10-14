//task interface
public interface Task {
    public void performTask();
}

//in projectTasks
//in wantedTasks
public class addTask implements Task {
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String priority;

    public addTask(String title, String description, String dueDate,
                   String status, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    @Override
    public void performTask() {
        //what do I put here??
    }

}

public class runTask implements Task {
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

    @Override
    public void performTask() {
        //what do I put here??
    }

}