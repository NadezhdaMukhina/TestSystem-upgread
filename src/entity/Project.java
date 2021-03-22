package entity;

import java.util.Objects;

public class Project {

    private String project;

    public Project() {
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project1 = (Project) o;
        return Objects.equals(project, project1.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }

    @Override
    public String toString() {
        return "Project{" +
                "project='" + project + '\'' +
                '}';
    }
}
