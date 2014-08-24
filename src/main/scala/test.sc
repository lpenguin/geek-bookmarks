def f(action: => Any) = action

def f2(i:Int)(f: Int => String) = f(i)

f2(1)((i:Int) => {String.valueOf(i)})

trait X {
  def foo() = println("Foo")
}


foo