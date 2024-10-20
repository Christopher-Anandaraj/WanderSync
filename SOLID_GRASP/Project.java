import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private List<Task> tasks;
    private List<TeamMember> teamMembers;

    //Constructor with all parameters if project has all details
    public Project(String name, String description, String startDate, String endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Empty Constructor in case projects lacks any specific initial details
    public Project() {
        this("None", "None", "None", "None");
        this.tasks = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<Task> getTaskList() {
        return tasks;
    }

    public List<TeamMember> getTeamList() {
        return teamMembers;
    }
}
