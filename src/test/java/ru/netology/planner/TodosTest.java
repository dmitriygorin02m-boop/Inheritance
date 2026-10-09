package ru.netology.planner;

import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

public class TodosTest {

    // Добавление задач разных типов и их получения в исходном порядке
    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    // Поиск по названию SimpleTask
    @Test
    public void shouldFindSimpleTask() {
        SimpleTask simpleTask = new SimpleTask(1, "Позвонить друзьям");

        Todos todos = new Todos();
        todos.add(simpleTask);

        Task[] expected = {simpleTask};
        Task[] actual = todos.search("друзья");

        Assertions.assertArrayEquals(expected, actual);
    }

    // Поиск по одной из подзадач Epic
    @Test
    public void shouldFindEpicBySubtask() {
        Epic epic = new Epic(2, new String[]{
                "Купить сыр",
                "Купить хлеб",
                "Купить овсянку"
        });

        Todos todos = new Todos();
        todos.add(epic);

        Task[] expected = {epic};
        Task[] actual = todos.search("хлеб");

        Assertions.assertArrayEquals(expected, actual);
    }

    // Поиск по названию проекта в Meeting
    @Test
    public void shouldFindMeetingByProject() {
        Meeting meeting = new Meeting(
                3,
                "Обсуждение релиза",
                "Приложение Т-Банка",
                "В среду вечером"
        );

        Todos todos = new Todos();
        todos.add(meeting);

        Task[] expected = {meeting};
        Task[] actual = todos.search("Т-Банка");

        Assertions.assertArrayEquals(expected, actual);
    }

    // При отсутствии совпадений, возвращается пустой массив
    @Test
    public void shouldReturnEmptyArrayWhenNoMatches() {
        Todos todos = new Todos();

        todos.add(new SimpleTask(1, "Позвонить друзьям"));
        todos.add(new Epic(2, new String[]{
                "Купить сыр",
                "Купить молоко"
        }));
        todos.add(new Meeting(
                3,
                "Обсуждение релиза",
                "Приложение Т-Банка",
                "В среду вечером"
        ));

        Task[] expected = {};
        Task[] actual = todos.search("машина");

        Assertions.assertArrayEquals(expected, actual);
    }

    // Поиск нескольких подходящих задач
    @Test
    public void shouldFindMultipleTasks() {
        SimpleTask simpleTask = new SimpleTask(1, "Подготовить релиз");

        Meeting meeting = new Meeting(
                2,
                "Обсудить релиз",
                "Приложение",
                "Завтра"
        );

        Todos todos = new Todos();
        todos.add(simpleTask);
        todos.add(meeting);

        Task[] expected = {simpleTask, meeting};
        Task[] actual = todos.search("релиз");

        Assertions.assertArrayEquals(expected, actual);
    }

    // ТЕСТЫ  ДЛЯ ПОКРЫТИЯ 100% -------->

    // метод возвращает правильный идентификатор задачи
    @Test
    public void shouldReturnId() {
        Task task = new SimpleTask(5, "Позвонить родителям");

        Assertions.assertEquals(5, task.getId());
    }

    // Одна и та же задача
    @Test
    public void shouldReturnTrueWhenComparingSameObject() {
        Task task = new SimpleTask(1, "Позвонить");

        Assertions.assertEquals(task, task);
    }

    // Разные ID
    @Test
    public void shouldReturnFalseWhenIdsAreDifferent() {
        Task first = new SimpleTask(1, "Позвонить");
        Task second = new SimpleTask(2, "Позвонить");

        Assertions.assertNotEquals(first, second);
    }

    // Сравнение с null
    @Test
    public void shouldReturnFalseWhenComparingWithNull() {
        Task task = new SimpleTask(1, "Позвонить");

        Assertions.assertNotEquals(null, task);
    }

    // Сравнение объектов разных классов
    @Test
    public void checkComparisonWithDifferentClass() {
        Task task = new Task(1);
        SimpleTask simpleTask = new SimpleTask(1, "Позвонить родителям");

        Assertions.assertNotEquals(task, simpleTask);
    }

    // Сравнение задачи с null
    @Test
    public void checkComparisonWithNull() {
        Task task = new Task(1);

        Assertions.assertNotEquals(task, null);
    }

    // Равенства задач с одинаковыми id
    @Test
    public void checkEqualIds() {
        Task task1 = new Task(1);
        Task task2 = new Task(1);

        Assertions.assertEquals(task1, task2);
    }

    // Неравенство задач с разными id
    @Test
    public void checkDifferentIds() {
        Task task1 = new Task(1);
        Task task2 = new Task(2);

        Assertions.assertNotEquals(task1, task2);
    }

    // Разные типы задач с одинаковым ID
    @Test
    public void shouldReturnFalseWhenClassesAreDifferent() {
        Task simpleTask = new SimpleTask(1, "Позвонить");
        Task meeting = new Meeting(1, "Встреча", "Проект", "Завтра");

        Assertions.assertNotEquals(simpleTask, meeting);
    }

    // Если две задачи равны по equals(), их хеш-коды тоже должны совпадать
    @Test
    public void shouldReturnSameHashCodeForEqualTasks() {
        Task first = new SimpleTask(1, "Позвонить");
        Task second = new SimpleTask(1, "Купить хлеб");

        Assertions.assertEquals(first.hashCode(), second.hashCode());
    }

    // Базовый метод matches возвращает false
    @Test
    public void shouldReturnFalseForBaseTask() {
        Task task = new Task(1);

        Assertions.assertFalse(task.matches("текст"));
    }

    // Разные объекты одного типа с одинаковым ID
    @Test
    public void shouldReturnTrueWhenIdsAreEqual() {
        Task first = new SimpleTask(1, "Позвонить");
        Task second = new SimpleTask(1, "Купить хлеб");

        Assertions.assertEquals(first, second);
    }

    // Получение темы, проекта и времени встречи
    @Test
    public void shouldReturnMeetingFields() {
        Meeting meeting = new Meeting(
                1, "Обсудить проект",
                "Приложение",
                "Завтра"
        );
        Assertions.assertEquals("Обсудить проект", meeting.getTopic());
        Assertions.assertEquals("Приложение", meeting.getProject());
        Assertions.assertEquals("Завтра", meeting.getStart());
    }

    // Получение массива подзадач
    @Test
    public void shouldReturnSubtasks() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(1, subtasks);

        Assertions.assertArrayEquals(subtasks, epic.getSubtasks());
    }

    // Получение названия задачи
    @Test
    public void shouldReturnTitle() {
        SimpleTask task = new SimpleTask(1, "Позвонить друзьям");


        Assertions.assertEquals("Позвонить друзьям", task.getTitle());
    }
}










