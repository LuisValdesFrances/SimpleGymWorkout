package com.luis.simplegymworkout.service

import com.luis.simplegymworkout.model.Exercise
import com.luis.simplegymworkout.model.Group
import com.luis.simplegymworkout.model.Repetition
import com.luis.simplegymworkout.repository.TestRepository

class TestRepositoryService : IRepositoryService {

    override fun getGroups(): ArrayList<Group> {
        return TestRepository.groups
    }

    override fun getExercises(groupName: String): ArrayList<Exercise> {
        val groupFiltered = TestRepository.groups.filter { group -> groupName == group.name}
        val exercises = ArrayList<Exercise>()
        for(group in groupFiltered){
            for(exercise in group.exercises){
                exercises.add(exercise)
            }
        }
        return exercises
    }

    override fun getRepetitions(groupName : String, exerciseName: String): ArrayList<Repetition> {
        val exercises = this.getExercises(exerciseName)
        val exercisesFiltered = exercises.filter { exercise -> exerciseName == exercise.name}
        val repetitions = ArrayList<Repetition>()
        for(exercise in exercisesFiltered){
            for(repetition in exercise.repetitions){
                repetitions.add(repetition)
            }
        }
        return repetitions
    }

    override fun saveGroup(group: Group) {
        for(g in this.getGroups()){
            if(g.name == group.name){
                g.name = group.name
                g.exercises = group.exercises
                return
            }
        }
        this.getGroups().add(group)
    }

    override fun deleteGroup(name : String) {
        val groups = TestRepository.groups.filter {
            it.name != name
        }
        TestRepository.groups = ArrayList(groups)
    }

    override fun saveExercise(exercise: Exercise) {
        for(g in this.getGroups()){
            if(g.name == exercise.groupName){
                g.exercises.add(exercise)
                return
            }
        }
    }

    override fun deleteExercise(groupName: String, exerciseName : String) {
        for(group in TestRepository.groups){
            if(group.name == groupName){
                val exercises = group.exercises.filter {
                    it.name != exerciseName
                }
                group.exercises = exercises as ArrayList<Exercise>
            }
        }
    }
}