package br.com.disapps.data.storage.preferences

/**
 * Created by dnso on 13/03/2018.
 */
enum class InitialScreen(private var screen: String) {
    CARDS("cartoes"),
    LINES("linhas");

    override fun toString(): String {
        return screen
    }
}