package xyz.xasmc.mitori.satori

interface ResourceHandler {

    fun getResource(id: String): Resource?

    data class Resource(
        val data: ByteArray,
        val type: String? = null, // MINE 字符串
        val extension: String? = null, //后缀名
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Resource

            if (!data.contentEquals(other.data)) return false
            if (type != other.type) return false
            if (extension != other.extension) return false

            return true
        }

        override fun hashCode(): Int {
            var result = data.contentHashCode()
            result = 31 * result + (type?.hashCode() ?: 0)
            result = 31 * result + (extension?.hashCode() ?: 0)
            return result
        }
    }

}