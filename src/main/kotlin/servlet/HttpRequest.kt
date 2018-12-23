package servlet

class HttpRequest : Request {
    override val context: MutableMap<Any, Any> = mutableMapOf();
    val method: HttpMethod
        get() {
            TODO("Implementation");
        }
}