package `in`.frostapps.a7minutesworkout

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        val crunches = ExerciseModel(1, "Crunches", R.drawable.crunches_side,
            false, false)
        exerciseList.add(crunches)

//        val lunges = ExerciseModel(2, "Lunges", R.drawable.lunges,
//            false, false)
//        exerciseList.add(lunges)
//
//        val vUps = ExerciseModel(3, "V-Ups", R.drawable.v_ups,
//            false, false)
//        exerciseList.add(vUps)
//
//        val pushUps = ExerciseModel(4, "Push Ups", R.drawable.push_ups,
//            false, false)
//        exerciseList.add(pushUps)
        return exerciseList


    }

}