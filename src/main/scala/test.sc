def f(action: => Any) = action

def f2(i:Int)(f: Int => String) = f(i)

f2(1)((i:Int) => {String.valueOf(i)})

trait X {
  def foo() = println("Foo")
}


val map1 = Map(1 -> 2, 2 -> 3)
val map2 = Map(1 -> 3, 4 -> 6)
map1 ++ map2

val l1 = 1 :: 2 :: 3 :: Nil
val l2 = 4 :: 5 :: 6 :: Nil

l2 ++ l1


class Poly(terms0: Map[Int, Double]){
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  def terms = terms0 withDefaultValue 0.0
//  def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))

  def + (other:Poly) =
    new Poly((other.terms foldLeft terms)(addTerm))

  def addTerm(terms: Map[Int, Double], term: (Int, Double)):Map[Int, Double] = {
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }

  override def toString() = {
    (for((exp, coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+exp) mkString " + "
  }
}

val p1 = new Poly(1 -> 2, 2 -> 3)
val p2 = new Poly(Map(1 -> 3.0, 4 -> 6.0))

p1 + p2