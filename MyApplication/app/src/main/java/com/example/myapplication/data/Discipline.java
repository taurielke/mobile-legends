package com.example.myapplication.data;

public class Discipline {
    private int id;
    private String name;
    private int  teacherId;
    private int semester;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }


    @Override
    public String toString() {
        return "name: " + name + ", semester: " + semester;
    }
}
