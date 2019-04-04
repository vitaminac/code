package servlet

interface Request {
    val context: MutableMap<Any, Any>;
}
