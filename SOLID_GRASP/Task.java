<<<<<<< HEAD
import java.util.ArrayList;

//task interface
//public abstract class taskManagement { //make into an abstract class
//    protected String title;
//    protected String description;
//    protected String dueDate;
//    protected String status;
//    protected String priority;
//    protected String assignedTo;
//
//    //assign all vars via super to reduce repetition
//    public taskManagement(String title, String description, String dueDate,
//                   String status, String priority, String assignedTo) {
//        this.title = title;
//        this.description = description;
//        this.dueDate = dueDate;
//        this.status = status;
//        this.priority = priority;
//        this.assignedTo = assignedTo;
//    }
//
//    protected abstract void performTask();
//}
//
////add a task
//public class addTask extends taskManagement {
//
//    //QUESTION: Are duplicates allowed??
//
//    //create the task
//    public addTask(String title, String description, String dueDate,
//                   String status, String priority, String assignedTo) {
//        super(title, description, dueDate, status, priority, assignedTo); //create task
//    }
//
//    //add task to project arrayList
//    @Override
//    public void performTask() {
//        projectTasks.add(this); //project tasks arraylist should be named this
//    }
//
//}
//
////complete the task
//public class runTask extends taskManagement {
//
//    public runTask(String title, String description, String dueDate,
//                   String status, String priority, String assignedTo) {
//        super(title, description, dueDate, status, priority, assignedTo);
//
//    //mark task as complete
//    @Override
//    public void performTask() {
//        this.status = "Complete";
//        //should I remove from arrayList?
//    }
//}
//
////remove task
//public class removeTask extends TaskManagement {
//    public runTask(String title, String description, String dueDate,
//                   String status, String priority, String assignedTo) {
//        super(title, description, dueDate, status, priority, assignedTo);
//    }
//
//    @Override
//    public void performTask() {
//        projectTasks.remove(this);
//    }
//}
//_________________________________________________________________

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
=======
public abstract class Task {
>>>>>>> main
    protected String title;
    protected String description;
    protected String dueDate;
    protected String status;
    protected String priority;
    protected String assignedTo;
<<<<<<< HEAD

    @Override
=======
    protected String message;

>>>>>>> main
    public void setTitle(String title) {
        this.title = title;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getTitle() {
        return title;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public void setDescription(String description) {
        this.description = description;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getDescription() {
        return description;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getDueDate() {
        return dueDate;
    }

<<<<<<< HEAD
    @Override
=======

>>>>>>> main
    public void setPriority(String priority) {
        this.priority = priority;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getPriority() {
        return priority;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public void setStatus(String status) {
        this.status = status;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getStatus() {
        return status;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> main
    public String getAssignedTo() {
        return assignedTo;
    }

<<<<<<< HEAD
    @Override
    public void setStatusComplete() {
        this.status = "Complete";
    }
=======
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
>>>>>>> main
}

//an example special task that uses parent Task
class PriorityTask extends Task {
    public PriorityTask(String title, String description, String priority, String status) {
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setStatus(status);
    }
<<<<<<< HEAD
}
=======
    @Override
        public void runTask() {
            System.out.println("This is a priority task!");
        }
}

>>>>>>> main
//another example
class DueDateTask extends Task {
    public DueDateTask(String title, String description, String dueDate, String status) {
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        setStatus(status);
    }
<<<<<<< HEAD
}
=======
    @Override
        public void runTask() {
            System.out.println("This is a due date task!");
        }
}

>>>>>>> main
//ANOTHER example I think you get it by now
class AssignedTask extends Task {
    public AssignedTask(String title, String description, String assignedTo, String status) {
        setTitle(title);
        setDescription(description);
        setAssignedTo(assignedTo);
        setStatus(status);
    }
<<<<<<< HEAD
=======
    @Override
        public void runTask() {
            System.out.println("This is a assigned task!");
        }
>>>>>>> main
}
