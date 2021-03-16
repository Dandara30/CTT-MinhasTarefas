package br.com.dvg.minhastarefas.model

data class Tarefa(
    var id: Long,
    var titulo: String,
    val descricao: String,
    val status: StatusTarefa
)

enum class StatusTarefa(val valor: String) {
    AFAZER("a Fazer"),
    EMPROGRESSO("Em progresso"),
    FEITA("Feita")
}