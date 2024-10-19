import java.util.ArrayList;

public class Project {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private ArrayList<Task> tasks;
    private ArrayList<TeamMember> teamMembers;

    //Constructor with all parameters if project has all details
    public Project(String name, String description, String startDate, String endDate, ArrayList<Task> tasks, ArrayList<TeamMember> teamMembers) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        tasks = tasks;
        teamMembers = teamMembers;
    }

    //Empty Constructor in case projects lacks any specific initial details
    public Project() {
        this ("emptyName", "emptyDesc", "emptyStartDate", "emptyEndDate");
        this.tasks = new ArrayList<Task>();
        this.teamMembers = new ArrayList<TeamMember>();
    }

    public void setName(String name) {
            this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTeamMembers(ArrayList<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public  int getName() {
        return name;
    }

    public int getDescription() {
        return description;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getTaskList() {
        return tasks;
    }

    public int getTeamList() {
        return teamMembers;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<TeamMember> getTeamMembers() {
        return teamMembers;
    }
}
