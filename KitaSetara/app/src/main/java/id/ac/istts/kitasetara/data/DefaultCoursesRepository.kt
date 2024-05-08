package id.ac.istts.kitasetara.data

import id.ac.istts.kitasetara.data.source.local.AppDatabase
import id.ac.istts.kitasetara.model.course.Content
import id.ac.istts.kitasetara.model.course.Course
import id.ac.istts.kitasetara.model.course.Module

class DefaultCoursesRepository(
    private val localDataSource:AppDatabase
) {
    suspend fun getAllCourses():List<Course> {
        localDataSource.courseDao().clearCourses()

        //insert course data, later will be replaced using firebase
        val coursesList:List<Course> = arrayListOf(
            Course(1, "Testing Course", "Description of testing course Description of testing course Description of testing course Description of testing course v vDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing courseDescription of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing " +
                    " Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course Description of testing course course"),
            Course(2, "Testing Course 2", "Description of second testing course"),
            Course(3, "Testing Course 3", "Description of third testing course"),
            Course(4, "Testing Course 4", "Description of fourth course"),
            Course(5, "Testing Course 5", "Description of fifth testing course"),
            Course(6, "Testing Course 6", "Description of sixth testing course")
        )
        localDataSource.courseDao().insertMany(coursesList)

        return localDataSource.courseDao().getAll()
    }

    suspend fun getCourseById(id:Int): Course {
        return localDataSource.courseDao().getById(id)
    }

    suspend fun getAllCourseModule(idCourse:Int):List<Module>{

        localDataSource.moduleDao().clearModules()

        val modulesList:List<Module> = arrayListOf(
            Module(1, "Introduction", "Description of module", "1"),
            Module(2, "What is equality?", "Description of module 2", "1"),
            Module(3, "Why equality?", "Description of module 3", "1"),
            Module(4, "Introduction Course 2", "Description of module", "2"),
            Module(5, "Course 2 Scope", "Description of module 2", "2"),
            Module(6, "Why Course 2", "Description of module 3", "2"),
        )
        localDataSource.moduleDao().insertMany(modulesList)

        return  localDataSource.moduleDao().getAllModulesByCourseId(idCourse)
    }

    suspend fun insertCourses(courses:List<Course>){
        localDataSource.courseDao().insertMany(courses)
    }

    suspend fun getModule(idModule:Int):Module{
        return  localDataSource.moduleDao().getModule(idModule)
    }

    suspend fun getAllModuleContent(idModule:Int):List<Content>{
        return  localDataSource.contentDao().getAllContentByModuleId(idModule)
    }

    suspend fun getContent(idContent:Int):Content{
        return  localDataSource.contentDao().getContent(idContent)
    }

}