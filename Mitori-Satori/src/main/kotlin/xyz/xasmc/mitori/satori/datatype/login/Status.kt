package xyz.xasmc.mitori.satori.datatype.login

import xyz.xasmc.mitori.satori.util.IntEnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Status.Serializer::class)
enum class Status(val value: Int) {
    OFFLINE(0),    //离线
    ONLINE(1),     //在线
    CONNECT(2),    //连接中
    DISCONNECT(3), //断开连接
    RECONNECT(4);  //重新连接

    companion object Serializer : IntEnumSerializer<Status>("Status", entries, Status::value)
}
