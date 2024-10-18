import java.util.List;
import java.util.ArrayList;

interface TeamMemberInterface {
    public void carryOutResponsibility();
}

public abstract class TeamMember implements TeamMemberInterface {
    private String name;
    private String email;
    private List<Project> projectList;

    public void joinProject(Project project) {
        if (projectList == null) {
            projectList = new ArrayList<Project>();
        }
        projectList.add(project);
        System.out.println("Project successfully added!");
    }

    public void leaveProject(Project project) {
        if (projectList == null || projectList.isEmpty()) {
            System.out.println("You aren't in any projects.");
            return;
        }
        if (projectList.contains(project)) {
            projectList.remove(project);
            System.out.println("Successfully left project!");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class TeamLeader extends TeamMember {
    public TeamLeader(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You led a meeting detailing and reviewing team assignments.");
    }
}

class TeamTester extends TeamMember {
    public TeamTester(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You tested the code sent in by the developers for functionality and bugs.");
    }
}

class TeamReviewer extends TeamMember {
    public TeamReviewer(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You reviewed the team's work on each responsibility, ensuring it was up to standard.");
    }
}

class TeamTaskManager extends TeamMember {
    public TeamTaskManager(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You made sure the tasks were being performed on time and efficiently.");
    }
}

class TeamDesigner extends TeamMember {
    public TeamDesigner(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You designed a User Interface compatible with the project.");
    }
}

class TeamDeveloper extends TeamMember {
    public TeamDeveloper(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        System.out.println("You developed code necessary to complete your responsibilities.");
    }
}

