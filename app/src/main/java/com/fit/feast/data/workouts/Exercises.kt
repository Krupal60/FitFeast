package com.fit.feast.data.workouts

data class Exercises(
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: String,
    val instructions: List<String>,
    val name: String,
    val secondaryMuscles: List<String>,
    val target: String
)

data class BodyPartList(
    val part : String
)