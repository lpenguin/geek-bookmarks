trait Resolvable[T]{
  def resolve(data:T):Unit
}

class Foo(val text: String){
  override def toString: String = text
}

class Bar(val text:String){
  override def toString: String = text
}

class Handler{
  private val fooPromise: Promise[Foo] = new Promise[Foo]
  private val barPromise: Promise[Bar] = new Promise[Bar]

  def getFoo():Promise[Foo] = {
    fooPromise
  }

  def getBar():Promise[Bar] = {
    barPromise
  }

  def process() = {
    Thread.sleep(300)
    fooPromise.resolve(new Foo("foo"))
    Thread.sleep(200)
    barPromise.resolve(new Bar("bar"))
  }

}



class Promise[T] extends Resolvable[T]{
  var promiseCallback: T => Any = null

  //  def then[U](promiseCallback: T => Promise[U]):Promise[U]
  def then[U <: Any](promiseCallback: T => U):Unit = {
    this.promiseCallback = promiseCallback
  }

  override def resolve(data: T): Unit = {
    if(promiseCallback != null) promiseCallback.apply(data)
  }
}

val handler = new Handler

val fooPromise = handler.getFoo()

fooPromise.then(data => handler.getBar())

handler.process()



//class PromiseImpl[T]() extends Promise[T]{
//  var promiseCallback: (T) => Any = null
//  var next: Promise[Any] = null
//
//  override def resolve(data: T): Unit = {
//    promiseCallback.apply(data)
//  }
//
////  override def then(promiseCallback: (T) => Unit): Promise[Unit] = {
////    this.promiseCallback = promiseCallback
////    new PromiseImpl[Unit]
////  }
//  //  def then[U](promiseCallback: T => Promise[U]):Promise[U]
////  override def then[U](promiseCallback: (T) => U): Promise[U] = {
////    this.promiseCallback = (x => {
////        promiseCallback.apply(x)
////    })
////    next = new PromiseImpl[Any]
////  }
//}
//
//val p = new PromiseImpl[Int]()
//p.then(x => Console.println("x: "+x))
//p.resolve(1)

