package com.example.myapplication.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DisciplineData {
    private static ArrayList<Discipline> disciplines  = new ArrayList<Discipline>();
    DisciplineDB disciplinesDB;

    public DisciplineData(Context context){
        disciplinesDB = new DisciplineDB(context);
        readAll();
    }

    public Discipline getDiscipline(int id){
        Discipline d = new Discipline();
        d.setId(id);
        return disciplinesDB.get(d);
    }

    public List<Discipline> findAllDiscipline(){
        return disciplines;
    }

    public void addDiscipline(String name, int semester, int teacherId){
        Discipline discipline = new Discipline();
        discipline.setName(name);
        discipline.setSemester(semester);
        discipline.setTeacherId(teacherId);
        disciplinesDB.add(discipline);
        readAll();
    }
    public void updateDiscipline(int id, String name, int semester, int teacherId){
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setName(name);
        discipline.setTeacherId(teacherId);
        discipline.setSemester(semester);
        disciplinesDB.update(discipline);
        readAll();
    }

    public void deleteAll(){
        disciplines.clear();
        disciplinesDB.deleteAll();
        readAll();
    }

    private void readAll(){
        List<Discipline> ds = disciplinesDB.readAll();
        disciplines.clear();
        for(Discipline discipline : ds){
            disciplines.add(discipline);
        }
    }
}
