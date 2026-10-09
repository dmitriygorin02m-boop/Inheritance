package ru.netology.planner;

public class Epic extends Task {
    private final String[] subtasks;

    public Epic(int id, String[] subtasks) {
        super(id);
        this.subtasks = subtasks.clone();
    }

    public String[] getSubtasks() {
        return subtasks.clone();
    }

    @Override
    public boolean matches(String query) {
        for (String subtask : subtasks) {
            if (subtask.contains(query)) {
                return true;
            }
        }
        return false;
    }
}
