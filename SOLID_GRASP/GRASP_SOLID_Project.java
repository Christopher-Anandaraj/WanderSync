import java.util.ArrayList;

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

public interface task {
    void performTask();
}

public class genericTask implements task {

}

public class specializedTask extends genericTask {

}