package Week8GroupActivity;

import java.util.logging.Logger;

interface TeamManagement {
    public void performTeamOperation(Project project, TeamMember member);
}

class AddMember implements TeamManagement {
    @Override
    public void performTeamOperation(Project project, TeamMember member) {
        project.getTeamList().add(member);
    }
}

class RemoveMember implements TeamManagement {
    @Override
    public void performTeamOperation(Project project, TeamMember member) {
        project.getTeamList().remove(member);
    }
}

public abstract class TeamMember {
    private String name;
    private String email;
    static Logger log = Logger.getLogger(TeamMember.class.getName());

    public abstract void carryOutResponsibility();

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
        log.info("You led a meeting detailing and reviewing team assignments.");
    }
}

class TeamTester extends TeamMember {
    public TeamTester(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        log.info("You tested the code sent in by the developers for functionality and bugs.");
    }
}

class TeamReviewer extends TeamMember {
    public TeamReviewer(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        log.info("You reviewed the team's work on each responsibility, ensuring it was up to standard.");
    }
}

class TeamTaskManager extends TeamMember {
    public TeamTaskManager(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        log.info("You made sure the tasks were being performed on time and efficiently.");
    }
}

class TeamDesigner extends TeamMember {
    public TeamDesigner(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        log.info("You designed a User Interface compatible with the project.");
    }
}

class TeamDeveloper extends TeamMember {
    public TeamDeveloper(String name, String email) {
        setName(name);
        setEmail(email);
    }

    @Override
    public void carryOutResponsibility() {
        log.info("You developed code necessary to complete your responsibilities.");
    }
}
