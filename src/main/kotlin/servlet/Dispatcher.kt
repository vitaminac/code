package servlet

interface Dispatcher<R_IN : Request, R_OUT : Response> {
    fun serve(request: R_IN): R_OUT;
}