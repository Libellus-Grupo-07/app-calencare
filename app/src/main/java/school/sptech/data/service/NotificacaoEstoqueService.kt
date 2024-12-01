package school.sptech.data.service

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import school.sptech.data.model.NotificacaoEstoque

interface NotificacaoEstoqueService {
    @GET("/api/notificacao-estoque/buscar/{empresaId}")
    suspend fun getNotificacoesEstoque(
        @Path("empresaId") empresaId: Int
    ): Response<List<NotificacaoEstoque>>

    @DELETE("/api/notificacao-estoque/excluir/{notificacaoId}")
    suspend fun deleteNotificacaoEstoque(
        @Path("notificacaoId") notificacaoId: Int
    ): Response<Void>

    @PATCH("/api/notificacao-estoque/marcar-como-lida/{notificacaoId}")
    suspend fun marcarComoLida(
        @Path("notificacaoId") notificacaoId: Int
    ): Response<Void>

    @PATCH("/api/notificacao-estoque/marcar-todas-como-lidas/{empresaId}")
    suspend fun marcarTodasComoLida(
        @Path("empresaId") empresaId: Int
    ): Response<Void>


}