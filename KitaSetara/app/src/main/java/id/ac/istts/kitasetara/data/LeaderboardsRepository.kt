package id.ac.istts.kitasetara.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.istts.kitasetara.data.source.local.AppDatabase
import id.ac.istts.kitasetara.model.leaderboard.Leaderboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaderboardsRepository (
    private val firebaseDatabase: FirebaseDatabase,
    private val localDataSource: AppDatabase
){

    suspend fun fetchLeaderboardScoreFromFirebaseAndInsertToRoom(){
        firebaseDatabase.getReference("scores").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val leaderboards = mutableListOf<Leaderboard>()
                for (snapshot in dataSnapshot.children) {
                    //get user's name
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    //get user's id
                    val userId = snapshot.child("userid").getValue(String::class.java) ?: ""
                    //get score
                    val score = snapshot.child("score").getValue(Int::class.java) ?: 0
                    //get photo url
                    val photoUrl = snapshot.child("photoUrl").getValue(String::class.java) ?: ""

                    //get id
                    val id = snapshot.key
                    leaderboards.add(Leaderboard(id = id!!, userid = userId, name = name, score = score, photoUrl = photoUrl))
                }

                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.leaderboardDao().clearLeaderboards()
                    localDataSource.leaderboardDao().insertMany(
                        leaderboards.sortedWith(compareByDescending<Leaderboard> { it.score }.thenBy {it.id})
                    )
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    suspend fun getLeaderboards():List<Leaderboard>{
        return localDataSource.leaderboardDao().getAll()
    }
}