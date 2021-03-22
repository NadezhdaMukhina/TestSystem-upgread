package entity;

import java.util.Objects;

public class Task {

    private int id;
    private String project;
    private String topic;
    private String type;
    private int priority;
    private String user;
    private String description;

    public Task() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                priority == task.priority &&
                Objects.equals(project, task.project) &&
                Objects.equals(topic, task.topic) &&
                Objects.equals(type, task.type) &&
                Objects.equals(user, task.user) &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, topic, type, priority, user, description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", topic='" + topic + '\'' +
                ", type='" + type + '\'' +
                ", priority=" + priority +
                ", user='" + user + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
