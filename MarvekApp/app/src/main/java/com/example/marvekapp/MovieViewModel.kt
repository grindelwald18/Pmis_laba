package com.example.marvekapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class MovieViewModel : ViewModel() {

    private val data: MutableLiveData<List<Movie>> = MutableLiveData()
    private val selectedMovie: MutableLiveData<Movie?> = MutableLiveData()

    val navigateEventState = MutableSharedFlow<Unit>()

    val loading = MutableLiveData<Boolean>()

    init {
        loading.value = false
        data.value = emptyList()
        selectedMovie.value = null


        val list = listOf(
           Movie(1, "iron_man_1", "Железный человек 1",
                "Миллиардер-изобретатель Тони Старк попадает в плен к Афганским террористам," +
                        " которые пытаются заставить его создать оружие массового поражения. В тайне" +
                        " от своих захватчиков Старк конструирует высокотехнологичную киберброню," +
                        " которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что" +
                        " в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями." +
                        " Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально...",
                LocalDate.of(2020, 2,3)),

            Movie(2, "iron_man_2", "Железный человек 2",
                "Прошло полгода с тех пор, как мир узнал, что миллиардер-изобретатель Тони" +
                        " Старк является обладателем уникальной кибер-брони Железного человека." +
                        " Общественность требует, чтобы Старк передал технологию брони правительству" +
                        " США, но Тони не хочет разглашать её секреты, потому что боится, что она попадёт не в те руки.\n" +
                        "\n" +
                        "Между тем Иван Ванко — сын русского учёного, когда-то работавшего на фирму" +
                        " Старка, но потом уволенного и лишенного всего, намерен отомстить Тони за беды" +
                        " своей семьи. Для чего сооружает своё высокотехнологичное оружие.",
                LocalDate.of(2020, 2,3)),

            Movie(3, "iron_man_3", "Железный человек 3",
                "Когда мир Старка рушится на его глазах по вине неизвестных противников," +
                        " Тони жаждет найти виновных и свести с ними счеты. Оказавшись в безвыходной ситуации," +
                        " Старк может рассчитывать только на себя и свою изобретательность, чтобы защитить тех," +
                        " кто ему дорог. Это становится настоящим испытанием для героя, которому придется не только сражаться с" +
                        " коварным врагом, но и разобраться в себе, чтобы найти ответ на вопрос, который давно его " +
                        "тревожит: что важнее - человек или костюм?",
                LocalDate.of(2020, 2,3))
        )

        data.value = list
    }

    fun getSelectedMovie(): MutableLiveData<Movie?> {
        return selectedMovie
    }


    fun getData(): MutableLiveData<List<Movie>> {
        return data
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            loading.value = true
            val list = data.value?.toMutableList() ?: mutableListOf()
            movie.id = list.size
            list.add(movie)
            delay(2000)
            data.value = list
            loading.value = false
            navigateEventState.emit(Unit)
        }

    }

    fun selectMovie(movie: Movie?) {
        selectedMovie.value = movie
    }

    fun updateMovie(updatedMovie: Movie) {
        viewModelScope.launch {
            loading.value = true
            val list = data.value?.toMutableList() ?: mutableListOf()
            val index = list.indexOfFirst { it.id == updatedMovie.id }
            delay(2000)
            if (index != -1) {
                list[index] = updatedMovie
                data.value = list
                selectedMovie.value = updatedMovie
            }
            loading.value = false
            navigateEventState.emit(Unit)
        }
    }

    fun removeMovie(index: Int) {
        val list = data.value?.toMutableList() ?: mutableListOf()
        if (index in 0 until list.size) {
            list.removeAt(index)
            data.value = list
        }
    }
}
